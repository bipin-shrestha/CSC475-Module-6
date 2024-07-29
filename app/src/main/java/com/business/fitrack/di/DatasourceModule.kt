package com.business.fitrack.di

import com.business.fitrack.data.network.RemoteDatasource
import com.business.fitrack.data.network.RemoteDatasourceImpl
import com.business.fitrack.data.network.api.ProductApi
import com.business.fitrack.data.network.api.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides remote datasource-related instances.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DatasourceModule {

    /**
     * Provides an instance of [RemoteDatasource].
     */
    @Singleton
    @Provides
    fun provideRemoteDataSource(productApi: ProductApi, searchApi: SearchApi): RemoteDatasource =
        RemoteDatasourceImpl(productApi, searchApi)
}