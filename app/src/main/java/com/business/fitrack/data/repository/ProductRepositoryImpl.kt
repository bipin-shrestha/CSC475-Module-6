package com.business.fitrack.data.repository

import com.business.fitrack.domain.product.Product
import com.business.fitrack.domain.product.ProductRepository
import com.business.fitrack.domain.product.Resource
import com.business.fitrack.data.database.dao.ProductDao
import com.business.fitrack.data.mapper.toEntity
import com.business.fitrack.data.mapper.toProduct
import com.business.fitrack.data.mapper.toProductsList
import com.business.fitrack.data.network.DataResult
import com.business.fitrack.data.network.RemoteDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val remoteDatasource: RemoteDatasource
) : ProductRepository {

    override suspend fun saveProduct(product: Product) {
        productDao.insert(product.toEntity())
    }

    override suspend fun removeProduct(product: Product) {
        productDao.remove(product.toEntity())
    }

    override suspend fun getProduct(id: Int): Flow<Product?> =
        productDao.get(id).map { it?.toProduct() }

    override suspend fun getAllProducts(): Flow<List<Product>> =
        productDao.getAll().map {
            it.map { entity -> entity.toProduct() }
        }

    override suspend fun getRemoteProduct(code: String): Flow<Resource<Product>> {
        return flow {
            emit(Resource.Loading)
            val result = remoteDatasource.getProduct(code)
            when(result) {
                is DataResult.Success -> {
                    emit(Resource.Success(result.data.toProduct()))
                }
                is DataResult.Error -> {
                    emit(Resource.Error(""))
                }
            }
        }
    }

    override suspend fun searchByQuery(query: String): Flow<Resource<List<Product>>> {
        return flow {
            emit(Resource.Loading)
            val result = remoteDatasource.searchByQuery(query)
            when(result) {
                is DataResult.Success -> {
                    emit(Resource.Success(result.data.toProductsList()))
                }
                is DataResult.Error -> {
                    emit(Resource.Error(""))
                }
            }
        }
    }
}