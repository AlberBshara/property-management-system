package com.example.pms.view.chatting_screen.messages_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.viewmodel.presentation_vm.chatting_vm.messages_vm.MessagesScreenVM
import  androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.ui.theme.lightBlue
import com.example.pms.viewmodel.presentation_vm.chatting_vm.messages_vm.MessagesState.Sender

@Composable
fun MessagesScreen(
    navController: NavHostController,
    viewModel: MessagesScreenVM = viewModel()
) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .focusable()
            .wrapContentHeight()
            .imePadding(),
        verticalArrangement = Arrangement.Top
    ) {
        ChatAppBar(
            title = "alber bshara",
            description = "online",
            onUserNameClick = {
            }, onBackArrowClick = {
                navController.popBackStack()
            }, onUserProfilePictureClick = {
            },
            onMoreDropDownBlockUserClick = {
            }
        )
        if (state.chatMessages.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(state.chatMessages) {
//                val sdf = remember {
//                    java.text.SimpleDateFormat("hh:mm", Locale.ROOT)
//                }
                    when (it.sender) {
                        Sender.UserA -> SentMessage(it.text, "10:30")
                        Sender.UserB -> ReceivedMessage(it.text, "10:40")
                    }
                }
            }
        } else {
            NoMessagesYetScreen(isAnimating = true)
        }
        InputMessage(onSendMessageListener = {})
    }
}

@Composable
private fun InputMessage(
    modifier: Modifier = Modifier,
    onSendMessageListener: (message: String) -> Unit
) {
    var message by remember {
        mutableStateOf("")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        TextField(
            value = message,
            onValueChange = { message = it },
            placeholder = {
                Text(
                    text = stringResource(R.string.message),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Center
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.LightGray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.LightGray,
                unfocusedLabelColor = Color.LightGray,
                cursorColor = lightBlue
            ),
            modifier = Modifier
                .weight(0.8f),
            shape = CircleShape,
            maxLines = 2,
        )
        FloatingActionButton(
            onClick = {
                onSendMessageListener(message)
            },
            shape = CircleShape,
            backgroundColor = lightBlue
        ) {
            Icon(
                imageVector = if (message.isEmpty()) Icons.Filled.Mic
                else Icons.Filled.Send,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
private fun SentMessage(
    message: String,
    time: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = lightBlue,
                    shape = RoundedCornerShape(12.dp)
                )
                .align(Alignment.CenterEnd)
        ) {
           Column(
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.End

           ) {
               Text(
                   text = message,
                   style = MaterialTheme.typography.body1,
                   color = Color.White,
                   textAlign = TextAlign.End,
                   modifier = Modifier
                       .padding(top = 5.dp, bottom = 2.dp, start = 10.dp, end = 10.dp)
               )
               Text(
                   text = time,
                   color = Color.White,
                   style = MaterialTheme.typography.caption,
                   modifier = Modifier.padding(start = 10.dp, end = 10.dp)
               )
           }
        }
    }
}


@Composable
private fun ReceivedMessage(
    message: String,
    time: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                )
                .align(Alignment.CenterStart)
        ) {
           Column(
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.Start
           ) {
               Text(
                   text = message,
                   style = MaterialTheme.typography.body1,
                   color = Color.White,
                   textAlign = TextAlign.Start,
                   modifier = Modifier
                       .padding(top = 5.dp, bottom = 2.dp, start = 10.dp, end = 10.dp)
               )
               Text(
                   text = time,
                   color = Color.White,
                   style = MaterialTheme.typography.caption,
                   modifier = Modifier.padding(start = 10.dp , end = 10.dp)
               )
           }
        }
    }
}

