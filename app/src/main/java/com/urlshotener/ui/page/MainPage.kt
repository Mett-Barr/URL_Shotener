package com.urlshotener.ui.page

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.urlshotener.BASE_URL
import com.urlshotener.TEST_URL
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp

import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.launch

val slideSize = 200.dp

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun MainPage() {

    // We save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    val scrollState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    val stateCheck by remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset
        }
    }
    val listState by remember {
        derivedStateOf {
//            scrollState.layoutInfo.visibleItemsInfo.first().
        }
    }

    fun LazyListState.isScrolledToTheStart() = layoutInfo.visibleItemsInfo.first().index == 0

    val viewSizeInt = remember {
        mutableStateOf(0)
    }







    ProvideWindowInsets {
        androidx.compose.material.Scaffold(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .onGloballyPositioned { layoutCoordinates ->
                    viewSizeInt.value = layoutCoordinates.size.width
                    Log.d("!!!", "MainPage: viewSizeInt")
                },


            floatingActionButton = {
                Log.d("!!!", "MainPage: viewSizeDp")
                Test3(
//                    viewSizeDp = thisDp(px = viewSizeInt.value) - 32.dp
                    viewSizeDp = slideProportion(
                        slideRange = thisDp(px = stateCheck),
                        widthRange = thisDp(px = viewSizeInt.value),
                        itemIndex = scrollState.firstVisibleItemIndex
                    ),
//                    modifier = Modifier.clickable {
//                        coroutineScope.launch {
//                            // 0 is the first item index
//                            scrollState.animateScrollToItem(0)
//                        }
//                    },
                    onClick = {
                        coroutineScope.launch {
                            // 0 is the first item index
                            scrollState.animateScrollToItem(0)
                        }
                    },
                    scrollState = scrollState
                )
            },
            floatingActionButtonPosition = FabPosition.End,


            topBar = {
                androidx.compose.material.Text(
                    //滑多少PX
                    text = stateCheck.toString() + "\n"
                            //滑多少DP
                            + thisDp(px = stateCheck).toString() + "\n"
                            //滑到第幾個
                            + scrollState.firstVisibleItemIndex.toString(),
                    modifier = Modifier.padding(top = 50.dp)
                )
            }

        ) {
            LazyColumn(
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.systemBars,
                    applyTop = true,
                    applyBottom = true
                ),
                state = scrollState,
                reverseLayout = true
            ) {

//                item {
//                    Test2(modifier = Modifier.alpha(0f))
//                }

                items(30) { index ->
                    if (index == 0) {
                        Test(modifier = Modifier.padding(bottom = slideSize + 8.dp))
                    } else Test()
                }
            }

        }
    }
}

