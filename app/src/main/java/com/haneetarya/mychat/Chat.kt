package com.haneetarya.mychat

import android.app.Activity
import android.os.Bundle
import android.os.Message
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haneetarya.mychat.data.MessageData
import com.haneetarya.mychat.ui.theme.Background
import com.haneetarya.mychat.ui.theme.ChatBlue
import com.haneetarya.mychat.ui.theme.ChatBlueStroke
import com.haneetarya.mychat.ui.theme.ChatOrange
import com.haneetarya.mychat.ui.theme.ChatOrangeStroke
import com.haneetarya.mychat.ui.theme.Header
import com.haneetarya.mychat.ui.theme.MyChatTheme
import com.haneetarya.mychat.ui.theme.TextWhite
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
class Chat : ComponentActivity() {
    private lateinit var mSocket: Socket

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val list = remember {
                mutableStateListOf(
                    MessageData("Start Chatting", user = "Bot"),
                )
            }
            runBlocking {
                launch {
                    mSocket = Util.connect(intent.extras!!.getString("name","Random User"))!!
                    mSocket.on("user-joined") {
                        val data = it[0] as? JSONObject
                        list.add(
                            MessageData(
                                user = data?.getString("name") ?: "",
                                message = "Joined the Chat"
                            )
                        )
                    }
                    mSocket.on("recieve") {
                        val data = it[0] as? JSONObject
                        list.add(
                            MessageData(
                                user = data?.getString("name") ?: "User",
                                message = data?.getString("message") ?: "New Message"
                            )
                        )
                    }
                    mSocket.on("leave") {
                        val data = it[0] as? JSONObject
                        list.add(
                            MessageData(
                                user = data?.getString("name") ?: "User",
                                message = "Left he Chat"
                            )
                        )
                    }
                }
            }
            MyChatTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = Background,
                    bottomBar = {
                        MessageUi(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Header),
                            list
                        )
                    },
                    topBar = {
                        Head()
                    }

                ) {
                    Messages(
                        messageList = list,
                        modifier = Modifier.padding(it)
                    )

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket.disconnect()
    }
}

@Composable
fun Head(modifier: Modifier = Modifier) {

    val activity = LocalContext.current as? Activity
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Header),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                modifier = Modifier
                    .background(Color.Transparent)
                    .width(50.dp)
                    .weight(1f),
                onClick = {
                    activity?.finish()
                }
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(19.dp)
                        .background(Color.Transparent)
                        .padding(end = 4.dp),
                    tint = Color.White
                )
                Text(
                    text = "Back",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(
                modifier = Modifier
                    .width(10.dp)
                    .weight(3f)
            )

        }
        Text(
            text = "Random Chats",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageUi(
    modifier: Modifier = Modifier,
    list: MutableList<MessageData>
) {
    var message by remember {
        mutableStateOf("")
    }
    Row(
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 18.dp),
            value = message,
            onValueChange = {
                message = it
            },
            label = null,
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = TextWhite,
                cursorColor = Color.White,
                disabledLabelColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color(98, 98, 104, 84)

            ),
            placeholder = {
                Text(text = "Enter your Message", color = Color.LightGray)
            },
            trailingIcon = {
                if (message.isNotEmpty()) {
                    IconButton(onClick = {
                        val m = MessageData(
                            message,
                            "You",
                            true
                        )
                        list.add(
                            m
                        )
                        runBlocking {
                            launch {
                                Util.sendMessage(m)
                            }
                        }
                        message = ""
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Send,
                            contentDescription = "Send",
                            tint = Color.White
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun Messages(
    messageList: MutableList<MessageData>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = messageList.size) {
        listState.scrollToItem(messageList.size - 1)
    }
    LazyColumn(
        state = listState,
        userScrollEnabled = true,
        modifier = modifier.padding(top = 20.dp, bottom = 20.dp)
    ) {
        items(messageList.size) {
            MessageItem(message = messageList[it])
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun MessageItem(
    message: MessageData,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = if (message.self) Alignment.End else Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    ) {
        Box(
            modifier = modifier
                .widthIn(min = 100.dp, max = 300.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (message.self) ChatOrange else ChatBlue)
                .border(
                    shape = RoundedCornerShape(10.dp),
                    width = 0.5.dp,
                    color = if (message.self) ChatOrangeStroke else ChatBlueStroke
                )
                .padding(start = 10.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = message.user,
                    color = if (message.self) ChatOrangeStroke else ChatBlueStroke
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = message.message,
                    color = TextWhite
                )
            }
        }
    }
}

