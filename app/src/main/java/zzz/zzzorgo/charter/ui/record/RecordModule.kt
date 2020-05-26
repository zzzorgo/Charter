package zzz.zzzorgo.charter.ui.record

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import zzz.zzzorgo.charter.di.ViewModelKey

@Module
abstract class RecordModule {
    @Binds
    @IntoMap
    @ViewModelKey(RecordViewModel::class)
    abstract fun bindRecordViewModel(viewModel: RecordViewModel): ViewModel
}