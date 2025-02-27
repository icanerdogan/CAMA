package com.ibrahimutkusarican.cleanarchitecturemovieapp.features.search.domain.usecase

import com.ibrahimutkusarican.cleanarchitecturemovieapp.core.ui.UiState
import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.search.domain.model.SearchItemModel
import kotlinx.coroutines.flow.Flow

interface DeleteLastSearchUseCase {
    fun deleteAllLastSearch() : Flow<UiState<Boolean>>
    fun deleteLastSearch(searchItemModel: SearchItemModel) : Flow<UiState<Boolean>>
}