package com.stevecampos.recipe.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stevecampos.recipe.domain.entities.Geolocation
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.entities.RecipeOwner
import com.stevecampos.recipe.presentation.R
import com.stevecampos.recipe.presentation.list.components.FakeProfileImage
import com.stevecampos.recipe.presentation.list.RecipeListScreenEvent
import com.stevecampos.recipe.presentation.list.navigation.RecipeContentType

@Composable
fun RecipeDetailPane(
    recipe: Recipe,
    contentType: RecipeContentType,
    event: (RecipeListScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {


    Column(
        modifier
            .fillMaxSize()
            .testTag(tag = "RecipeDetailPane")
    ) {
        Box(
            modifier = Modifier
                .weight(4f)
                .fillMaxSize()
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxSize()
            )
            if (contentType is RecipeContentType.ListOnly)
                IconButton(
                    onClick = { event(RecipeListScreenEvent.RecipeDetailBackClicked) },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(48.dp)
                        .align(
                            Alignment.TopStart
                        )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                        tint = Color.White,
                        contentDescription = stringResource(
                            id = R.string.msg_back
                        )
                    )
                }
        }

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(6f)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FakeProfileImage(modifier = Modifier.size(20.dp))
                    Text(
                        text = stringResource(id = R.string.msg_recipe_owner, recipe.owner.names),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_star_rate_24),
                        tint = Color.Yellow,
                        contentDescription = stringResource(id = R.string.msg_recipe_reputation)
                    )
                    Text(text = "4.5")
                }
            }

            RecipeBadges(recipe = recipe)

            ElevatedButton(
                onClick = { event(RecipeListScreenEvent.MapIconClicked(recipe)) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_map_24),
                    contentDescription = stringResource(id = R.string.msg_locate_recipe_on_map)
                )
                Text(
                    text = stringResource(id = R.string.msg_locate_recipe_on_map),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Column(
                modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.msg_recipe_short_description),
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = recipe.description, style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.msg_cooking_stage),
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = recipe.steps.reduce { acc, s -> "$acc\n$s" },
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.msg_ingredients),
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = recipe.ingredients.reduce { acc, s -> "$acc, $s" },
                    style = MaterialTheme.typography.bodyMedium
                )
            }


        }
    }
}

@Composable
fun RecipeBadges(recipe: Recipe) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconWithText(
                iconResId = R.drawable.ic_peru, text = stringResource(
                    id = R.string.msg_country
                )
            )
            IconWithText(
                iconResId = R.drawable.ic_recipe, text = stringResource(
                    id = R.string.msg_steps, recipe.steps.size
                )
            )
            IconWithText(
                iconResId = R.drawable.ic_healthy, text = stringResource(
                    id = R.string.msg_healthy
                )
            )
        }
    }
}

@Composable
fun IconWithText(
    iconResId: Int, text: String, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = text,
            modifier = modifier.size(32.dp)
        )
        Text(text = text, style = MaterialTheme.typography.labelSmall)
    }
}

@Preview
@Composable
fun RecipeDetailScreenPreview() {
    val recipe = Recipe(
        1,
        "Ceviche",
        "https://www.recetasnestle.com.pe/sites/default/files/srh_recipes/983b3beba61893c89be5456219d45451.jpg",
        "Plato de mariscos frescos y jugosos, marinados en jugo de limón",
        listOf("pescado", "limón", "cebolla", "cilantro"),
        listOf(
            "1. Cortar el pescado en dados",
            "2. Mezclar con jugo de limón",
            "3. Añadir cebolla y cilantro"
        ),
        RecipeOwner("Chef Peruano 1", "Perú"),
        Geolocation(-12.0464, -77.0428)
    )
    RecipeDetailPane(recipe = recipe, contentType = RecipeContentType.ListOnly, event = {})
}