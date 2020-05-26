package zzz.zzzorgo.charter.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import zzz.zzzorgo.charter.data.DatabaseModule
import zzz.zzzorgo.charter.ui.account.AccountEditFragment
import zzz.zzzorgo.charter.ui.account.AccountListFragment
import zzz.zzzorgo.charter.ui.misc.CategoryManagerEditFragment
import zzz.zzzorgo.charter.ui.misc.CategoryManagerFragment
import zzz.zzzorgo.charter.ui.record.RecordEditFragment
import zzz.zzzorgo.charter.ui.record.RecordListFragment
import zzz.zzzorgo.charter.utils.MainModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ViewModelModule::class,
    MainModule::class,
    DatabaseModule::class
])
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