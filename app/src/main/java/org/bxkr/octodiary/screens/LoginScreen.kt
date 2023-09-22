package org.bxkr.octodiary.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.bxkr.octodiary.Diary
import org.bxkr.octodiary.R
import org.bxkr.octodiary.screens.LoginService.LogInWithMosRu
import org.bxkr.octodiary.ui.theme.OctoDiaryTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var loginText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }
    var goAuth by rememberSaveable { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { 2 })
    var currentPage by rememberSaveable { mutableStateOf(Diary.MES) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentPage = Diary.values()[page]
        }
    }

    if (goAuth) LogInWithMosRu(context)

    Column(modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = currentPage.ordinal,
            indicator = @Composable { tabPositions ->
                if (currentPage.ordinal < tabPositions.size) {
                    val width by animateDpAsState(
                        targetValue = tabPositions[currentPage.ordinal].contentWidth,
                        label = "tabIndicatorAnimation"
                    )
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[currentPage.ordinal]),
                        width = width
                    )
                }
            }
        ) {
            Diary.values().forEach {
                Tab(
                    selected = currentPage == it,
                    onClick = {
                        currentPage = it
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(currentPage.ordinal)
                        }
                    },
                    text = { Text(stringResource(id = it.title)) },
                    icon = { Icon(it.icon, stringResource(it.title)) }
                )
            }
        }
        HorizontalPager(state = pagerState) { page ->
            Column(
                Modifier.fillMaxSize(),
                Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .width(TextFieldDefaults.MinWidth)
                        .height(TextFieldDefaults.MinHeight)
                        .align(Alignment.CenterHorizontally)
                        .background(
                            Brush.linearGradient(Diary.values()[page].gradientColors
                                .map { colorResource(it) }), MaterialTheme.shapes.medium
                        )
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { goAuth = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.AutoMirrored.Rounded.OpenInNew,
                        contentDescription = stringResource(id = R.string.log_in_on_mosru),
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                        tint = Color.White
                    )
                    Text(
                        stringResource(id = Diary.values()[page].logInLabel),
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .width(96.dp)
                            .padding(horizontal = 16.dp)
                            .align(Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Text(
                        stringResource(id = R.string.or),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .width(96.dp)
                            .padding(horizontal = 16.dp)
                            .align(Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
                TextField(
                    value = loginText,
                    onValueChange = { loginText = it },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 1.dp),
                    label = { Text(stringResource(id = R.string.username)) },
                    shape = RoundedCornerShape(
                        topStart = MaterialTheme.shapes.medium.topStart,
                        topEnd = MaterialTheme.shapes.medium.topEnd,
                        bottomStart = MaterialTheme.shapes.extraSmall.bottomStart,
                        bottomEnd = MaterialTheme.shapes.extraSmall.bottomEnd
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Unspecified,
                        unfocusedIndicatorColor = Color.Unspecified
                    )
                )
                TextField(
                    value = passwordText,
                    onValueChange = { passwordText = it },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 1.dp),
                    label = { Text(stringResource(id = R.string.password)) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = MaterialTheme.shapes.medium.copy(
                        topStart = MaterialTheme.shapes.extraSmall.topStart,
                        topEnd = MaterialTheme.shapes.extraSmall.topEnd
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Unspecified,
                        unfocusedIndicatorColor = Color.Unspecified
                    )
                )
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(stringResource(id = R.string.log_in))
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    OctoDiaryTheme {
        Surface {
            LoginScreen()
        }
    }
}