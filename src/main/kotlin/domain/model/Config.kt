package domain.model

import com.google.gson.annotations.SerializedName
 import kotlinx.serialization.Serializable

@Serializable
data class Config(
    @SerializedName("expire_in")
    val expire_in: Int?,

    @SerializedName("full_url")
    val full_url: String?,

    @SerializedName("short")
    val short: String?,
)