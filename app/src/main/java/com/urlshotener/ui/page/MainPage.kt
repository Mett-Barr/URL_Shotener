package com.urlshotener.ui.page

import android.app.Activity
import android.util.Log
import android.view.WindowInsets
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.FabPosition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.ripple.rememberRipple
//import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp

import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.max
import com.urlshotener.data.URLItem
import com.urlshotener.data.UrlShortenerViewModel
import com.urlshotener.ui.component.CustomTextField
import com.urlshotener.ui.component.URLItemCard
import com.urlshotener.ui.component.URLItemCardFirst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

var slideSize = 252.dp

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
//@ExperimentalMaterial3Api
@Composable
fun MainPage(viewModel: UrlShortenerViewModel) {


    val coroutineScope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }

    // We save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    val scrollState = rememberLazyListState()


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

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    /**     Data     */
    val list by viewModel.allUrlItems.collectAsState(initial = emptyList())


    ProvideWindowInsets {
        val moveRange = thisPx(dp = slideSize + 16.dp)

        androidx.compose.material.Scaffold(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
//                .pointerInteropFilter { motionEvent ->
//                    when (motionEvent.action) {
//                        MotionEvent.ACTION_DOWN -> {
////                            viewModel.onTouchEvent.value = false
////                            viewModel.closeFabOnClick.value = false
////                            viewModel.fabOnClick.value = false
//                            Log.d("!!!", "MainPage: DOWN")
//                        }
//                        MotionEvent.ACTION_MOVE -> {
////                            viewModel.onTouchEvent.value = false
////                            viewModel.closeFabOnClick.value = false
////                            viewModel.fabOnClick.value = false
//                            Log.d("!!!", "MainPage: MOVE")
//
//                        }
//                        MotionEvent.ACTION_UP -> {
////                            viewModel.onTouchEvent.value = true
//                            Log.d("!!!", "MainPage: UP")
//                        }
////                        else -> false
//                    }
//                    false
//                }
                .onGloballyPositioned { layoutCoordinates ->
                    viewSizeInt.value = layoutCoordinates.size.width
//                    Log.d("!!!", "MainPage: viewSizeInt")
                },

//            topBar = {
//                TopAppBar(modifier = Modifier.clickable {
//                    coroutineScope.launch {
//                        scaffoldState.snackbarHostState.showSnackbar(message = "!!!", actionLabel = "Redo")
//                        Log.d("!!!", "MainPage: TopAppBar")
//                    }
//                }) {
//                }
//            },

            floatingActionButton = {
                FAB(
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
                    onClick = {
                        coroutineScope.launch {
                            // 0 is the first item index
                            scrollState.animateScrollToItem(0)
//                            scaffoldState.snackbarHostState.showSnackbar(message = "Snackbar")
                        }
                    },
                    scrollState = scrollState,
                    viewModel = viewModel,
                    coroutineScope = coroutineScope
                )

            },
            floatingActionButtonPosition = FabPosition.End,

            scaffoldState = scaffoldState,
//            snackbarHost = {
//                scaffoldState.snackbarHostState
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
                    .fillMaxSize()
                    .onGloballyPositioned { layoutCoordinates ->
                        layoutCoordinates.size.width
                    }
                    .pointerInput(scrollState) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent(PointerEventPass.Initial)
                                val dragEvent = event.changes.firstOrNull()
                                when {
                                    dragEvent!!.positionChangeConsumed() -> {
//                                        Log.d("!!!", "dragEvent: changedToDownIgnoreConsumed")
                                        return@awaitPointerEventScope
                                    }

                                    dragEvent.changedToDownIgnoreConsumed() -> {
//                                        Log.d("!!!", "dragEvent: changedToDownIgnoreConsumed")

                                        viewModel.onTouchEvent.value = false
                                        viewModel.closeFabOnClick.value = false
                                        viewModel.fabOnClick.value = false

                                        viewModel.onTouchEvent.value = false
                                    }

                                    dragEvent.changedToUpIgnoreConsumed() -> {
//                                        Log.d("!!!", "dragEvent: changedToUpIgnoreConsumed")
                                        viewModel.onTouchEvent.value = true
                                    }
                                }
                            }
                        }
                    }) {

                item {
                    Spacer(modifier = Modifier.height(slideSize + 16.dp))
                }

                items(list) { urlItem ->
//                    if (urlItem == list.first()) URLItemCardFirst(urlItem, viewModel)
//                    else URLItemCard(urlItem, viewModel)
                    URLItemCard(urlItem = urlItem, viewModel = viewModel)
                }

                if (list.isNotEmpty()) {
                    item {
                        val listSize by remember {
                            derivedStateOf { list.size }
                        }

                        val spacerHeight =
                            screenHeight - thisDp(px = 1133 + (listSize - 1) * 430) + slideSize

                        val spacerHeight2: Dp by animateDpAsState(screenHeight - thisDp(px = 1133 + (listSize - 1) * 430) + slideSize)
//                        screenHeight - thisDp(px = 1133 + (listSize - 1) * 430) + slideSize

                        if (spacerHeight > 0.dp) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(spacerHeight2)
//                                    .animateContentSize()
                                    .background(Color.Gray)
                            )
                        }
                    }
                }

                if (scrollState.isScrollInProgress) viewModel.fabOnClick.value = false
            }


