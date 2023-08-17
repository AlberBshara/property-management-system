package com.example.pms.viewmodel.api.user_services

import com.example.pms.model.*
import com.example.pms.viewmodel.api.util.Keys
import com.example.pms.viewmodel.api.util.Urls
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


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


    @POST(Urls.REGISTER_END_POINT)
    suspend fun registerEmail(
        @Body registerEmail : RegisterWithVerification.RegisterEmail
    ) : RegisterEmail.RegisterEmailResponse

    @POST(Urls.VERIFY_EMAIL_CHECK_CODE)
    suspend fun verifyEmailCheckCode(
        @Body checkCode:VerifyEmailCheckCodeRequest
    ):VerifyEmailCheckCodeResponse

    @POST(Urls.SEND_CODE_TO_GMAIL_TO_VERIFY)
    suspend fun sendCodeToGmailToVerify(
        @Body email:SendCodeToGmailRequest
    ):SendCodeTOGmailTResponse



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
    ) : ResponseBody

    @POST(Urls.MY_POSTS_END_POINT)
    suspend fun fetchMyPostsWithType(
        @Header(Keys.AUTHORIZATION) authToken : String ,
        @Body vehiclesRequest : MyPostsModels.MyVehiclesPostResponse.MyVehiclesPostModel
    ) : ResponseBody


    @POST(Urls.EDIT_USER_PROFILE_END_POINT)
    suspend fun editSocialMediaUrl(
        @Header(Keys.AUTHORIZATION) token: String,
        @Body url: EditSocialMediaRequest
    ): EditSocialMediaRequest.EditSocialMediaResponse

    @GET(Urls.GET_OTHER_ESTATES + "/{${Keys.ID}}")
    suspend fun getOtherEstates(
        @Header(Keys.AUTHORIZATION) token : String ,
        @Path(Keys.ID) userId: Int
    ):ProfileOtherEstateResponse

    @GET(Urls.GET_OTHER_CARS + "/{${Keys.ID}}")
    suspend fun getOtherCars(
        @Header(Keys.AUTHORIZATION) token : String ,
        @Path(Keys.ID) userId: Int
    ):ProfileOtherCarResponse

    @GET(Urls.GET_OTHER_PROFILE + "/{${Keys.ID}}")
    suspend fun getOtherProfile(
        @Header(Keys.AUTHORIZATION) token : String ,
        @Path(Keys.ID) userId: Int
    ):ProfileData
}