package com.example.pms.view.vehicles_screen.publish_car


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.ui.theme.*
import com.example.pms.view.animation.CircularSlider
import com.example.pms.view.utils.YearDialogPicker
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.publish_vehicle_vm.PublishVehicleEvents
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.publish_vehicle_vm.PublishVehicleState
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.publish_vehicle_vm.PublishVehicleVM

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun PublishingVehicleForm(
    navController: NavHostController,
    viewModel: PublishVehicleVM,
    state: PublishVehicleState
) {

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
            PMSDropDownOutLinedTextFiled(
                initialValue = state.enteredData.listingType,
                onValueChanged = {
                    viewModel.onEvent(PublishVehicleEvents.OnListingTypeChanged(it))
                },
                label = R.string.listingType,
                listMenuItems = listOf(
                    stringResource(id = R.string.sell),
                    stringResource(id = R.string.rent)
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            PMSDropDownOutLinedTextFiled(
                initialValue = state.enteredData.transmissionType,
                onValueChanged = {
                    viewModel.onEvent(PublishVehicleEvents.OnTransmissionChanged(it))
                },
                label = R.string.transmission_type,
                listMenuItems = listOf(
                    stringResource(id = R.string.manually),
                    stringResource(id = R.string.automatic)
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            PMSDropDownOutLinedTextFiled(
                initialValue = state.enteredData.brand,
                onValueChanged = {
                    viewModel.onEvent(PublishVehicleEvents.OnBrandChanged(it))
                },
                label = R.string.brand,
                listMenuItems = listOf("BMS", "Sham", "asd", "Kia", "other"),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            PMSOutLinedTextField(
                initialValue = state.enteredData.secondaryBrand,
                onValueChanged = {
                    viewModel.onEvent(PublishVehicleEvents.OnSecondaryBrandChanged(it))
                }, label = R.string.secondaryBrand,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                keyboardType = KeyboardType.Text
            )
        }
        PMSOutLinedTextField(
            initialValue = state.enteredData.price.toString(),
            onValueChanged = {
                if (it.isNotEmpty()) {
                    viewModel.onEvent(PublishVehicleEvents.OnPriceChanged(it.toFloat()))
                }
            }, label = R.string.price,
            keyboardType = KeyboardType.Number,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            leadingIcon = R.drawable.price_ic
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularSlider(
                modifier = Modifier.size(180.dp),
                thumbColor = lightBlue,
                progressColor = lightBlue,
            ) {
                viewModel.onEvent(PublishVehicleEvents.OnKiloMeterChanged(it))
            }
            Text(
                text = "${state.enteredData.kilometer} " + stringResource(id = R.string.km),
                style = MaterialTheme.typography.caption,
                color = Color.Black
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            PMSDropDownOutLinedTextFiled(
                initialValue = state.enteredData.governorate,
                onValueChanged = {
                   viewModel.onEvent(PublishVehicleEvents.OnGovernorateChanged(it))
                },
                label = R.string.governorate,
                listMenuItems = listOf(),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedTextField(
                value = state.enteredData.location,
                onValueChange = {
                    viewModel.onEvent(PublishVehicleEvents.OnLocationChanged(it))
                },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = transparentGray,
                    focusedBorderColor = lightBlue,
                    unfocusedBorderColor = transparentGray,
                    cursorColor = lightBlue
                ),
                shape = RoundedCornerShape(20.dp),
                label = {
                    Text(
                        text = stringResource(id = R.string.location),
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.caption
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.location_ic),
                        contentDescription = null
                    )
                }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            YearDialogPicker(
                showDialog = state.showYearDialogPicker,
                selectedYear = {
                    viewModel.onEvent(PublishVehicleEvents.OnManufactureYearChanged(it))
                    viewModel.onEvent(PublishVehicleEvents.ShowYearDialogPicker)
                },
                onDismissRequest = {
                    viewModel.onEvent(PublishVehicleEvents.ShowYearDialogPicker)
                },
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = {
                            viewModel.onEvent(PublishVehicleEvents.ShowYearDialogPicker)
                        }
                    ),
                OnLeadingIcon = {
                    viewModel.onEvent(PublishVehicleEvents.ShowYearDialogPicker)
                })

            Spacer(modifier = Modifier.width(10.dp))
            PMSDropDownOutLinedTextFiled(
                initialValue = state.enteredData.condition,
                onValueChanged = {
                    viewModel.onEvent(PublishVehicleEvents.OnConditionChanged(it))
                },
                label = R.string.condition,
                listMenuItems = listOf(
                    stringResource(id = R.string.new_vehicle),
                    stringResource(id = R.string.used_vehicle)
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PMSDropDownOutLinedTextFiled(
                initialValue = state.enteredData.color,
                onValueChanged = {
                    viewModel.onEvent(PublishVehicleEvents.OnColorChanged(it))
                },
                label = R.string.color,
                listMenuItems = listOf(*stringArrayResource(id = R.array.colors_list)),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))

            PMSDropDownOutLinedTextFiled(
                initialValue = state.enteredData.fuelType,
                onValueChanged = {
                    viewModel.onEvent(PublishVehicleEvents.OnFuelTypeChanged(it))
                },
                label = R.string.fuel_type,
                listMenuItems = listOf(
                    stringResource(id = R.string.gasoline),
                    stringResource(id = R.string.hybrid),
                    stringResource(id = R.string.diesel),
                    stringResource(id = R.string.electric)
                ),
                modifier = Modifier.weight(1f)
            )

        }

        PMSOutLinedTextField(
            initialValue = state.enteredData.description,
            onValueChanged = {
                viewModel.onEvent(PublishVehicleEvents.OnDescriptionChanged(it))
            },
            label = R.string.description,
            keyboardType = KeyboardType.Text,
            leadingIcon = R.drawable.description_ic,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = false
        )


        Button(
            onClick = {
                viewModel.onEvent(PublishVehicleEvents.Submit)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = lightBlue,
                disabledBackgroundColor = lightBlue
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 26.dp, end = 26.dp,
                    top = 10.dp, bottom = 10.dp
                ),
        ) {
            Text(
                text = stringResource(id = R.string.submit),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.button,
                modifier = Modifier
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}


