package org.bxkr.octodiary.widget.button



import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.bxkr.octodiary.Diary
import org.bxkr.octodiary.R
import org.bxkr.octodiary.network.MySchoolLoginService

@Composable
fun GosuslugiLogin(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit) -> Unit
) {
    Context.MODE_PRIVATE
    val context = androidx.compose.ui.platform.LocalContext.current
    Row(
        modifier = modifier
            .width(TextFieldDefaults.MinWidth)
            .height(TextFieldDefaults.MinHeight)
            .background(
                Brush.linearGradient(
                    listOf(
                        colorResource(R.color.blue),
                        colorResource(R.color.red)
                    )
                ), MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                onClick {
                    MySchoolLoginService.logInWithEsia(
                        context,
                        Diary.MES
                    )
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.AutoMirrored.Rounded.OpenInNew,
            contentDescription = stringResource(id = R.string.log_in),
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
            tint = Color.White
        )
        Text(
            stringResource(id = R.string.log_in_on_gosuslugi),
            color = Color.White
        )
    }
}