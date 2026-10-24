package com.example.pr5tarasova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pr5tarasova.ui.theme.PR5TarasovaTheme

// Модель данных
data class Message(val author: String, val body: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PR5TarasovaTheme {
                // ЖЕЛЕЗНО указываем цвет фона из нашей темы, чтобы экран стал СИНИМ
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    var isExpanded by remember { mutableStateOf(false) }

    // При клике цвет "облачка" меняется на primary (в нашем случае - черный)
    val surfaceColor by animateColorAsState(
        if (isExpanded) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surface,
    )

    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                // Рамка вокруг аватарки (черная)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = msg.author,
                // Имя автора (черное)
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .clickable { isExpanded = !isExpanded }
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium,
                    // Важно: если облачко стало черным при клике, текст внутри должен стать белым
                    color = if (isExpanded) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

object SampleData {
    val conversationSample = listOf(
        Message("Lexi", "Test... Test... Test..."),
        Message("Lexi", "List of Android versions:\nAndroid KitKat (API 19)\nAndroid Lollipop (API 21)\nAndroid Marshmallow (API 23)\nAndroid Nougat (API 24)\nAndroid Oreo (API 26)\nAndroid Pie (API 28)\nAndroid 10 (API 29)\nAndroid 11 (API 30)\nAndroid 12 (API 31)"),
        Message("Lexi", "I think Kotlin is my favorite programming language.\nIt's so much fun!"),
        Message("Lexi", "Searching for alternatives to XML layouts..."),
        Message("Lexi", "Hey, take a look at Jetpack Compose, it's great!\nIt's the Android's modern toolkit for building native UI.\nIt simplifies and accelerates UI development on Android.\nLess code, powerful tools, and intuitive Kotlin APIs :)"),
        Message("Lexi", "It's available from API 21+ :)"),
        Message("Lexi", "Writing Kotlin for UI seems so natural, Compose where have you been all my life?")
    )
}

@Preview
@Composable
fun PreviewConversation() {
    PR5TarasovaTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Conversation(SampleData.conversationSample)
        }
    }
}