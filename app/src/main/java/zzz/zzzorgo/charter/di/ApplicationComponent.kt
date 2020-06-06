package zzz.zzzorgo.charter.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import zzz.zzzorgo.charter.data.DatabaseModule
import zzz.zzzorgo.charter.ui.account.AccountEditFragment
import zzz.zzzorgo.charter.ui.account.AccountListFragment
import zzz.zzzorgo.charter.ui.account.AccountModule
import zzz.zzzorgo.charter.ui.misc.CategoryManagerEditFragment
import zzz.zzzorgo.charter.ui.misc.CategoryManagerFragment
import zzz.zzzorgo.charter.ui.misc.CategoryManagerModule
import zzz.zzzorgo.charter.ui.record.RecordEditFragment
import zzz.zzzorgo.charter.ui.record.RecordListFragment
import zzz.zzzorgo.charter.ui.record.RecordModule
import zzz.zzzorgo.charter.ui.statistic.StatisticFragment
import zzz.zzzorgo.charter.ui.statistic.StatisticModule
import zzz.zzzorgo.charter.utils.network.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ViewModelModule::class,
    DatabaseModule::class,
    AccountModule::class,
    CategoryManagerModule::class,
    RecordModule::class,
    StatisticModule::class,
    NetworkModule::class
])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun inject(fragment: RecordEditFragment)
    fun inject(fragment: RecordListFragment)
    fun inject(fragment: AccountEditFragment)
    fun inject(fragment: AccountListFragment)
    fun inject(fragment: CategoryManagerEditFragment)
    fun inject(fragment: CategoryManagerFragment)
    fun inject(fragment: StatisticFragment)
}