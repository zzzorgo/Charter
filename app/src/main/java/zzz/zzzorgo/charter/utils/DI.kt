package zzz.zzzorgo.charter.utils

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import dagger.*
import zzz.zzzorgo.charter.data.AppDatabase
import zzz.zzzorgo.charter.data.dao.AccountDao
import zzz.zzzorgo.charter.data.dao.CategoryDao
import zzz.zzzorgo.charter.data.dao.RecordDao
import zzz.zzzorgo.charter.data.dao.SettingsDao
import zzz.zzzorgo.charter.data.migration.MIGRATION_1_2
import zzz.zzzorgo.charter.ui.account.AccountEditFragment
import zzz.zzzorgo.charter.ui.account.AccountListFragment
import zzz.zzzorgo.charter.ui.misc.CategoryManagerEditFragment
import zzz.zzzorgo.charter.ui.misc.CategoryManagerFragment
import zzz.zzzorgo.charter.ui.record.RecordEditFragment
import zzz.zzzorgo.charter.ui.record.RecordListFragment
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass


@Singleton
@Component(modules = [ViewModelModule::class, MainModule::class, DatabaseModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun inject(activity: RecordEditFragment)
    fun inject(activity: RecordListFragment)
    fun inject(activity: AccountEditFragment)
    fun inject(activity: AccountListFragment)
    fun inject(activity: CategoryManagerEditFragment)
    fun inject(activity: CategoryManagerFragment)
}

class ViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown model class: $modelClass")
        }
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

class MyApplication : Application() {
    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: ApplicationComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): ApplicationComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerApplicationComponent.factory().create(applicationContext)
    }
}

@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDataBase(context: Context): AppDatabase {
        val databaseName = "database"
        return Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Singleton
    @Provides
    fun provideAccountDao(database: AppDatabase): AccountDao {
        return database.accountDao()
    }


    @Singleton
    @Provides
    fun provideRecordDao(database: AppDatabase): RecordDao {
        return database.recordDao()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Singleton
    @Provides
    fun provideSettingsDao(database: AppDatabase): SettingsDao {
        return database.settingsDao()
    }
}
