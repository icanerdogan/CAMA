package com.ibrahimutkusarican.cleanarchitecturemovieapp.features.home.data

import com.ibrahimutkusarican.cleanarchitecturemovieapp.core.State
import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.home.data.local.entity.MovieEntity
import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.home.data.local.entity.MovieType
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    fun getMoviesByType(movieType: MovieType) : Flow<State<List<MovieEntity>>>
}