package com.virtualworld.mipymeanabelmaster.id

import com.virtualworld.mipymeanabelmaster.core.data.FirestoreDataSourceImp
import com.virtualworld.mipymeanabelmaster.core.repository.OrderRepository
import com.virtualworld.mipymeanabelmaster.domain.usecase.GetOrderByCodeUseCase
import com.virtualworld.mipymeanabelmaster.domain.usecase.GetOrdersSentUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import kotlin.math.sin

val dataModules = module {

    factoryOf (::GetOrdersSentUseCase)
    factoryOf (::GetOrderByCodeUseCase)

    singleOf(::OrderRepository)
    singleOf(::FirestoreDataSourceImp)

}


