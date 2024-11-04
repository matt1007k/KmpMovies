package com.maxdev.kmpmovies

import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import com.maxdev.kmpmovies.data.AndroidRegionDataSource
import com.maxdev.kmpmovies.data.RegionDataSource
import com.maxdev.kmpmovies.data.database.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val nativeModule: Module = module {
    single { getDatabaseBuilder(get()) }
    factory { Geocoder(get()) }
    factory { LocationServices.getFusedLocationProviderClient(androidContext()) }
    factoryOf(::AndroidRegionDataSource) bind RegionDataSource::class
}