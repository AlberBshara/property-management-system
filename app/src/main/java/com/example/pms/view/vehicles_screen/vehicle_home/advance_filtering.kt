package com.example.pms.view.vehicles_screen.vehicle_home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm.VehicleHomeConstants
import com.example.pms.R
import com.example.pms.ui.theme.lightBlue


@Composable
fun AdvanceFiltering(
    isFiltering: Boolean,
    onCancelDialog: () -> Unit
) {

    if (isFiltering) {
        val context = LocalContext.current
        val selectedFilteringItem: Map<String, String> = mutableMapOf()

        var colorShowing by remember {
            mutableStateOf(false)
        }
        var brandShowing by remember {
            mutableStateOf(false)
        }
        var conditionShowing by remember {
            mutableStateOf(false)
        }
        var operationShowing by remember {
            mutableStateOf(false)
        }
        var drivingForceShowing by remember {
            mutableStateOf(false)
        }
        var transmissionShowing by remember {
            mutableStateOf(false)
        }


        Dialog(
            onDismissRequest = {
                onCancelDialog()
            },
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = Color.White),
                contentAlignment = Alignment.TopCenter
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "",
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .padding(start = 10.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = stringResource(id = R.string.filter_guidlines),
                        style = MaterialTheme.typography.subtitle2,
                        color = Color.LightGray,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 4.dp)
                    )

                }
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 39.dp, bottom = 42.dp)
                        .fillMaxSize()
                )
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    IconButton(onClick = {
                                        colorShowing = !colorShowing
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.arrow_downward_ic),
                                            contentDescription = null
                                        )
                                    }
                                    Text(
                                        text = stringResource(id = R.string.color),
                                        style = MaterialTheme.typography.subtitle1,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                                        textAlign = TextAlign.Start
                                    )
                                }
                                Divider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 10.dp)
                                        .fillMaxWidth()
                                )
                            }
                            if (colorShowing) {
                                items(VehicleHomeConstants(context).listOfFiltering[6].items) { item ->
                                    var colorState by remember {
                                        mutableStateOf(false)
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(start = 10.dp, end = 10.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = item,
                                            color = Color.DarkGray,
                                            style = MaterialTheme.typography.body2
                                        )
                                        Checkbox(
                                            checked = colorState,
                                            onCheckedChange = {
                                                colorState = it

                                            },
                                            modifier = Modifier
                                                .scale(0.8f)
                                                .padding(start = 10.dp, end = 10.dp)
                                        )
                                    }
                                }
                            }
                        }
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    IconButton(onClick = {
                                        brandShowing = !brandShowing
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.arrow_downward_ic),
                                            contentDescription = null
                                        )
                                    }
                                    Text(
                                        text = stringResource(id = R.string.brand),
                                        style = MaterialTheme.typography.subtitle1,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                                        textAlign = TextAlign.Start
                                    )
                                    IconButton(onClick = {
                                        brandShowing = !brandShowing
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.arrow_downward_ic),
                                            contentDescription = null
                                        )
                                    }
                                }
                                Divider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 10.dp)
                                        .fillMaxWidth()
                                )
                            }
                            if (brandShowing) {
                                items(VehicleHomeConstants(context).listOfFiltering[5].items) { item ->
                                    var brandState by remember {
                                        mutableStateOf(false)
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(start = 10.dp, end = 10.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = item,
                                            color = Color.DarkGray,
                                            style = MaterialTheme.typography.body2
                                        )
                                        Checkbox(
                                            checked = brandState,
                                            onCheckedChange = {
                                                brandState = it
                                            },
                                            modifier = Modifier
                                                .scale(0.8f)
                                                .padding(start = 10.dp, end = 10.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    IconButton(onClick = {
                                        operationShowing = !operationShowing
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.arrow_downward_ic),
                                            contentDescription = null
                                        )
                                    }
                                    Text(
                                        text = stringResource(id = R.string.operation),
                                        style = MaterialTheme.typography.subtitle1,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                                        textAlign = TextAlign.Start
                                    )
                                }
                                Divider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 10.dp)
                                        .fillMaxWidth()
                                )
                            }
                            if (operationShowing) {
                                items(VehicleHomeConstants(context).listOfFiltering[1].items) { item ->
                                    var operationState by remember {
                                        mutableStateOf(false)
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(start = 10.dp, end = 10.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = item,
                                            color = Color.DarkGray,
                                            style = MaterialTheme.typography.body2
                                        )
                                        Checkbox(
                                            checked = operationState,
                                            onCheckedChange = {
                                                operationState = it
                                            },
                                            modifier = Modifier
                                                .scale(0.8f)
                                                .padding(start = 10.dp, end = 10.dp)
                                        )
                                    }
                                }
                            }

                        }
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    IconButton(onClick = {
                                        conditionShowing = !conditionShowing
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.arrow_downward_ic),
                                            contentDescription = null
                                        )
                                    }
                                    Text(
                                        text = stringResource(id = R.string.condition),
                                        style = MaterialTheme.typography.subtitle1,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                                        textAlign = TextAlign.Start
                                    )
                                }
                                Divider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 10.dp)
                                        .fillMaxWidth()
                                )
                            }
                            if (conditionShowing) {
                                items(VehicleHomeConstants(context).listOfFiltering[7].items) { item ->
                                    var conditionState by remember {
                                        mutableStateOf(false)
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(start = 10.dp, end = 10.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = item,
                                            color = Color.DarkGray,
                                            style = MaterialTheme.typography.body2
                                        )
                                        Checkbox(
                                            checked = conditionState,
                                            onCheckedChange = {
                                                conditionState = it
                                            },
                                            modifier = Modifier
                                                .scale(0.8f)
                                                .padding(start = 10.dp, end = 10.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    IconButton(onClick = {
                                        transmissionShowing = !transmissionShowing
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.arrow_downward_ic),
                                            contentDescription = null
                                        )
                                    }
                                    Text(
                                        text = stringResource(id = R.string.transmission_type),
                                        style = MaterialTheme.typography.subtitle1,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                                        textAlign = TextAlign.Start
                                    )
                                }
                                Divider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 10.dp)
                                        .fillMaxWidth()
                                )
                            }
                            if (transmissionShowing) {
                                items(VehicleHomeConstants(context).listOfFiltering[2].items) { item ->
                                    var transmissionState by remember {
                                        mutableStateOf(false)
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(start = 10.dp, end = 10.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = item,
                                            color = Color.DarkGray,
                                            style = MaterialTheme.typography.body2
                                        )
                                        Checkbox(
                                            checked = transmissionState,
                                            onCheckedChange = {
                                                transmissionState = it
                                            },
                                            modifier = Modifier
                                                .scale(0.8f)
                                                .padding(start = 10.dp, end = 10.dp)
                                        )
                                    }
                                }
                            }

                        }
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    IconButton(onClick = {
                                        drivingForceShowing = !drivingForceShowing
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.arrow_downward_ic),
                                            contentDescription = null
                                        )
                                    }
                                    Text(
                                        text = stringResource(id = R.string.deriving_force),
                                        style = MaterialTheme.typography.subtitle1,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                                        textAlign = TextAlign.Start
                                    )
                                }
                                Divider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 10.dp)
                                        .fillMaxWidth()
                                )
                            }
                            if (drivingForceShowing) {
                                items(VehicleHomeConstants(context).listOfFiltering[4].items) { item ->
                                    var drivingForceState by remember {
                                        mutableStateOf(false)
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(start = 10.dp, end = 10.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = item,
                                            color = Color.DarkGray,
                                            style = MaterialTheme.typography.body2
                                        )
                                        Checkbox(
                                            checked = drivingForceState,
                                            onCheckedChange = {
                                                drivingForceState = it
                                            },
                                            modifier = Modifier
                                                .scale(0.8f)
                                                .padding(start = 10.dp, end = 10.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .padding(bottom = 5.dp)
                        .align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = lightBlue,
                        disabledBackgroundColor = lightBlue
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.ok),
                        color = Color.White,
                        style = MaterialTheme.typography.button,
                    )
                }
            }
        }
    }
}




