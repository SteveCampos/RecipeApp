@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.stevecampos.recipe.presentation.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.presentation.R
import com.stevecampos.recipe.presentation.list.RecipeListScreenEvent
import com.stevecampos.recipe.presentation.list.RecipeListScreenState

@Composable
fun RecipeOnlyListPane(
    state: RecipeListScreenState,
    event: (RecipeListScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val pullRefreshState =
        rememberPullRefreshState(state.refreshing, { event(RecipeListScreenEvent.GetRecipes) })
    Box(
        modifier = modifier
            .testTag(tag = "RecipeOnlyListPane")
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {

        if (state.hasErrors()) Column(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = state.errorMessage!!),
            )
            Button(onClick = { event(RecipeListScreenEvent.GetRecipes) }) {
                Text(text = stringResource(id = R.string.msg_try_again))
            }
        } else LazyColumn(Modifier.fillMaxWidth()) {
            item {
                RecipeSearchBar(
                    state = state,
                    event = event,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                )
            }
            items(state.recipeList) { recipe ->
                RecipeItem(recipe = recipe,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clickable { event(RecipeListScreenEvent.RecipeSelected(recipe)) })
            }
        }
        val refreshingDescription = stringResource(id = R.string.msg_refreshing_description)
        PullRefreshIndicator(
            refreshing = state.refreshing,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { contentDescription = refreshingDescription }
        )
    }
}

@Composable
fun RecipeItem(recipe: Recipe, modifier: Modifier) {
    Card(
        modifier = modifier.semantics(mergeDescendants = true) {
            contentDescription = recipe.description
        },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(id = R.string.msg_recipe_owner, recipe.owner.names),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    FakeProfileImage(modifier = Modifier.size(20.dp))
                }
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                LazyRow {
                    items(recipe.ingredients) { ingredient ->
                        SuggestionChip(onClick = { }, label = {
                            Text(ingredient, style = MaterialTheme.typography.labelSmall)
                        }, modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeSearchBar(
    state: RecipeListScreenState,
    event: (RecipeListScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = state.searchText,
        onQueryChange = { query -> event(RecipeListScreenEvent.SearchTextChanged(query)) },
        onSearch = { query -> event(RecipeListScreenEvent.SearchTextChanged(query)) },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_hint), overflow = TextOverflow.Ellipsis
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { event(RecipeListScreenEvent.SearchTextChanged(state.searchText)) },
                enabled = state.searchText.isNotEmpty()
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_hint)
                )
            }
        },
        active = false,
        onActiveChange = { },
        modifier = modifier.wrapContentHeight()
    ) {}
}


@Composable
fun FakeProfileImage(
    modifier: Modifier
) {
    Image(
        modifier = modifier.clip(CircleShape),
        painter = painterResource(id = R.drawable.img_steve),
        contentDescription = stringResource(id = R.string.fake_profile_name),
    )
}