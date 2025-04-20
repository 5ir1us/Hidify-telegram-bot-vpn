package data.dto.vpn.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequestDto(
    val enable: Boolean,
    @SerializedName("is_active")
    val isActive: Boolean,
    val lang: String,
    val name: String?,
    @SerializedName("package_days")
    val packageDays: Int?,
    @SerializedName("telegram_id")
    val telegramId: Int?,
    @SerializedName("usage_limit_GB")
    val usageLimitGb: Double?
    )
