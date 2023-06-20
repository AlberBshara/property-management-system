package com.example.pms.view.dasboard_screen


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.viewmodel.presentation_vm.dashboard_vm.DashboardScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.ui.theme.*
import com.example.pms.view.suggesstions.Feature
import com.example.pms.view.suggesstions.standardQuadFromTo
import com.example.pms.viewmodel.presentation_vm.dashboard_vm.DashboardEvents

@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardScreenVM = viewModel()
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
                .weight(0.2f)
        ) {
            Text(
                text = stringResource(id = R.string.pm),
                fontSize = 40.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive
            )
            Text(
                text = stringResource(id = R.string.system),
                fontSize = 40.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    HelperItem(
                        feature = Feature(
                            title = stringResource(id = R.string.car_vector),
                            iconId = R.drawable.car_vector,
                            BlueViolet1,
                            BlueViolet2,
                            BlueViolet3
                        )
                    ) {
                        viewModel.onEvent(DashboardEvents.OnVehicleClicked(navController))
                    }
                }
                item {
                    HelperItem(
                        feature = Feature(
                            title = stringResource(id = R.string.estate_vector),
                            iconId = R.drawable.house_vector,
                            LightGreen1,
                            LightGreen2,
                            LightGreen3
                        )
                    ) {
                        viewModel.onEvent(DashboardEvents.OnEstateClicked(navController))
                    }
                }
                item {
                    HelperItem(
                        feature = Feature(
                            title = stringResource(id = R.string.settings_vector),
                            iconId = R.drawable.settings_vector,
                            OrangeYellow1,
                            OrangeYellow2,
                            OrangeYellow3
                        )
                    ) {
                        viewModel.onEvent(DashboardEvents.OnSettingClicked(navController))
                    }
                }
                item {
                    HelperItem(
                        feature = Feature(
                            title = stringResource(id = R.string.suggestion_vector),
                            iconId = R.drawable.suggestion_vector,
                            Beige1,
                            Beige2,
                            Beige3
                        )
                    ) {
                        viewModel.onEvent(DashboardEvents.OnSuggestionClicked(navController))
                    }
                }
                item {
                    HelperItem(
                        feature = Feature(
                            title = stringResource(id = R.string.profile_vector),
                            iconId = R.drawable.profile_vector,
                            Red1,
                            Red2,
                            Red3
                        )
                    ) {
                        viewModel.onEvent(DashboardEvents.OnProfileClicked(navController))
                    }
                }
                item {
                    HelperItem(
                        feature = Feature(
                            title = stringResource(id = R.string.aboutus_vector),
                            iconId = R.drawable.aboutus_vector,
                            Blue1,
                            Blue2,
                            Blue3
                        )
                    ) {
                        viewModel.onEvent(DashboardEvents.OnAboutUsClicked(navController))
                    }
                }
            }
        }

    }
}


@Composable
private fun HelperItem(
    feature: Feature,
    onStartClicked: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(feature.darkColor)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        // Medium colored path
        val mediumColoredPoint1 = Offset(0f, height * 0.3f)
        val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
        val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
        val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
        val mediumColoredPoint5 = Offset(width * 1.4f, -height.toFloat())

        val mediumColoredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
            standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
            standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
            standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
            standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }

        // Light colored path
        val lightPoint1 = Offset(0f, height * 0.35f)
        val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

        val lightColoredPath = Path().apply {
            moveTo(lightPoint1.x, lightPoint1.y)
            standardQuadFromTo(lightPoint1, lightPoint2)
            standardQuadFromTo(lightPoint2, lightPoint3)
            standardQuadFromTo(lightPoint3, lightPoint4)
            standardQuadFromTo(lightPoint4, lightPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawPath(
                path = mediumColoredPath,
                color = feature.mediumColor
            )
            drawPath(
                path = lightColoredPath,
                color = feature.lightColor
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = feature.title,
                style = MaterialTheme.typography.subtitle1,
                lineHeight = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopCenter),
                color = Color.White
            )
            Icon(
                painter = painterResource(id = feature.iconId),
                contentDescription = feature.title,
                tint = Color.White,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Icon(
                painter = painterResource(id = R.drawable.arrow_forward_ios_ic),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        onStartClicked()
                    }
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }
}

