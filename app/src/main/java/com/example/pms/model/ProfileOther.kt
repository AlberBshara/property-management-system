package com.example.pms.model

data class ProfileOtherEstateResponse(
    val posts:List<GetAllEstateResponse.PostData>
)

data class ProfileOtherCarResponse(
    val posts: List<MyFavResponse.PostData>
)