package com.example.pms.view.vehicles_screen.vehicle_home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pms.ui.theme.lightBlue
import com.example.pms.ui.theme.ligthGrey
import com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm.VehicleHomeConstants
import com.example.pms.R

@Composable
fun FilteringRow(
    titles: List<VehicleHomeConstants.FilteringType>,
    onSelectedItem: (Int) -> Unit,
    currentSelectedItem: Int,
    onAdvanceFiltering: () -> Unit
) {

    val selectedId = remember {
        mutableStateOf(currentSelectedItem)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(onClick = { onAdvanceFiltering() }) {
                Icon(
                    painter = painterResource(id = R.drawable.filter_ic),
                    contentDescription = null
                )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(titles.size) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .clickable {
                            selectedId.value = it
                            onSelectedItem(it)
                        }
                        .background(
                            color = if (selectedId.value == it) lightBlue
                            else ligthGrey
                        ),
                    contentAlignment = Alignment.Center) {
                    Text(
                        text = titles[it].title.name,
                        color = Color.Black,
                        fontWeight = FontWeight.W100,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}

