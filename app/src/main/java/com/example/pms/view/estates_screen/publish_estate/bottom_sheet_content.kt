package com.example.pms.view.estates_screen.publish_estate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pms.R
import com.example.pms.ui.theme.lightblue


@Composable
fun ExpandedBottomSheetContent(
    onAddPhotosListener: () -> Unit,
    onLocationListener: () -> Unit,
    onFileListener: () -> Unit,
    onCameraListener: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.add_item),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,

                )
            Icon(
                painter = painterResource(id = R.drawable.arrow_upward_ic),
                contentDescription = null,
                modifier = Modifier.padding(start = 20.dp)
            )

        }


        Spacer(modifier = Modifier.height(20.dp))
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)
        )


        Row {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(start = 20.dp)
                        .clickable {
                            onAddPhotosListener()
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.image_ic),
                        contentDescription = null,
                        tint = lightblue
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(id = R.string.photos),
                        color = Color.Black,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.W100
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(start = 20.dp)
                        .clickable {
                            onCameraListener()
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.camera_ic),
                        contentDescription = null,
                        tint = lightblue
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(id = R.string.camera),
                        color = Color.Black,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.W100
                    )
                }

            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(start = 20.dp)
                        .clickable {
                            onFileListener()
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.file_ic),
                        contentDescription = null,
                        tint = lightblue
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(id = R.string.files),
                        color = Color.Black,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.W100
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(start = 20.dp)
                        .clickable {
                            onLocationListener()
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.location_icon),
                        contentDescription = null,
                        tint = lightblue

                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(id = R.string.location),
                        color = Color.Black,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.W100
                    )
                }
            }
        }

    }
}


