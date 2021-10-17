package com.rcudev.myqrscan.di

import android.content.Context
import com.rcudev.myqrscan.MyQRScanApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyQRScanApplication {
        return app as MyQRScanApplication
    }

}