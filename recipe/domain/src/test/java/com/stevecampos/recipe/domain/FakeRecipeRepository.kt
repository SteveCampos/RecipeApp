package com.stevecampos.recipe.domain

import com.stevecampos.core.common.Failure
import com.stevecampos.core.common.Result
import com.stevecampos.recipe.domain.entities.Geolocation
import com.stevecampos.recipe.domain.entities.Recipe
import com.stevecampos.recipe.domain.entities.RecipeOwner
import com.stevecampos.recipe.domain.repository.RecipeRepository

class FakeRecipeRepository(private val mustSucceed: Boolean): RecipeRepository {
    override suspend fun getRecipeList(): Result<List<Recipe>> {
        if (!mustSucceed) return Result.Error(Failure.Others)
        val recipes = mutableListOf<Recipe>()

        // Recipe 1: Ceviche
        recipes.add(
            Recipe(
                1,
                "Ceviche",
                "ceviche_image_url",
                "Plato de mariscos frescos y jugosos, marinados en jugo de limón",
                listOf("pescado", "limón", "cebolla", "cilantro"),
                listOf("1. Cortar el pescado en dados", "2. Mezclar con jugo de limón", "3. Añadir cebolla y cilantro"),
                RecipeOwner("Chef Peruano 1", "Perú"),
                Geolocation(-12.0464, -77.0428)
            )
        )

        // Recipe 2: Lomo Saltado
        recipes.add(
            Recipe(
                2,
                "Lomo Saltado",
                "lomo_saltado_image_url",
                "Carne de res salteada con verduras y especias peruanas",
                listOf("carne de res", "cebolla", "tomate", "salsa de soja"),
                listOf("1. Saltear la carne de res", "2. Agregar las verduras", "3. Mezclar con salsa de soja"),
                RecipeOwner("Chef Peruano 2", "Perú"),
                Geolocation(-12.0464, -77.0428)
            )
        )

        // Recipe 3: Aji de Gallina
        recipes.add(
            Recipe(
                3,
                "Ají de Gallina",
                "aji_de_gallina_image_url",
                "Plato cremoso de pollo desmenuzado en una salsa de ají amarillo y nueces",
                listOf("pollo", "ají amarillo", "nueces", "leche"),
                listOf("1. Cocinar el pollo", "2. Preparar la salsa de ají amarillo y nueces", "3. Mezclar con leche"),
                RecipeOwner("Chef Peruano 3", "Perú"),
                Geolocation(-12.0464, -77.0428)
            )
        )

        // Recipe 4: Anticuchos
        recipes.add(
            Recipe(
                4,
                "Anticuchos",
                "anticuchos_image_url",
                "Brochetas de corazón de res marinadas y asadas",
                listOf("corazón de res", "ají panca", "comino", "vinagre"),
                listOf("1. Marinadar el corazón de res", "2. Ensartar en brochetas", "3. Asar en parrilla"),
                RecipeOwner("Chef Peruano 4", "Perú"),
                Geolocation(-12.0464, -77.0428)
            )
        )

        // Recipe 5: Causa Limeña
        recipes.add(
            Recipe(
                5,
                "Causa Limeña",
                "causa_limena_image_url",
                "Pastel de papa amarilla relleno con atún, pollo o mariscos",
                listOf("papa amarilla", "limón", "ají amarillo", "atún"),
                listOf("1. Cocinar las papas", "2. Preparar el relleno", "3. Armar el pastel"),
                RecipeOwner("Chef Peruano 5", "Perú"),
                Geolocation(-12.0464, -77.0428)
            )
        )

        // Recipe 6: Seco de Cordero
        recipes.add(
            Recipe(
                6,
                "Seco de Cordero",
                "seco_de_cordero_image_url",
                "Guiso de cordero con cilantro, cerveza y chicha de jora",
                listOf("cordero", "cilantro", "chicha de jora", "aji amarillo"),
                listOf("1. Cocinar el cordero", "2. Preparar la salsa de cilantro y chicha", "3. Cocinar a fuego lento"),
                RecipeOwner("Chef Peruano 6", "Perú"),
                Geolocation(-12.0464, -77.0428)
            )
        )

        // Recipe 7: Tacu Tacu
        recipes.add(
            Recipe(
                7,
                "Tacu Tacu",
                "tacu_tacu_image_url",
                "Plato de arroz y frijoles mezclado y luego frito, acompañado con lomo saltado",
                listOf("arroz", "frijoles", "cebolla", "huevo"),
                listOf("1. Cocinar arroz y frijoles", "2. Mezclar y formar tortas", "3. Freír y servir con lomo saltado"),
                RecipeOwner("Chef Peruano 7", "Perú"),
                Geolocation(-12.0464, -77.0428)
            )
        )

        // Recipe 8: Rocoto Relleno
        recipes.add(
            Recipe(
                8,
                "Rocoto Relleno",
                "rocoto_relleno_image_url",
                "Rocotos rellenos con carne, papas y queso, horneados y gratinados",
                listOf("rocoto", "carne molida", "papas", "queso"),
                listOf("1. Rellenar los rocotos", "2. Hornear y gratinar"),
                RecipeOwner("Chef Peruano 8", "Perú"),
                Geolocation(-12.0464, -77.0428)
            )
        )

        // Recipe 9: Papa a la Huancaína
        recipes.add(
            Recipe(
                9,
                "Papa a la Huancaína",
                "papa_a_la_huancaina_image_url",
                "Papas cocidas con salsa cremosa de ají amarillo y queso fresco",
                listOf("papas", "ají amarillo", "queso fresco", "nueces"),
                listOf("1. Cocinar las papas", "2. Preparar la salsa de ají amarillo y queso", "3. Servir sobre las papas"),
                RecipeOwner("Chef Peruano 9", "Perú"),
                Geolocation(-12.0464, -77.0428)
            )
        )

        // Recipe 10: Arroz con Mariscos
        recipes.add(
            Recipe(
                10,
                "Arroz con Mariscos",
                "arroz_con_mariscos_image_url",
                "Arroz cocido con una mezcla de mariscos en salsa de ají amarillo",
                listOf("arroz", "mariscos variados", "ají amarillo", "cebolla"),
                listOf("1. Cocinar el arroz", "2. Preparar la mezcla de mariscos en salsa", "3. Mezclar con el arroz"),
                RecipeOwner("Chef Peruano 10", "Perú"),
                Geolocation(-12.0464, -77.0428)
            )
        )

        return Result.Success(recipes)
    }
}