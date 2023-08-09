package com.example.pms.view.estates_screen.estate_home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pms.ui.theme.doublelightblue
import com.example.pms.ui.theme.ligthGrey
import com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm.EstateHomeConstants

@Composable
fun FilteringRowEstate(
    titles: List<EstateHomeConstants.Companion.FilteringType>,
    onSelectedItem: (Int) -> Unit,

    ) {

    val selectedId = remember {
        mutableStateOf(0)
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
                        color = if (selectedId.value == it) doublelightblue
                        else ligthGrey
                    ),
                contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = titles[it].title),
                    color = Color.Black,
                    fontWeight = FontWeight.W100,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }


}
