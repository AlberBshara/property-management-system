package com.example.pms.view.settings_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.settings_vm.SettingsScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.R
import com.example.pms.view.utils.DialogLoading
import com.example.pms.viewmodel.presentation_vm.settings_vm.SettingsEvents
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsScreenVM = viewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            ChoseLanguage(
            selectedLanguage = {
                viewModel.onEvent(SettingsEvents.OnLanguageClicked(navController , context , it))
            })},
        modifier = Modifier.fillMaxSize(),
        sheetShape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 50.dp, top = 10.dp,
                        start = 8.dp, end = 8.dp
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(id = R.string.settings),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Start
                )

                TitleItem(
                    title = stringResource(id = R.string.account),
                    icon = R.drawable.account_ic
                )
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                )
                SecondaryItem(text = stringResource(id = R.string.edit_account),
                    onClickListener = {
                        viewModel.onEvent(SettingsEvents.OnEditProfileClicked(navController))
                    })
                SecondaryItem(text = stringResource(id = R.string.change_password),
                    onClickListener = {
                        viewModel.onEvent(SettingsEvents.OnChangePasswordClicked(navController))
                    })
                SecondaryItem(text = stringResource(id = R.string.privacy),
                    onClickListener = {
                        viewModel.onEvent(SettingsEvents.OnPrivacyClicked(navController))
                    })
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )

                TitleItem(
                    title = stringResource(id = R.string.notifications),
                    icon = R.drawable.notifications_ic
                )
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.notifications),
                        color = Color.LightGray,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    Switch(
                        checked = state.turnOffNotifications,
                        onCheckedChange = {
                            viewModel.onEvent(SettingsEvents.OnNotificationsCaseChanged)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Green,
                            uncheckedThumbColor = Color.Red,
                            disabledCheckedThumbColor = Color.Green.copy(alpha = ContentAlpha.disabled),
                            disabledUncheckedThumbColor = Color.Red.copy(alpha = ContentAlpha.disabled),
                        ),
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
                TitleItem(
                    title = stringResource(id = R.string.more),
                    icon = R.drawable.more_ic
                )
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                )
                SecondaryItem(text = stringResource(id = R.string.language),
                    onClickListener = {
                       coroutineScope.launch {
                           if (sheetState.isVisible)
                               sheetState.hide()
                           else
                               sheetState.show()
                       }
                    })
                SecondaryItem(text = stringResource(id = R.string.theme),
                    onClickListener = {
                        viewModel.onEvent(SettingsEvents.OnThemeClicked(navController))
                    })
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                Text(
                    text = stringResource(id = R.string.app_version),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clickable {
                        viewModel.onEvent(SettingsEvents.OnLogoutClicked(context, navController))
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.logout),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }

            DialogLoading(isLoading = state.loadingLogout)

        }
    }
}

@Composable
private fun TitleItem(
    title: String,
    icon: Int
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(
            text = title,
            color = Color.Black,
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Composable
private fun SecondaryItem(
    text: String,
    onClickListener: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .clickable {
                onClickListener()
            }
    ) {
        Text(
            text = text,
            color = Color.LightGray,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(10.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.arrow_forward_ios_ic),
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}
