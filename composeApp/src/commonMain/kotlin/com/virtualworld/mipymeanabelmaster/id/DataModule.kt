package com.virtualworld.mipymeanabelmaster.id

import com.virtualworld.mipymeanabelmaster.domain.GetOrdersSentUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModules = module {

    factoryOf (::GetOrdersSentUseCase)


}


