package org.bxkr.octodiary.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.bxkr.octodiary.Diary
import org.bxkr.octodiary.R
import org.bxkr.octodiary.isPackageInstalled
import org.bxkr.octodiary.ui.theme.OctoDiaryTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var currentPageIndex by rememberSaveable { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    var alertTrigger by remember { mutableStateOf(false) }
    var alertAction by remember { mutableStateOf({}) }
    var dropdownExpanded by rememberSaveable { mutableStateOf(false) }

    val coffeeDrinks = arrayOf("Americano", "Cappuccino", "Espresso", "Latte", "Mocha")
    var selectedText by remember { mutableStateOf(coffeeDrinks[0]) }

    if (alertTrigger) {
        ShowAlertIfFoundReceivers {
            alertAction()
            alertTrigger = false
        }
    }

    Column(modifier.fillMaxSize()) {
        ExposedDropdownMenuBox(expanded = dropdownExpanded, onExpandedChange = {
            dropdownExpanded = !dropdownExpanded
        }) {

            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                coffeeDrinks.forEachIndexed { ind, item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            currentPageIndex = ind
                            selectedText = item
                            dropdownExpanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
        Diary.entries[currentPageIndex].alternativeLogIn
        PrimaryTabRow(
            selectedTabIndex = currentPage.ordinal
        ) {
            Diary.entries.forEach {
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
                            Brush.linearGradient(
                                Diary.entries[page].primaryLogGradientColors
                                    .map { colorResource(it) }), MaterialTheme.shapes.medium
                        )
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            alertAction = {
                                Diary.entries[currentPage.ordinal].primaryLogInFunction(context)
                            }
                            alertTrigger = true
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (page == Diary.MES.ordinal) {
                        Image(
                            painterResource(R.drawable.ic_mos_ru),
                            stringResource(id = R.string.log_in),
                            modifier = Modifier
                                .padding(start = 16.dp, end = 8.dp)
                                .width(16.dp)
                        )
                    } else {
                        Icon(
                            Icons.AutoMirrored.Rounded.OpenInNew,
                            contentDescription = stringResource(id = R.string.log_in),
                            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                            tint = Color.White
                        )
                    }
                    Text(
                        stringResource(id = Diary.entries[page].primaryLogInLabel),
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

                Diary.entries[page].alternativeLogIn(
                    Modifier.align(Alignment.CenterHorizontally)
                ) {
                    alertAction = it
                    alertTrigger = true
                }
            }
        }
    }
}

@Composable
fun ShowAlertIfFoundReceivers(onDismiss: () -> Unit) {
    val context = LocalContext.current
    var isShown by remember {
        mutableStateOf(
            true in listOf(
                "ru.mes.dnevnik",
                "ru.mes.dnevnik.fgis"
            ).map { context.packageManager.isPackageInstalled(it) })
    }
    LaunchedEffect(Unit) {
        if (!isShown) onDismiss()
    }
    AnimatedVisibility(isShown) {
        AlertDialog(onDismissRequest = { isShown = false; onDismiss() }, confirmButton = {
            TextButton(onClick = { isShown = false; onDismiss() }) {
                Text(stringResource(R.string.ok))
            }
        }, title = { Text(stringResource(R.string.warning)) }, text = {
            Text(
                stringResource(
                    R.string.auth_receivers_warn, stringResource(R.string.app_name)
                )
            )
        })
    }
}

@Preview(locale = "ru")
@Composable
fun LoginPreview() {
    OctoDiaryTheme {
        Surface {
            LoginScreen()
        }
    }
}