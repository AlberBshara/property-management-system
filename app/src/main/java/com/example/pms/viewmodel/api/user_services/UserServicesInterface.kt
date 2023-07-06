package com.example.pms.viewmodel.api.user_services

import com.example.pms.model.*
import com.example.pms.viewmodel.api.util.Keys
import com.example.pms.viewmodel.api.util.Urls
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface UserServicesInterface {

    @Multipart
    @POST(Urls.REGISTER_END_POINT)
    suspend fun postRegisterData(
        @Part(Keys.NAME) name: RequestBody,
        @Part(Keys.EMAIL) email: RequestBody,
        @Part(Keys.PASSWORD) password: RequestBody,
        @Part(Keys.PHONE_NUMBER) phoneNumber: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): ResponseBody

    @POST(Urls.LOGIN_END_POINT)
    suspend fun postLoginData(
        @Body user: LoginUserRequest
    ): LoginUserRequest.LoginResponse

    @GET(Urls.LOGOUT_END_POINT)
    suspend fun logOut(
        @Header(Keys.AUTHORIZATION) token: String
    ): LogoutResponse

    @POST(Urls.SEND_EMAIL_FORGET_PASSWORD)
    suspend fun postSendEmailForgetPassword(
        @Body email: ResetPassword.SendEmailRequest
    ): ResetPassword.SendEmailResponse

    @POST(Urls.SEND_CODE_FORGET_PASSWORD)
    suspend fun postSendCodePassword(
        @Body code: ResetPassword.SendCodeRequest
    ): ResetPassword.SendCodeResponse

    @POST(Urls.SEND_RESET_FORGET_PASSWORD)
    suspend fun postSendResetPassword(
        @Body reset_password: ResetPassword.SendResetRequest
    ): ResetPassword.SendResetResponse

    @GET(Urls.PROFILE_END_POINT)
    suspend fun getProfile(
        @Header(Keys.AUTHORIZATION) authToken: String
    ): ProfileData

    @POST(Urls.EDIT_USER_PROFILE_END_POINT)
    suspend fun updateUserInfo(
        @Header(Keys.AUTHORIZATION) token: String,
        @Body updateInfo: EditUserProfileRequest
    ): EditUserProfileResponse

    @Multipart
    @POST(Urls.EDIT_USER_IMAGE_END_POINT)
    suspend fun updateUserImageProfile(
        @Header(Keys.AUTHORIZATION) token: String,
        @Part updateImage: MultipartBody.Part
    ): EditUserImageResponse

    @GET(Urls.GET_USER_PROFILE_END_POINT)
    suspend fun getUserProfile(
        @Header(Keys.AUTHORIZATION) token: String,
    ): GetUserProfileResponse

    @POST(Urls.RESET_PASSWORD_WITH_TOKEN_END_POINT)
    suspend fun resetPasswordWithToken(
        @Header(Keys.AUTHORIZATION) token: String,
        @Body resetPassword: ResetPasswordWithTokenRequest
    ): ResetPasswordWithTokenRequest.ResetPasswordWithTokenResponse


    @POST(Urls.LIKE_END_POINT)
    suspend fun like(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Body likeSenderData: LikeData
    ): LikeData.LikedResponse

    @POST(Urls.MY_FAV_LIST_END_POINT)
    suspend fun fetchMyFavList(
        @Header(Keys.AUTHORIZATION) authToken: String ,
        @Body type : MyFavResponse.MyFavModel
    ) : MyFavResponse

    @POST(Urls.MY_POSTS_END_POINT)
    suspend fun fetchMyVehiclesPosts(
        @Header(Keys.AUTHORIZATION) authToken : String ,
        @Body vehiclesRequest : MyPostsModels.MyVehiclesPostResponse.MyVehiclesPostModel
    ) : MyPostsModels.MyVehiclesPostResponse


}