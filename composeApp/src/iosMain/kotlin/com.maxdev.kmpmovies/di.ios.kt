package com.maxdev.kmpmovies

import com.maxdev.kmpmovies.data.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val nativeModule: Module = module {
    single { getDatabaseBuilder() }
}