//@Preview
@Composable
fun ContentCard(
    originURL: String,
    shortURL: String,
    description: String,
    date: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(modifier),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,

        ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            androidx.compose.material.Text(text = originURL)
            Spacer(modifier = Modifier.size(16.dp))
            androidx.compose.material.Text(text = shortURL)
            Spacer(modifier = Modifier.size(16.dp))

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (dateRef, descriptionRef, buttonCopyRef, buttonDeleteRef) = createRefs()

                androidx.compose.material.Text(
                    text = date,
                    modifier = Modifier.constrainAs(dateRef) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                )
                androidx.compose.material.Text(
                    text = description,
                    modifier = Modifier.constrainAs(descriptionRef) {
                        start.linkTo(dateRef.end, margin = 16.dp)
                        bottom.linkTo(parent.bottom)
                    }
                )
                Icon(imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .constrainAs(buttonDeleteRef) {

                            end.linkTo(buttonCopyRef.start, margin = 16.dp)
                            centerVerticallyTo(parent)
                        }
                        .size(56.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable { }
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(10.dp)
                )

                Icon(
                    imageVector = Icons.Rounded.ContentCopy,
                    contentDescription = "Content Copy",
                    modifier = Modifier
                        .constrainAs(buttonCopyRef) {
                            end.linkTo(parent.end)
                            centerVerticallyTo(parent)
                        }
                        .size(56.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable { }
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(10.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun Test(modifier: Modifier = Modifier) {
//    Box(modifier = Modifier.fillMaxSize(),
//    contentAlignment = Alignment.Center)

//    Dialog(onDismissRequest = { /*TODO*/ }) {
    ContentCard(
        modifier = modifier,
        originURL = BASE_URL,
        shortURL = TEST_URL,
        description = "QWERTY",
        date = "10/10"
    )
//    }
}

//@Preview
@Composable
fun Test2(modifier: Modifier = Modifier) {
//    Box(modifier = Modifier.fillMaxSize(),
//    contentAlignment = Alignment.Center)

//    Dialog(onDismissRequest = { /*TODO*/ }) {
    ContentCard(
        originURL = BASE_URL,
        shortURL = TEST_URL,
        description = "QWERTY",
        date = "!!!!!!!!!!!!!!!!!",
        modifier = modifier
    )
//    }
}

@ExperimentalMaterialApi
@Composable
fun Test3(
    modifier: Modifier = Modifier,
    originURL: String = BASE_URL,
    shortURL: String = TEST_URL,
    description: String = "QWERTY",
    date: String = "!!!!!!!!!!!!!!!!!",
    viewSizeDp: SizeProportion = SizeProportion(200.dp, 0.dp),
    scrollState: LazyListState,
    onClick: () -> Unit = {}
) {

//    val fabState = remember {
//        mutableStateOf(1f)
//    }

    val clickState = remember {
        mutableStateOf(true)
    }

    var viewWidth = slideSize

//    val fabSize: Dp by animateDpAsState(targetValue = if (clickState.value) viewSizeDp else 56.dp)
    val fabHeight: Dp by animateDpAsState(targetValue = viewSizeDp.heightRang)
    val fabWidth: Dp by animateDpAsState(targetValue = viewSizeDp.widthRange)

//    val alpha = remember {
//        mutableStateOf(Modifier.alpha(1f))
//    }

    Card(
        modifier = Modifier
//            .wrapContentHeight()
//            .fillMaxWidth()
            .padding(start = 32.dp, bottom = 8.dp)
//            .then(modifier)
//            .animateContentSize()
//            .then(alpha.value)
//            .size(200.dp),
            .size(height = fabHeight, width = fabWidth),
//            .onGloballyPositioned { coordinates ->
//                viewWidth = coordinates.size.width
        shape = RoundedCornerShape(16.dp),
//                viewWidth = thisDp(px = coordinates.size.width)
//            },
        elevation = 8.dp,
        onClick = {
//            alpha.value = Modifier.alpha(0f)
//            clickState.value = !clickState.value
            if (scrollState.firstVisibleItemIndex < 2) {

                onClick.invoke()
            } else {

            }
        }

    ) {
        Spacer(
            modifier = Modifier
                .background(Color.Yellow)
                .fillMaxSize()
        )
    }
}

@Composable
fun fabAnimation(itemIndex: Int) {
    if (itemIndex > 2) {

    }
}


@Composable
fun thisDp(px: Int): Dp = with(LocalDensity.current) { px.toDp() }

@Composable
fun thisPx(dp: Dp): Int = with(LocalDensity.current) { dp.toPx().toInt() }

@Composable
fun slideProportion(
    slideRange: Dp,
    heightRang: Dp = 0.dp,
    widthRange: Dp,
    itemIndex: Int
): SizeProportion {
    val proportion: Float = if (slideSize >= slideRange && itemIndex == 0) {
        (slideSize - slideRange) / slideSize
    } else 0f
//    val proportion = (slideSize - slideRange) / widthRange
//    widthRange = (widthRange - 56.dp) * proportion
//    return ((widthRange - 56.dp) * proportion) + 56.dp

    val height = if ((slideSize - 56.dp) > slideRange && itemIndex == 0) {
        slideSize - slideRange
    } else {
        56.dp
    }
    val width = ((widthRange - 56.dp) * proportion) + 56.dp
    Log.d("!!!", "slideProportion: $height")
    return SizeProportion(height, width)
}

class SizeProportion(val heightRang: Dp, val widthRange: Dp)
