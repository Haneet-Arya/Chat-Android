package com.haneetarya.mychat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haneetarya.mychat.ui.theme.Background
import com.haneetarya.mychat.ui.theme.MyChatTheme
import com.haneetarya.mychat.ui.theme.TextWhite

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyChatTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Background),
                    contentAlignment = Alignment.Center
                ) {
                    NameForm()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameForm() {
    val context = LocalContext.current
    var name by remember {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(15.dp),
            text = "Enter your Name",
            style = MaterialTheme.typography.bodyLarge
        )
        TextField(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
            value = name, onValueChange = {
                name = it
            },
            label = {
                Text(text = "Name", color = Color.Black)
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            ),
            shape = RoundedCornerShape(8.dp)
        )
        Button(onClick = {
            if(name=="") {
                Toast.makeText(context, "Please Enter your Name", Toast.LENGTH_LONG).show()
                return@Button
            }
            val intent = Intent(context, Chat::class.java)
            intent.putExtra("name",name)
            context.startActivity(intent)
        }, shape = RoundedCornerShape(5.dp),
            ) {
            Text(
                text = "Start Chatting",
                fontSize = 13.sp,
                color = TextWhite
            )
        }
    }
}
