package com.urlshotener.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.ProvideWindowInsets
import com.urlshotener.BASE_URL
import com.urlshotener.TEST_URL

@ExperimentalMaterial3Api
@Preview
@Composable
fun MainPage() {
    ProvideWindowInsets {
        Scaffold(
            Modifier.background(MaterialTheme.colorScheme.background),
            floatingActionButton = {
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add")
                }
            }
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "!@#$")
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
    date: String
) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
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
fun Test() {
//    Box(modifier = Modifier.fillMaxSize(),
//    contentAlignment = Alignment.Center)

//    Dialog(onDismissRequest = { /*TODO*/ }) {
        ContentCard(
            originURL = BASE_URL,
            shortURL = TEST_URL,
            description = "QWERTYU",
            date = "10/10"
        )
//    }
}