package com.example.pms.view.suggesstions

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.suggestion_vm.SuggestionsScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.R
import com.example.pms.ui.theme.*
import com.example.pms.viewmodel.presentation_vm.suggestion_vm.SuggestionsEvents

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuggestionMainScreen(
    navController: NavHostController,
    viewModel: SuggestionsScreenVM = viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.suggestion_im),
            contentDescription = null,
            modifier = Modifier
                .size(220.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(id = R.string.how_help),
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.DarkGray,
            fontSize = 20.sp,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        HelpingSection(
            onFindingGarageClicked = {
                viewModel.onEvent(SuggestionsEvents.OnFindingGarageClicked(navController))
            },
            onFindingWashClicked = {
                viewModel.onEvent(SuggestionsEvents.OnFindingWashGarageClicked(navController))
            },
            onNewsClicked = {
                viewModel.onEvent(SuggestionsEvents.OnNewsClicked(navController))
            },
            onReviewCar = {
                viewModel.onEvent(SuggestionsEvents.OnRateCarClicked(navController))
            })

    }
}


@ExperimentalFoundationApi
@Composable
private fun HelpingSection(
    onFindingGarageClicked: () -> Unit,
    onFindingWashClicked: () -> Unit,
    onNewsClicked: () -> Unit,
    onReviewCar: () -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            HelperItem(
                feature = Feature(
                    title = stringResource(id = R.string.finding_garage),
                    iconId = R.drawable.location_icon,
                    BlueViolet1,
                    BlueViolet2,
                    BlueViolet3
                )
            ) {
                onFindingGarageClicked()
            }
        }
        item {
            HelperItem(
                feature = Feature(
                    title = stringResource(id = R.string.finding_wash),
                    iconId = R.drawable.location_icon,
                    LightGreen1,
                    LightGreen2,
                    LightGreen3
                )
            ) {
                onFindingWashClicked()
            }
        }
        item {
            HelperItem(
                feature = Feature(
                    title = stringResource(id = R.string.news_car),
                    iconId = R.drawable.news_ic,
                    OrangeYellow1,
                    OrangeYellow2,
                    OrangeYellow3
                )
            ) {
                onNewsClicked()
            }
        }
        item {
            HelperItem(
                feature = Feature(
                    title = stringResource(id = R.string.rate_car),
                    iconId = R.drawable.review_ic,
                    Beige1,
                    Beige2,
                    Beige3
                )
            ) {
                onReviewCar()
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
                lineHeight = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopStart),
                color = Color.White
            )
            Icon(
                painter = painterResource(id = feature.iconId),
                contentDescription = feature.title,
                tint = Color.White,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Text(
                text = stringResource(id = R.string.start),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        onStartClicked()
                    }
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }
}