//            SnackbarHost(hostState = scaffoldState.snackbarHostState, snackbar = {
//                Snackbar(action = {}) {
//                    Text(text = "SnackBar")
//                }
//            })
        }
    }
}

@Composable
fun URLItemCard(
    urlItem: URLItem,
    viewModel: UrlShortenerViewModel
) {

}

class PP(override val values: Sequence<URLItem>) : PreviewParameterProvider<URLItem>


@Composable
fun ContentCard2(
    urlItem: URLItem,
    viewModel: UrlShortenerViewModel,
    modifier: Modifier = Modifier,
    padding: Dp = 0.dp
) {

    val height = remember {
        mutableStateOf(0)
    }

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .onGloballyPositioned {
                height.value = it.size.height
            },
//            .padding(bottom = padding),
//            .then(modifier),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,

        ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            androidx.compose.material.Text(text = urlItem.originURL)
            Spacer(modifier = Modifier.size(16.dp))
            androidx.compose.material.Text(text = urlItem.shortURL)
            Spacer(modifier = Modifier.size(16.dp))

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (dateRef, descriptionRef, buttonCopyRef, buttonDeleteRef) = createRefs()

                androidx.compose.material.Text(
                    text = urlItem.date,
                    modifier = Modifier.constrainAs(dateRef) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                )
                androidx.compose.material.Text(
                    text = height.value.toString(),
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
                        .clickable {
                            viewModel.deleteById(urlItem.id)
                            Log.d("!!!", "ContentCard2: ")
                        }
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
                        .clickable {
                            Log.d("!!!", "ContentCard2: ")
                        }
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun ContentCard3(
    urlItem: URLItem,
    viewModel: UrlShortenerViewModel,
) {

    val height = remember {
        mutableStateOf(0)
    }

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .onGloballyPositioned {
                height.value = it.size.height
            }
            .padding(bottom = slideSize + 16.dp),
//            .then(modifier),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,

        ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            androidx.compose.material.Text(text = urlItem.originURL)
            Spacer(modifier = Modifier.size(16.dp))
            androidx.compose.material.Text(text = urlItem.shortURL)
            Spacer(modifier = Modifier.size(16.dp))

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (dateRef, descriptionRef, buttonCopyRef, buttonDeleteRef) = createRefs()

                androidx.compose.material.Text(
                    text = urlItem.date,
                    modifier = Modifier.constrainAs(dateRef) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                )
                androidx.compose.material.Text(
                    text = height.value.toString(),
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
                        .clickable {
                            viewModel.deleteById(urlItem.id)
                            Log.d("!!!", "ContentCard2: ")
                        }
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
                        .clickable {
                            Log.d("!!!", "ContentCard2: ")
                        }
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(10.dp)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun FAB(
    viewSizeDp: SizeProportion = SizeProportion(200.dp, 0.dp),
    scrollState: LazyListState,
    viewModel: UrlShortenerViewModel,
    coroutineScope: CoroutineScope,
    onClick: () -> Unit = {}
) {

    val fabCornerShape: Dp by animateDpAsState(targetValue = viewModel.fabRoundedCornerShape.value)

    val fabHeight: Dp by animateDpAsState(targetValue = viewSizeDp.heightRang)
    val fabWidth: Dp by animateDpAsState(targetValue = viewSizeDp.widthRange)

    val alphaProportion: Float = if (viewSizeDp.heightRang > 150.dp) {
        (viewSizeDp.heightRang.value - 150) / 50
    } else 0f

    val moveRange = thisPx(dp = slideSize + 16.dp)

    fun fabClick() {
        if (scrollState.firstVisibleItemIndex < 2) {
            onClick.invoke()
        }
        // 在 index > 2 時，點擊只能單純放大
        else {

        }
        viewModel.closeFabOnClick.value = false
        viewModel.fabOnClick.value = true
        viewModel.onTouchEvent.value = false
    }

    Card(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 8.dp)
            .size(height = fabHeight, width = fabWidth),
        shape = RoundedCornerShape(fabCornerShape),
        elevation = 16.dp,
        onClick = {
//            viewModel.fabOnClick.value = true
            fabClick()
        },

        ) {

        // Expanded state
        Box(modifier = Modifier.alpha(alphaProportion)) {
            if (alphaProportion > 0f) {
                ShortUrlFab(
                    viewModel = viewModel,
                    activity = LocalContext.current as Activity,
                    scrollState = scrollState,
                    coroutineScope = coroutineScope,
                    moveRange = moveRange
                )
            }
        }

        // Closed state
        Box {
            if (1 - alphaProportion > 0f) {
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
                        .alpha(1 - alphaProportion),
                )
            }
        }
    }
}


//滑動所產生的縮放
@Composable
fun fabAnimation(
    slideRange: Dp,
    widthRange: Dp,
    itemIndex: Int,
    fabOnClick: Boolean,
    scrollState: LazyListState,
    slideToBottom: () -> Unit = {},
    slideToNoPadding: () -> Unit = {},
    viewModel: UrlShortenerViewModel
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
    }

    // 自動歸位
    if (scrollState.firstVisibleItemIndex == 0 && viewModel.onTouchEvent.value && slideRange <= slideSize / 2) {
//        Log.d("!!!", "slideProportion: ${viewModel.onTouchEvent.value}")
        slideToBottom.invoke()
    } else if (scrollState.firstVisibleItemIndex == 0 && viewModel.onTouchEvent.value && slideRange <= slideSize) {
//        Log.d("!!!", "slideProportion: ${viewModel.onTouchEvent.value}")
        slideToNoPadding.invoke()
    }

//    // 點擊縮小FAB
    if (viewModel.closeFabOnClick.value && !fabOnClick) {
        height = 56.dp
        width = 56.dp
        Log.d("!!!!", "height $height   width $width")
    }

    viewModel.fabRoundedCornerShape.value = (16 + 40 * (1 - height.value / slideSize.value)).dp

    return SizeProportion(height, width)
}

@Composable
fun ShortUrlFab(
    viewModel: UrlShortenerViewModel,
    activity: Activity,
    scrollState: LazyListState,
    coroutineScope: CoroutineScope,
    moveRange: Int
) {
//    val activity = LocalContext.current as Activity

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var text = remember { mutableStateOf("123") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                focusManager.clearFocus()
                activity.window.insetsController?.hide(WindowInsets.Type.ime())
            }
            .onGloballyPositioned {
                text.value = it.size.height.toString()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        androidx.compose.material.Text(
            text = "Shorten your URL",
            textAlign = TextAlign.Center,
            maxLines = 4,
            style = androidx.compose.material.MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    focusManager.clearFocus()
                    Log.d("!!!", "SharePage: ")
                }
        )

        CustomTextField(
            textFieldValue = viewModel.myURL.value,
            onValueChange = { viewModel.myURL.value = it },
            label = { androidx.compose.material.Text(text = "URL") },
            trailingIcon = {
                androidx.compose.material.Icon(
                    imageVector = Icons.Rounded.ContentCopy,
                    contentDescription = "Copy",
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable {}
                        .padding(8.dp)
                        .size(24.dp)
                )
            },
            modifier = Modifier.height(100.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        OperationButton(
            clickCancel = {
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

            },
            clickOK = {
                viewModel.addNewURLItem(
                    viewModel.myURL.value.text,
                    viewModel.shortURL.value.text,
                    "10/10",
                    "qwerty"
                )
            })
    }
}

@Composable
fun thisDp(px: Int): Dp = with(LocalDensity.current) { px.toDp() }

@Composable
fun thisPx(dp: Dp): Int = with(LocalDensity.current) { dp.toPx().toInt() }

class SizeProportion(val heightRang: Dp, val widthRange: Dp)
