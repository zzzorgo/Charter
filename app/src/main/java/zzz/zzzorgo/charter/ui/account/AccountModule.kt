package zzz.zzzorgo.charter.ui.account

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import zzz.zzzorgo.charter.di.ViewModelKey

@Module
abstract class AccountModule {
    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel
}