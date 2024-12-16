package com.ibrahimutkusarican.cleanarchitecturemovieapp.features.home.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ibrahimutkusarican.cleanarchitecturemovieapp.core.BaseViewModel
import com.ibrahimutkusarican.cleanarchitecturemovieapp.core.genre.domain.usecase.GetMovieGenresUseCase
import com.ibrahimutkusarican.cleanarchitecturemovieapp.utils.extensions.doOnSuccess
import com.ibrahimutkusarican.cleanarchitecturemovieapp.utils.extensions.getSuccessOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieGenresUseCase: GetMovieGenresUseCase
): BaseViewModel() {

    init {
        viewModelScope.launch {
            val data = getMovieGenresUseCase.getMovieGenresUseCase()
                .getSuccessOrThrow()
            Log.d(TAG,data.toString())
        }
    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

}