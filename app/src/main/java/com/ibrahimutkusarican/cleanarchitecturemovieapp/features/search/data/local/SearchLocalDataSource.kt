package com.ibrahimutkusarican.cleanarchitecturemovieapp.features.search.data.local

import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.details.data.local.VisitedMovieDao
import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.details.data.local.VisitedMovieEntity
import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.search.data.local.entities.RegionEntity
import javax.inject.Inject

class SearchLocalDataSource @Inject constructor(
    private val visitedMovieDao: VisitedMovieDao,
    private val regionDao: RegionDao
) {

    suspend fun getLastVisitedMovies() : List<VisitedMovieEntity> =
        visitedMovieDao.getVisitedMovies()

    suspend fun getRegions() : List<RegionEntity> = regionDao.getRegions()

    suspend fun insertRegions(regions : List<RegionEntity>) = regionDao.insertRegions(regions)

    suspend fun deleteAllRegions() = regionDao.deleteAllRegions()
}