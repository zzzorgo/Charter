package zzz.zzzorgo.charter.ui.misc

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import zzz.zzzorgo.charter.di.ViewModelKey

@Module
abstract class CategoryManagerModule {
    @Binds
    @IntoMap
    @ViewModelKey(CategoryManagerViewModel::class)
    abstract fun bindCategoryManagerViewModel(viewModel: CategoryManagerViewModel): ViewModel
}