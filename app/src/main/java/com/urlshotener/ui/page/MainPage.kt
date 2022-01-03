package com.urlshotener.ui.page

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.WindowInsets
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp

import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.max
import com.google.accompanist.insets.systemBarsPadding
import com.urlshotener.ApplicationToast
import com.urlshotener.data.URLItem
import com.urlshotener.data.UrlShortenerViewModel
import com.urlshotener.ui.component.CustomTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

var slideSize = 252.dp

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
//@Preview
@Composable
fun MainPage(viewModel: UrlShortenerViewModel) {

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


    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    /**     Data     */
    val list by viewModel.allUrlItems.collectAsState(initial = emptyList())


    ProvideWindowInsets {
        val moveRange = thisPx(dp = slideSize + 16.dp)
        val testPadding = thisPx(dp = 8.dp)

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
//                    .heightIn(min = screenHeight + slideSize + 16.dp)
                    .fillMaxSize()
//                    .defaultMinSize(minHeight = screenHeight + slideSize + 16.dp)
//                    .height(1000.dp)
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
                                        Log.d("!!!", "dragEvent: changedToDownIgnoreConsumed")
                                        return@awaitPointerEventScope
                                    }

                                    dragEvent.changedToDownIgnoreConsumed() -> {
                                        Log.d("!!!", "dragEvent: changedToDownIgnoreConsumed")

                                        viewModel.onTouchEvent.value = false
                                        viewModel.closeFabOnClick.value = false
                                        viewModel.fabOnClick.value = false

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

//                items(viewModel.getItemsSize()) { index ->
//                    if (index == 0) {
//                        Test(modifier = Modifier.padding(bottom = slideSize + 16.dp))
//                    } else Test()
//
//                }

//                items(viewModel.allUrlItems)

//                val list by viewModel.allUrlItems.collectAsState(initial = emptyList())

//                coroutineScope.launch {
//                    viewModel.allUrlItems.collect() {
//                        Log.d("!!!", it.size.toString())
//                        items(it) { index ->
//                            if (index.id == 0) {
//                                Test(modifier = Modifier.padding(bottom = slideSize + 16.dp))
//                            } else Test()
//
//                        }
//                    }
//                }

                items(list) { item ->
//                    AnimatedContent(targetState = list) {
//
//                    }
                    if (item == list.first()) {
//                        Test(modifier = Modifier.padding(bottom = slideSize + 16.dp))
                        ContentCard3(urlItem = item, viewModel = viewModel)
                    } else ContentCard2(urlItem = item, viewModel = viewModel)
                }

                if (list.isNotEmpty()) {
//                    val spacerHeight = screenHeight -
                    item {
                        val spacerHeight =
                            screenHeight - thisDp(px = 1133 + (list.size - 1) * 430) + slideSize
                        Log.d("!!!", screenHeight.toString())
                        if (spacerHeight > 0.dp) Spacer(modifier = Modifier.height(spacerHeight))
                    }
                }

//                itemsIndexed(
//                    items = list,
//                    itemContent = { index, item ->
//                        AnimatedVisibility(
//                            visible =
//                        ) {
//
//                        }
//                    }
//                )

                if (scrollState.isScrollInProgress) viewModel.fabOnClick.value = false

//                if (scrollState.firstVisibleItemIndex == 0 && )
            }

        }
    }
}

@Composable
fun test(viewModel: UrlShortenerViewModel): List<URLItem> {
    val list by viewModel.allUrlItems.collectAsState(initial = emptyList())
    return list
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


//@Preview
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
        date = "10/10",
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
    viewModel: UrlShortenerViewModel,
    coroutineScope: CoroutineScope,
    onClick: () -> Unit = {}
) {

    val fabCornerShape: Dp by animateDpAsState(targetValue = viewModel.fabRoundedCornerShape.value)

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

    Card(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 8.dp)
            .size(height = fabHeight, width = fabWidth),
        shape = RoundedCornerShape(fabCornerShape),
//        shape = RoundedCornerShape(16.dp),
        elevation = 16.dp,
        onClick = {
//            viewModel.fabOnClick.value = true
            fabClick()
            Log.d("!!!", "FAB: fabClick")
        },
//        backgroundColor = androidx.compose.material.MaterialTheme.colors.background

    ) {

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
    heightRang: Dp = 0.dp,
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
        Log.d("!!!!!!", "fabAnimation: ")
    }

    // 自動歸位
    if (scrollState.firstVisibleItemIndex == 0 && viewModel.onTouchEvent.value && slideRange <= slideSize / 2) {
//        Log.d("!!!", "slideProportion: ${viewModel.onTouchEvent.value}")
        slideToBottom.invoke()
        Log.d("!!!", "fabAnimation: ")
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

    viewModel.fabRoundedCornerShape.value =
        (16 + 40 * slideRange.value.toInt() / slideSize.value.toInt()).dp

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
//                slideSize = (it.size.height).dp + 32.dp
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

//        Spacer(modifier = Modifier.size(16.dp))
//
//        CustomTextField(
//            textFieldValue = viewModel.shortURL.value,
//            onValueChange = { viewModel.shortURL.value = it },
//            label = { androidx.compose.material.Text(text = "URL") },
//            readOnly = true,
//            trailingIcon = {
//                androidx.compose.material.Icon(
//                    imageVector = Icons.Rounded.ContentCopy,
//                    contentDescription = "Copy",
//                    modifier = Modifier
//                        .padding(4.dp)
//                        .clip(RoundedCornerShape(50))
//                        .clickable {}
//                        .padding(8.dp)
//                        .size(24.dp)
//                )
//            }
//        )

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
//                viewModel.addNewURLItem(
//                    viewModel.myURL.value.text,
//                    viewModel.shortURL.value.text,
//                    "10/10",
//                    "qwerty"
//                )
//                Log.d("!!!", viewModel.getItemsSize().toString())
//                viewModel.getItemsSize()
//                viewModel.deleteURLItem(
//                    viewModel.myURL.value.text,
//                    viewModel.shortURL.value.text,
//                    "10/10",
//                    "qwerty"
//                )

//                viewModel.deleteById()

//                viewModel.justDelete()
                Log.d("!!!", "ShortUrlFab: ")
//                val urlItem = viewModel.allUrlItems.collect()
//                viewModel.deleteById()

                viewModel.addNewURLItem(
                    viewModel.myURL.value.text,
                    viewModel.shortURL.value.text,
                    "10/10",
                    "qwerty"
                )
            })

//        val fabHeight = remember {
//            mutableStateOf(470)
//        }

//        viewModel.fabHeight.value = thisDp(fabHeight.value)
    }
}

@Composable
fun thisDp(px: Int): Dp = with(LocalDensity.current) { px.toDp() }

@Composable
fun thisPx(dp: Dp): Int = with(LocalDensity.current) { dp.toPx().toInt() }

class SizeProportion(val heightRang: Dp, val widthRange: Dp)
