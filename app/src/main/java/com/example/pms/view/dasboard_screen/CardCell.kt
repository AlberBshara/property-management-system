package com.example.pms.view.dasboard_screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pms.ui.theme.transparent_p

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GridCell(
    image: Int,
    text: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .padding(10.dp)
            .size(130.dp),
        elevation = 4.dp,
        backgroundColor = transparent_p,
        onClick = {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(100.dp, 100.dp), Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "icon",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(bottom = 4.dp)
                )
            }
            Text(
                text = stringResource(id = text),
                color = Color.White
            )
        }
    }
}