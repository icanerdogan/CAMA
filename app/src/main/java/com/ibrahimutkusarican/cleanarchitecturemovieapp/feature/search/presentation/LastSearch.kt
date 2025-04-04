package com.ibrahimutkusarican.cleanarchitecturemovieapp.feature.search.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ibrahimutkusarican.cleanarchitecturemovieapp.R
import com.ibrahimutkusarican.cleanarchitecturemovieapp.feature.search.domain.model.SearchItemModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview(showBackground = true)
fun LastSearch(
    modifier: Modifier = Modifier,
    lastSearch: List<SearchItemModel> = listOf(SearchItemModel(10,"asdsad")),
    handleSearchUiAction: (uiAction : SearchUiAction) -> Unit = {}
){
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.last_search),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                modifier = Modifier.clickable {
                    handleSearchUiAction.invoke(SearchUiAction.LastSearchAllClearAction)
                },
                text = stringResource(R.string.clear_all),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.small_padding)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
        ) {
            lastSearch.forEach { item ->
                SearchItem(
                    item = item,
                    searchItemType = SearchItemType.LAST_SEARCH,
                    searchItemRemoveClickAction = { deleteItem ->
                        handleSearchUiAction.invoke(SearchUiAction.LastSearchItemDeleteClickAction(deleteItem))
                    },
                    searchItemClickAction = { text ->
                        handleSearchUiAction.invoke(SearchUiAction.LastSearchItemClickAction(text))
                    }
                )
            }
        }
    }
}