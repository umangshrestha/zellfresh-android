package com.zellfresh.ui.components.products

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zellfresh.client.ListProductsQuery.Rating
import com.zellfresh.client.apollo.dto.Result
import com.zellfresh.ui.components.imagebutton.ImageButtonSkeleton
import com.zellfresh.client.ListProductsQuery.Item as Product


@Composable
fun ProductsList(
    categoriesList: Result<List<Product>>,
    fetchMore: suspend () -> Unit,
    onAddItemToCart: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()

    when (categoriesList) {
        is Result.Loading -> LazyColumn(modifier = modifier) {
            repeat(4) {
                item {
                    ImageButtonSkeleton()
                }
            }
        }

        is Result.Failure -> Text(
            text = "Error: ${categoriesList.exception.message}",
            modifier = modifier
        )

        is Result.Success -> {
            LazyColumn(
                state = lazyListState,
                modifier = modifier) {
                items(categoriesList.data) { category ->
                    ProductItem(
                        name = category.name,
                        unit = category.unit,
                        description = category.description,
                        badgeText = category.badgeText,
                        price = category.price,
                        imageUrl = category.imageUrl,
                        availableQuantity = category.availableQuantity,
                        rating = category.rating,
                        onAddItemToCart = onAddItemToCart,
                        productId = category.productId
                    )
                }
                item {
                    LaunchedEffect(remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }) {
                        fetchMore()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CategoriesListLoading() {
    ProductsList(
        Result.Loading(),
        {},
        {},
    )
}


@Preview(showBackground = true)
@Composable
fun CategoriesListError() {
    ProductsList(
        Result.Failure(Exception("Error")),
        {},
        {}
    )
}

@Preview(showBackground = true)
@Composable
fun CategoriesListSuccess() {
    ProductsList(
        Result.Success(
            List(4) {
                Product(
                    name = "Product Name",
                    unit = "Unit",
                    description = "Product Description",
                    price = 10.99,
                    imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiXM1f7aFP4rKF-wJZ2juCb-7JcQCspEYUVwLK4JrpBdVtRB-ELAqpUCmkg6znfoG4fh8&usqp=CAU",
                    availableQuantity = 10,
                    rating = Rating(4.5, 100),
                    productId = "123",
                    badgeText = "Brand",
                )
            }
        ),
        {},
        {}
    )
}