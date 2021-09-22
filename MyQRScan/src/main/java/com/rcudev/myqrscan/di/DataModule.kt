package com.rcudev.myqrscan.di

import android.content.Context
import androidx.room.Room
import com.rcudev.myqrscan.data.MyQRScanScanRepositoryImpl
import com.rcudev.myqrscan.data.local.MyQRScanDao
import com.rcudev.myqrscan.data.local.MyQRScanDb
import com.rcudev.myqrscan.domain.repository.MyQRScanRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMyQRScanRepository(myQRScanDao: MyQRScanDao): MyQRScanRepository =
        MyQRScanScanRepositoryImpl(myQRScanDao)

    @Provides
    @Singleton
    fun provideMyQRScanDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MyQRScanDb::class.java, "qr").allowMainThreadQueries().build()

    @Provides
    fun provideMyQRScanDao(myQRScanDb: MyQRScanDb) = myQRScanDb.composeQRDao()

}