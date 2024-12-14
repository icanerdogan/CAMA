package com.ibrahimutkusarican.cleanarchitecturemovieapp.features.main.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ibrahimutkusarican.cleanarchitecturemovieapp.R

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    NavigationBar(
        modifier = modifier
    ) {

        var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BottomNavigationItems.items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    selected = selectedItemIndex == index,
                    bottomNavigationItems = item,
                    onClick = {
                        selectedItemIndex = index
                        navController.navigate(item.navigationRoute) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomNavigationItem(
    modifier: Modifier = Modifier
        .width(100.dp),
    selected: Boolean = false,
    onClick: () -> Unit = {},
    bottomNavigationItems: BottomNavigationItems = BottomNavigationItems(
        itemName = "movies",
        itemLabel = R.string.movies,
        iconResourceId = R.drawable.ic_movie,
        navigationRoute = NavigationRoutes.Home
    ),
) {
    val animatedIndicatorColor by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
        label = "animatedIndicatorColor"
    )
    val animatedIconAndTextColor by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "animatedIconAndTextColor"
    )
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(if (selected) 2.dp else 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = animatedIndicatorColor,
            contentColor = animatedIconAndTextColor
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (selected) Arrangement.Start else Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.weight(1f),
                painter = painterResource(bottomNavigationItems.iconResourceId),
                contentDescription = bottomNavigationItems.itemName
            )
            AnimatedVisibility(selected) {
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(bottomNavigationItems.itemLabel),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}