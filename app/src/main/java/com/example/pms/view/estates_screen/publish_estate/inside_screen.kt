package com.example.pms.view.estates_screen.publish_estate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pms.R
import com.example.pms.ui.theme.lightblue
import com.example.pms.ui.theme.transparentGray
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeConstants
import com.example.pms.viewmodel.presentation_vm.estates_vm.publish_estate_vm.PublishEstateEvents
import com.example.pms.viewmodel.presentation_vm.estates_vm.publish_estate_vm.PublishEstateState
import com.example.pms.viewmodel.presentation_vm.estates_vm.publish_estate_vm.PublishEstateVM

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PublishingEstateForm(
    viewModel: PublishEstateVM,
    state: PublishEstateState
) {

    val context = LocalContext.current


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {


        Text(
            text = stringResource(id = R.string.basic_info),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            TextFiledDropDownOutLined(
                onValueChanged = {
                    viewModel.onEvent(PublishEstateEvents.OnGovernorateTypeChanged(governorate = it))
                },
                label = R.string.governorate,
                listMenuItems = EstateHomeConstants.listOfFiltering[0],
                modifier = Modifier.weight(1f),
                textState = state.enteredData.governorate
            )

        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            TextFiledOutLined(
                onValueChanged = {
                    viewModel.onEvent(PublishEstateEvents.OnAddressChanged(address = it))
                },
                label = R.string.address_view_more,
                modifier = Modifier.weight(1f),
                leadingIcon = R.drawable.location_icon,
                keyboardType = KeyboardType.Text,
                textState = state.enteredData.address
            )

        }


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TextFiledDropDownOutLined(
                onValueChanged = {
                    viewModel.onEvent(PublishEstateEvents.OnEstateTypeChanged(estateType = it))
                },
                label = R.string.estate_type,
                listMenuItems = EstateHomeConstants.listOfFiltering[1],

                modifier = Modifier.weight(1f),
                textState = state.enteredData.estateType
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TextFiledDropDownOutLined(
                onValueChanged = {
                    viewModel.onEvent(PublishEstateEvents.OnOperationTypeChanged(operationType = it))
                },
                label = R.string.Operation_type,
                listMenuItems = EstateHomeConstants.listOfFiltering[2],

                modifier = Modifier.weight(1f),
                textState = state.enteredData.operationType
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TextFiledDropDownOutLined(
                onValueChanged = {
                    viewModel.onEvent(PublishEstateEvents.OnStatusChanged(status = it))
                },
                label = R.string.status_type,
                listMenuItems = EstateHomeConstants.listOfFiltering[3],

                modifier = Modifier.weight(1f),
                textState = state.enteredData.statusOFEstate
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TextFiledOutLined(
                onValueChanged = {
                    viewModel.onEvent(PublishEstateEvents.OnPriceChanged(price = it))
                },
                label = R.string.price,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = R.drawable.money_ic,
                keyboardType = KeyboardType.Number,
                textState = state.enteredData.price
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {


            val readOnly by remember { mutableStateOf(true) }

            OutlinedTextField(
                modifier = Modifier.weight(0.4f),
                value = state.enteredData.space.toInt().toString() + "  m2",
                onValueChange = {


                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = transparentGray,
                    focusedBorderColor = lightblue,
                    unfocusedBorderColor = transparentGray,
                    cursorColor = lightblue
                ),
                shape = RoundedCornerShape(20.dp),
                label = {
                    Text(
                        text = stringResource(id = R.string.space),
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.caption
                    )
                },
                singleLine = true,
                readOnly = readOnly


            )

            Slider(
                value = state.enteredData.space,
                onValueChange = {
                    viewModel.onEvent(PublishEstateEvents.OnSpaceChanged(space = it))
                },
                valueRange = 0f..1000f,
                modifier = Modifier.weight(1f),
                colors = SliderDefaults.colors(
                    thumbColor = lightblue,
                    activeTrackColor = lightblue,
                    inactiveTrackColor = Color.Gray
                )
            )
        }
        AnimatedVisibility(visible = state.enteredData.estateType == "villa" || state.enteredData.estateType == "apartment") {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {

                    TextFiledOutLined(
                        onValueChanged = {
                            viewModel.onEvent(PublishEstateEvents.OnBedsChanged(beds = it))
                        },
                        label = R.string.bed,
                        modifier = Modifier.weight(1f),
                        leadingIcon = R.drawable.bed_icon,
                        keyboardType = KeyboardType.Number,
                        textState = state.enteredData.beds
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextFiledOutLined(
                        onValueChanged = {
                            viewModel.onEvent(PublishEstateEvents.OnBathsChanged(baths = it))
                        },
                        label = R.string.baths,
                        modifier = Modifier.weight(1f),
                        leadingIcon = R.drawable.bathroom_icon,
                        keyboardType = KeyboardType.Number,
                        textState = state.enteredData.baths
                    )


                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TextFiledOutLined(
                        onValueChanged = {
                            viewModel.onEvent(PublishEstateEvents.OnGarageChanged(garage = it))
                        },
                        label = R.string.garage,
                        modifier = Modifier.weight(1f),
                        leadingIcon = R.drawable.garage_icon,
                        keyboardType = KeyboardType.Number,
                        textState = state.enteredData.garage
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextFiledOutLined(
                        onValueChanged = {
                            viewModel.onEvent(PublishEstateEvents.OnLevelsChanged(levels = it))
                        },
                        label = R.string.level,
                        modifier = Modifier.weight(1f),
                        leadingIcon = R.drawable.level_icon,
                        keyboardType = KeyboardType.Number,
                        textState = state.enteredData.levels
                    )
                }
            }

        }

        TextFiledOutLined(
            onValueChanged = {
                viewModel.onEvent(PublishEstateEvents.OnDescriptionChanged(description = it))
            },
            label = R.string.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            leadingIcon = R.drawable.description_ic,
            keyboardType = KeyboardType.Text,
            textState = state.enteredData.description
        )
        AnimatedVisibility(visible = state.dataInvalid) {
            Snackbar(
                backgroundColor = Color.DarkGray,
                elevation = 3.dp,
                modifier = Modifier
                    .padding(10.dp),
                action = {
                    Button(
                        onClick = {
                            viewModel.onEvent(PublishEstateEvents.HideSnackBar)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = lightblue,
                            disabledBackgroundColor = lightblue
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.ok),
                            color = Color.White,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            ) {
                Text(
                    text = stringResource(id = R.string.data_required),
                    color = Color.White,
                    style = MaterialTheme.typography.caption
                )
            }
        }







        Button(
            onClick = {
                viewModel.onEvent(PublishEstateEvents.OnDone(context = context))
            },
            modifier = Modifier.padding(20.dp),
            colors = ButtonDefaults.buttonColors(lightblue)
        )
        {
            Text(text = stringResource(id = R.string.done), color = Color.White, fontSize = 18.sp)

        }


    }


}