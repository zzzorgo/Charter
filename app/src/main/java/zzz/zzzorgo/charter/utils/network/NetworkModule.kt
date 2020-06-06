package zzz.zzzorgo.charter.utils.network

import com.android.volley.toolbox.BaseHttpStack
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.HurlStack
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideHttpStack(): BaseHttpStack {
        return HurlStack()
    }
    
    @Provides
    fun provideBasicNetwork(baseHttpStack: BaseHttpStack): BasicNetwork {
        return LogBasicNetwork(baseHttpStack)
    }
}