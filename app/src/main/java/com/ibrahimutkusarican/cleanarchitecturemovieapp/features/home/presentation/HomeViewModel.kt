package com.ibrahimutkusarican.cleanarchitecturemovieapp.features.home.presentation

import androidx.lifecycle.viewModelScope
import com.ibrahimutkusarican.cleanarchitecturemovieapp.core.ui.BaseViewModel
import com.ibrahimutkusarican.cleanarchitecturemovieapp.core.ui.UiState
import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.home.data.local.entity.MovieType
import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.home.domain.model.HomeMovieModel
import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.home.domain.usecase.GetHomeMoviesUseCase
import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.home.domain.usecase.RefreshHomeMoviesUseCase
import com.ibrahimutkusarican.cleanarchitecturemovieapp.utils.extensions.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeMoviesUseCase: GetHomeMoviesUseCase,
    private val refreshHomeMoviesUseCase: RefreshHomeMoviesUseCase
) : BaseViewModel() {

    private val _movies = MutableStateFlow<Map<MovieType, List<HomeMovieModel>>>(mapOf())
    val movies: StateFlow<Map<MovieType, List<HomeMovieModel>>> = _movies

    private val _homeUiState =
        MutableStateFlow<UiState<Map<MovieType, List<HomeMovieModel>>>>(UiState.Loading)
    val homeUiState: StateFlow<UiState<Map<MovieType, List<HomeMovieModel>>>> = _homeUiState

    private val _refreshUiState =
        MutableStateFlow<UiState<Map<MovieType, List<HomeMovieModel>>>?>(null)
    val refreshUiState: StateFlow<UiState<Map<MovieType, List<HomeMovieModel>>>?> = _refreshUiState

    init {
        getMovies()
    }

    private fun getMovies() {
        getHomeMoviesUseCase.getHomeMoviesUseCase().doOnSuccess { movies ->
            _movies.value = movies
        }.onEach { uiState ->
            _homeUiState.value = uiState
        }.launchIn(viewModelScope)
    }

    private fun refreshMovies() {
        refreshHomeMoviesUseCase.refreshHomeMovies().doOnSuccess { movies ->
            _movies.value = movies
        }.onEach { refreshUiState ->
            _refreshUiState.emit(refreshUiState)
        }.launchIn(viewModelScope)
    }

    fun handleUiAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.PullToRefreshAction -> refreshMovies()
            is HomeUiAction.ErrorRetryAction -> getMovies()
            else -> {}
        }
    }
}