package com.urlshotener.ui.page

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.urlshotener.BASE_URL
import com.urlshotener.TEST_URL
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp

import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.max
import com.urlshotener.MainViewModel
import com.urlshotener.ui.component.CustomRoundedButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val slideSize = 200.dp

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
//@Preview
@Composable
fun MainPage(viewModel: MainViewModel) {

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

    var paddingSize = 568

    var context: Context




    ProvideWindowInsets {
        val moveRange = thisPx(dp = slideSize + 16.dp)
        val testPadding = thisPx(dp = 8.dp)

        androidx.compose.material.Scaffold(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .pointerInteropFilter { motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            viewModel.onTouchEvent.value = false
                            viewModel.closeFabOnClick.value = false
                            viewModel.fabOnClick.value = false
                            Log.d("!!!", "MainPage: DOWN")
                        }
                        MotionEvent.ACTION_UP -> {
//                            viewModel.onTouchEvent.value = true
                            Log.d("!!!", "MainPage: UP")
                        }
//                        else -> false
                    }
                    false
                }
                .onGloballyPositioned { layoutCoordinates ->
                    viewSizeInt.value = layoutCoordinates.size.width
//                    Log.d("!!!", "MainPage: viewSizeInt")
                },


            floatingActionButton = {
//                Log.d("!!!", "MainPage: viewSizeDp")
                FAB(
//                    viewSizeDp = thisDp(px = viewSizeInt.value) - 32.dp
                    viewSizeDp = fabAnimation(
                        slideRange = thisDp(px = stateCheck),
                        widthRange = thisDp(px = viewSizeInt.value),
                        itemIndex = scrollState.firstVisibleItemIndex,
                        fabOnClick = viewModel.fabOnClick.value,
                        scrollState = scrollState,
                        slideToBottom = {
                            coroutineScope.launch {
                                // 0 is the first item index
                                scrollState.animateScrollToItem(0)
                            }
                        },
                        slideToNoPadding = {
                            coroutineScope.launch {
                                // 0 is the first item index
                                scrollState.animateScrollToItem(0, moveRange)
                            }
                        },

                        viewModel = viewModel
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
                    scrollState = scrollState,
                    viewModel = viewModel,
                    coroutineScope = coroutineScope
                )

//                FAB()
            },
            floatingActionButtonPosition = FabPosition.End,


//            topBar = {
//                androidx.compose.material.Text(
//                    //滑多少PX
//                    text = stateCheck.toString() + "\n"
//                            //滑多少DP
//                            + thisDp(px = stateCheck).toString() + "\n"
//                            //滑到第幾個
//                            + scrollState.firstVisibleItemIndex.toString(),
//                    modifier = Modifier.padding(top = 50.dp)
//                )
//            }

        ) {
            LazyColumn(
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.systemBars,
                    applyTop = true,
                    applyBottom = true
                ),
                state = scrollState,
                reverseLayout = true,
                modifier = Modifier
                    .pointerInput(scrollState) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent(PointerEventPass.Initial)
                                val dragEvent = event.changes.firstOrNull()
                                when {
                                    dragEvent!!.positionChangeConsumed() -> {
                                        Log.d("!!!", "dragEvent: changedToDownIgnoreConsumed")
                                        return@awaitPointerEventScope
                                    }

                                    dragEvent.changedToDownIgnoreConsumed() -> {
                                        Log.d("!!!", "dragEvent: changedToDownIgnoreConsumed")
                                        viewModel.onTouchEvent.value = false
                                    }

                                    dragEvent.changedToUpIgnoreConsumed() -> {
                                        Log.d("!!!", "dragEvent: changedToUpIgnoreConsumed")
                                        viewModel.onTouchEvent.value = true
                                    }
                                }
                            }
                        }
                    }) {

//                item {
//                    Test2(modifier = Modifier.alpha(0f))
//                }

                items(30) { index ->
                    if (index == 0) {
                        Test(modifier = Modifier.padding(bottom = slideSize + 16.dp))
                    } else Test()

                }

                if (scrollState.isScrollInProgress) viewModel.fabOnClick.value = false

//                if (scrollState.firstVisibleItemIndex == 0 && )
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

//@Preview
//@Composable
//fun FAB() {
//
//    Card(
//        modifier = Modifier
//            .padding(start = 32.dp, bottom = 8.dp)
//            .fillMaxWidth()
//            .height(200.dp),
//        shape = RoundedCornerShape(16.dp),
//        elevation = 16.dp
//    ) {
//        Box() {
//            Icon(
//                imageVector = Icons.Rounded.ExpandMore, contentDescription = null,
//                modifier = Modifier.align(Alignment.TopCenter).size(36.dp).padding(top = 8.dp)
//            )
//            androidx.compose.material.Text(
//                text = TEST_URL,
//                modifier = Modifier.align(Alignment.Center)
//            )
//            Icon(
//                imageVector = Icons.Rounded.Add,
//                contentDescription = null,
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .size(24.dp)
//                    .alpha(0f),
//            )
//        }
//    }
//}

@ExperimentalMaterialApi
@Composable
fun FAB(
    modifier: Modifier = Modifier,
    originURL: String = BASE_URL,
    shortURL: String = TEST_URL,
    description: String = "QWERTY",
    date: String = "!!!!!!!!!!!!!!!!!",
    viewSizeDp: SizeProportion = SizeProportion(200.dp, 0.dp),
    scrollState: LazyListState,
    viewModel: MainViewModel,
    coroutineScope: CoroutineScope,
    onClick: () -> Unit = {}
) {

//    val fabSize: Dp by animateDpAsState(targetValue = if (clickState.value) viewSizeDp else 56.dp)
    val fabHeight: Dp by animateDpAsState(targetValue = viewSizeDp.heightRang)
    val fabWidth: Dp by animateDpAsState(targetValue = viewSizeDp.widthRange)

    val alphaProportion: Float = if (viewSizeDp.heightRang > 150.dp) {
        (viewSizeDp.heightRang.value - 150) / 50
    } else 0f

    val moveRange = thisPx(dp = slideSize + 16.dp)

    fun fabClick() {
//            alpha.value = Modifier.alpha(0f)
//            clickState.value = !clickState.value
        if (scrollState.firstVisibleItemIndex < 2) {
            onClick.invoke()
        }
        // 在 index > 2 時，點擊只能單純放大
        else {

        }
        viewModel.closeFabOnClick.value = false
        viewModel.fabOnClick.value = true
        viewModel.onTouchEvent.value = false

        Log.d("!!!", "fabClick: ")
    }

//    if (fabHeight == 56.dp && fabWidth == 56.dp) {
//        viewModel.closeFabOnClick.value = false
//        Log.d("!!!", "closeFabOnClick.value = false")
//    }

    Card(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 8.dp)
            .size(height = fabHeight, width = fabWidth),
        shape = RoundedCornerShape(16.dp),
        elevation = 16.dp,
        onClick = {
//            viewModel.fabOnClick.value = true
            fabClick()
        },
//        backgroundColor = androidx.compose.material.MaterialTheme.colors.background

    ) {
        Box() {
            if (alphaProportion > 0f) {
                androidx.compose.material.Icon(
                    imageVector = Icons.Rounded.ExpandMore, contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(36.dp)
                        .padding(top = 4.dp)
                        .alpha(alphaProportion)
                        .clip(RoundedCornerShape(50))
                        .clickable(enabled = alphaProportion > 0.5f) {
                            viewModel.closeFabOnClick.value = true
                            viewModel.onTouchEvent.value = false
                            if (scrollState.firstVisibleItemIndex < 2) {
                                coroutineScope.launch {
                                    // 0 is the first item index
                                    scrollState.animateScrollToItem(0, moveRange)
                                }
                            } else {
//                            viewSizeDp.heightRang.value =
                            }

                            Log.d("!!!!!", "FAB: Icon 1")
                        }
                )
                androidx.compose.material.Text(
                    text = ((viewSizeDp.heightRang - 56.dp) / (slideSize - 56.dp)).toString(),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(alphaProportion)
                        .clickable(enabled = alphaProportion > 0.5f) {
                            Log.d("!!!!!", "FAB: Text")
                        }
                )
                CustomRoundedButton(
                    text = "Test",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .alpha(alphaProportion)
                        .clickable(enabled = alphaProportion > 0.5f) {
                            Log.d("!!!!!", "FAB: Button")
                        }
                ) { }
//                androidx.compose.material.Button(
//                    onClick = { /*TODO*/ },
//                    modifier = Modifier
//                        .align(Alignment.BottomEnd)
//                        .padding(16.dp)
//                        .alpha(alphaProportion)
//                        .clickable(enabled = alphaProportion > 0.5f) {
//                            Log.d("!!!!!", "FAB: Button")
//                        },
//                ) {
//                    Text(
//                        text = "Test",
//                        style = androidx.compose.material.MaterialTheme.typography.button
//                    )
//                }

            }
            if (1 - (viewSizeDp.heightRang - 56.dp) / (slideSize - 56.dp) > 0f) {
                androidx.compose.material.Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false, radius = 300.dp),
                            enabled = alphaProportion < 0.5f
                        ) {
                            fabClick()
                            Log.d("!!!!!", "FAB: Icon 2")
                        }
                        .align(Alignment.Center)
                        .padding(16.dp)
                        .sizeIn(max(100.dp, 100.dp))
                        .fillMaxSize()
//                        .size(48.dp)
                        .alpha(1 - (viewSizeDp.heightRang - 56.dp) / (slideSize - 56.dp)),
                )
            }
        }
    }
}


