package com.rcudev.myqrscan.di

import com.rcudev.myqrscan.domain.repository.MyQRScanRepository
import com.rcudev.myqrscan.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetQRListUseCase(scanRepository: MyQRScanRepository) =
        GetQRListUseCase(scanRepository)

    @Provides
    fun provideGetQRListByCategoryUseCase(scanRepository: MyQRScanRepository) =
        GetQRListByCategoryUseCase(scanRepository)

    @Provides
    fun provideSaveQRUseCase(scanRepository: MyQRScanRepository) = SaveQRUseCase(scanRepository)

    @Provides
    fun provideUpdateQRUseCase(scanRepository: MyQRScanRepository) = UpdateQRUseCase(scanRepository)

    @Provides
    fun provideDeleteQRUseCase(scanRepository: MyQRScanRepository) = DeleteQRUseCase(scanRepository)
}