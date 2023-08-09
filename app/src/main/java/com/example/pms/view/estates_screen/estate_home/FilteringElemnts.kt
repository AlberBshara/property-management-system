package com.example.pms.view.estates_screen.estate_home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pms.R
import com.example.pms.ui.theme.lightblue
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeConstants
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeEvents
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeState
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeVM

@Composable
fun FilteringElementsScreen(viewModel: EstateHomeVM, state: EstateHomeState) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(15.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFiledDropDownOutLined(
            onValueChanged = {
                viewModel.onEvent(EstateHomeEvents.OnGovernorateTypeChanged(governorate = it))
            },
            label = R.string.governorate,
            listMenuItems = EstateHomeConstants.listOfFiltering[0],
            textState = state.enteredData.governorate
        )

        TextFiledDropDownOutLined(
            onValueChanged = {
                viewModel.onEvent(EstateHomeEvents.OnEstateTypeChanged(estateType = it))
            },
            label = R.string.estate_type,
            listMenuItems = EstateHomeConstants.listOfFiltering[1],

            textState = state.enteredData.estateType
        )

        TextFiledDropDownOutLined(
            onValueChanged = {
                viewModel.onEvent(EstateHomeEvents.OnOperationTypeChanged(operationType = it))
            },
            label = R.string.operation,
            listMenuItems = EstateHomeConstants.listOfFiltering[2],

            textState = state.enteredData.operationType
        )


        TextFiledDropDownOutLined(
            onValueChanged = {
                viewModel.onEvent(EstateHomeEvents.OnStatusChanged(status = it))
            },
            label = R.string.status_type,
            listMenuItems = EstateHomeConstants.listOfFiltering[3],


            textState = state.enteredData.statusOFEstate
        )

        TextFiledOutLined(
            onValueChanged = {
                viewModel.onEvent(EstateHomeEvents.OnPriceDownChanged(price_down = it))
            },
            label = R.string.Price_down_to,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = R.drawable.money_ic,
            keyboardType = KeyboardType.Number,
            textState = state.enteredData.min_price,
            keyboardOptions = ImeAction.Next
        )

        TextFiledOutLined(
            onValueChanged = {
                viewModel.onEvent(EstateHomeEvents.OnPriceUpChanged(price_up = it))
            },
            label = R.string.Price_up_to,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = R.drawable.money_ic,
            keyboardType = KeyboardType.Number,
            textState = state.enteredData.max_price,
            keyboardOptions = ImeAction.Next
        )

        TextFiledOutLined(
            onValueChanged = {
                viewModel.onEvent(EstateHomeEvents.OnSpaceDownChanged(space_down = it))
            },
            label = R.string.Space_down_to,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = R.drawable.space_ic,
            keyboardType = KeyboardType.Number,
            textState = state.enteredData.min_space,
            keyboardOptions = ImeAction.Next
        )

        TextFiledOutLined(
            onValueChanged = {
                viewModel.onEvent(EstateHomeEvents.OnSpaceUpChanged(space_up = it))
            },
            label = R.string.Space_up_to,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = R.drawable.space_ic,
            keyboardType = KeyboardType.Number,
            textState = state.enteredData.max_space,
            keyboardOptions = ImeAction.Next
        )

        TextFiledOutLined(
            onValueChanged = {
                viewModel.onEvent(EstateHomeEvents.OnLevelDownChanged(level_down = it))
            },
            label = R.string.Level_down_to,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = R.drawable.level_icon,
            keyboardType = KeyboardType.Number,
            textState = state.enteredData.min_level,
            keyboardOptions = ImeAction.Next
        )

        TextFiledOutLined(
            onValueChanged = {
                viewModel.onEvent(EstateHomeEvents.OnLevelUpChanged(level_up = it))
            },
            label = R.string.Level_up_to,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = R.drawable.level_icon,
            keyboardType = KeyboardType.Number,
            textState = state.enteredData.max_level,
            keyboardOptions = ImeAction.Done
        )


        Button(
            onClick = {
                viewModel.onEvent(EstateHomeEvents.ShowDropDownFilterAll(open = false))
                viewModel.onEvent(EstateHomeEvents.OnDoneFilter(context = context))

            },
            modifier = Modifier.padding(20.dp),
            colors = ButtonDefaults.buttonColors(lightblue)
        )
        {
            Text(text = stringResource(id = R.string.search), color = Color.White, fontSize = 18.sp)

        }


    }


}