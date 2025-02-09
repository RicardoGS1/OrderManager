package com.virtualworld.mipymeanabelmaster.id

import org.koin.core.context.startKoin


fun  initKoin() {
    startKoin{
        modules(viewModelsModule,dataModules,platformModule)
    }
}

