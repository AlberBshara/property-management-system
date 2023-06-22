package com.example.pms.view.profile_screen.my_fav_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.profile_vm.my_posts_vm.MyPostsScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.R

@Composable
fun MyFavScreen(
    navController : NavHostController,
    viewModel : MyPostsScreenVM = viewModel()
) {
    Column(
        verticalArrangement = Arrangement.Top ,
        horizontalAlignment = Alignment.CenterHorizontally ,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
       Row(
           verticalAlignment = Alignment.CenterVertically ,
           horizontalArrangement = Arrangement.Start ,
           modifier = Modifier
               .fillMaxSize()
               .padding(10.dp)
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
               text = stringResource(id = R.string.my_fav),
               fontWeight = FontWeight.Bold,
               fontSize = 16.sp,
               color = Color.Black,
           )
       }
    }
}