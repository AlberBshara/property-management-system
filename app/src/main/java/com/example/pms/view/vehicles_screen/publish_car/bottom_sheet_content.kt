package com.example.pms.view.vehicles_screen.publish_car

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import com.example.pms.ui.theme.darkBlue
import com.example.pms.ui.theme.lightBlue


@Composable
fun ExpandedBottomSheetContent(
    onAddPhotosListener: () -> Unit,
    onLocationListener: () -> Unit,
    onFileListener: () -> Unit,
    onCameraListener: () -> Unit,
    showIndicator: Boolean = false
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (showIndicator) {
                CircularProgressIndicator(
                    modifier = Modifier.size(28.dp),
                    color = lightBlue
                )
            }
            Text(
                text = stringResource(id = R.string.add_item),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.fillMaxWidth()
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                .clickable {
                    onAddPhotosListener()
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.image_ic),
                contentDescription = null,
                tint = Color.Green
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
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    onCameraListener()
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.camera_ic),
                contentDescription = null,
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = R.string.camera),
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
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    onFileListener()
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.file_ic),
                contentDescription = null,
                tint = Color.Cyan
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
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    onLocationListener()
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.location_ic),
                contentDescription = null,
                tint = Color.Yellow.copy(alpha = 0.6f)
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


@Composable
fun CollapsedBottomSheetContent(
    onAddPhotosListener: () -> Unit,
    onLocationListener: () -> Unit,
    onFileListener: () -> Unit,
    onCameraListener: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                onAddPhotosListener()
            },
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 1.dp, bottom = 1.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.image_ic),
                contentDescription = null,
                tint = Color.Green
            )
        }

        IconButton(
            onClick = {
                onCameraListener()
            },
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 1.dp, bottom = 1.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.camera_ic),
                contentDescription = null,
                tint = Color.Red
            )
        }

        IconButton(
            onClick = {
                onFileListener()
            },
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 1.dp, bottom = 1.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.file_ic),
                contentDescription = null,
                tint = Color.Cyan
            )
        }
        IconButton(
            onClick = {
                onLocationListener()
            },
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 1.dp, bottom = 1.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.location_ic),
                contentDescription = null,
                tint = Color.Yellow.copy(alpha = 0.6f)
            )
        }
    }
}