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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.viewmodel.presentation_vm.chatting_vm.messages_vm.MessagesScreenVM
import  androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.model.dto.ChatMessage
import com.example.pms.model.dto.Sender
import com.example.pms.ui.theme.lightBlue
import com.example.pms.view.animation.shimmerEffect
import com.example.pms.viewmodel.presentation_vm.chatting_vm.messages_vm.MessagesEvents

@Composable
fun MessagesScreen(
    navController: NavHostController,
    viewModel: MessagesScreenVM = viewModel(),
    receiverId: Int, receiverUserName: String ,
    imageURl : String
) {

    val context = LocalContext.current
    viewModel.onEvent(
        MessagesEvents.OnStart(context, receiverId, receiverUserName)
    )
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
            title = state.username,
            description = "",
            pictureUrl = imageURl,
            onUserNameClick = {
            }, onBackArrowClick = {
                navController.popBackStack()
            }, onUserProfilePictureClick = {
            },
            onMoreDropDownBlockUserClick = {
            }
        )
        if (state.isLoading) {
            MessagesShimmerLoading(
                modifier = Modifier.weight(1f)
            )
        } else {
            if (state.chatMessages.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    reverseLayout = true
                ) {
                    items(state.chatMessages.reversed()) {
                        when (it.sender) {
                            Sender.UserA -> SentMessage(it, "")
                            Sender.UserB -> ReceivedMessage(it, "")
                        }
                    }
                }
            } else {
                NoMessagesYetScreen(
                    isAnimating = true,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        InputMessage(
            message = state.currentMessage,
            onSendMessageListener = {
                viewModel.onEvent(MessagesEvents.SendMessage(it, receiverId, context))
            },
            onMessageChanging = {
                viewModel.onEvent(MessagesEvents.OnMessageChanging(it))
            }
        )
    }
}

@Composable
private fun InputMessage(
    modifier: Modifier = Modifier,
    message: String,
    onSendMessageListener: (message: String) -> Unit,
    onMessageChanging: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        TextField(
            value = message,
            onValueChange = {
                onMessageChanging(it)
            },
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
    chatMessage: ChatMessage,
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
                    text = chatMessage.text,
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
            /**
             if (chatMessage.isSending) {
            Icon(
            painter = painterResource(id = R.drawable.is_sending_ic),
            contentDescription = null,
            modifier = Modifier
            .size(14.dp)
            .padding(2.dp)
            .align(Alignment.BottomEnd)
            )
            }
            else if (chatMessage.sentSuccessfully){
            Icon(
            painter = painterResource(id = R.drawable.sending_done_ic),
            contentDescription = null,
            modifier = Modifier
            .size(14.dp)
            .padding(2.dp)
            .align(Alignment.BottomEnd),
            tint = Color.Black
            )
            }
             */
        }
    }
}


@Composable
private fun ReceivedMessage(
    chatMessage: ChatMessage,
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
                    text = chatMessage.text,
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
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
            }
            /**
            if (chatMessage.isSending) {
            Icon(
            painter = painterResource(id = R.drawable.is_sending_ic),
            contentDescription = null,
            modifier = Modifier
            .size(14.dp)
            .align(Alignment.BottomStart)
            )
            }
            else if (chatMessage.sentSuccessfully){
            Icon(
            painter = painterResource(id = R.drawable.sending_done_ic),
            contentDescription = null,
            modifier = Modifier
            .size(14.dp)
            .align(Alignment.BottomStart),
            tint = Color.Green
            )
            }
             */
        }
    }
}


@Composable
private fun MessagesShimmerLoading(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        repeat(10) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 150.dp)
                    .height(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .shimmerEffect()
                    .align(Alignment.Start)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, top = 10.dp, bottom = 10.dp, start = 150.dp)
                    .height(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .shimmerEffect()
                    .align(Alignment.End)
            )
        }
    }
}
