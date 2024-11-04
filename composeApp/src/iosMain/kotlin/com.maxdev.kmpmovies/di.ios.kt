package com.maxdev.kmpmovies

import com.maxdev.kmpmovies.data.IosRegionDataSource
import com.maxdev.kmpmovies.data.RegionDataSource
import com.maxdev.kmpmovies.data.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocationManager

actual val nativeModule: Module = module {
    single { getDatabaseBuilder() }
    factoryOf(::IosRegionDataSource) bind RegionDataSource::class
}