//滑動所產生的縮放
@Composable
fun fabAnimation(
    slideRange: Dp,
    heightRang: Dp = 0.dp,
    widthRange: Dp,
    itemIndex: Int,
    fabOnClick: Boolean,
    scrollState: LazyListState,
    slideToBottom: () -> Unit = {},
    slideToNoPadding: () -> Unit = {},
    viewModel: MainViewModel
): SizeProportion {

    // 滑動縮放
    val proportion: Float = if (slideSize >= slideRange && itemIndex == 0) {
        (slideSize - slideRange) / slideSize
    } else 0f

    var height = if ((slideSize - 56.dp) > slideRange && itemIndex == 0) {
        slideSize - slideRange
    } else {
        56.dp
    }
    var width = ((widthRange - 56.dp) * proportion) + 56.dp

    // 點擊放大
    if (fabOnClick && !viewModel.closeFabOnClick.value) {
        height = slideSize
        width = widthRange
        Log.d("!!!!!!", "fabAnimation: ")
    }

    // 自動歸位
    if (scrollState.firstVisibleItemIndex == 0 && viewModel.onTouchEvent.value && slideRange <= 80.dp) {
//        Log.d("!!!", "slideProportion: ${viewModel.onTouchEvent.value}")
        slideToBottom.invoke()
        Log.d("!!!", "fabAnimation: ")
    } else if (scrollState.firstVisibleItemIndex == 0 && viewModel.onTouchEvent.value && slideRange <= 200.dp) {
//        Log.d("!!!", "slideProportion: ${viewModel.onTouchEvent.value}")
        slideToNoPadding.invoke()
    }

//    // 點擊縮小FAB
    if (viewModel.closeFabOnClick.value && !fabOnClick) {
        height = 56.dp
        width = 56.dp
        Log.d("!!!!", "height $height   width $width")
    }
    return SizeProportion(height, width)
}

@Composable
fun thisDp(px: Int): Dp = with(LocalDensity.current) { px.toDp() }

@Composable
fun thisDp2(px: Int): Dp = LocalDensity.current.run { px.toDp() }

@Composable
fun thisPx(dp: Dp): Int = with(LocalDensity.current) { dp.toPx().toInt() }

class SizeProportion(val heightRang: Dp, val widthRange: Dp)