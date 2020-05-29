package zzz.zzzorgo.charter.ui.statistic

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import zzz.zzzorgo.charter.di.ViewModelKey

@Module
abstract class StatisticModule {
    @Binds
    @IntoMap
    @ViewModelKey(StatisticViewModel::class)
    abstract fun bindStatisticViewModel(viewModel: StatisticViewModel): ViewModel
}
