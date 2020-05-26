package zzz.zzzorgo.charter.utils

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import zzz.zzzorgo.charter.di.ViewModelKey
import zzz.zzzorgo.charter.ui.account.AccountViewModel
import zzz.zzzorgo.charter.ui.misc.CategoryManagerViewModel
import zzz.zzzorgo.charter.ui.record.RecordViewModel

@Module
abstract class MainModule {
    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryManagerViewModel::class)
    abstract fun bindMainViewModel(viewModel: CategoryManagerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecordViewModel::class)
    abstract fun bindIntroViewModel(viewModel: RecordViewModel): ViewModel
}