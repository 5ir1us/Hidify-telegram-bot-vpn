package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllConfigUser(
    @SerialName("added_by_uuid")
    val addedByUuid: String?,

    val comment: String?,

    @SerialName("current_usage_GB")
    val currentUsageGB: Double?,

    @SerialName("ed25519_private_key")
    val ed25519PrivateKey: String?,

    @SerialName("ed25519_public_key")
    val ed25519PublicKey: String?,

    val enable: Boolean,
    val id: Int,

    @SerialName("is_active")
    val isActive: Boolean,

    val lang: String?,

    @SerialName("last_online")
    val lastOnline: String?,

    @SerialName("last_reset_time")
    val lastResetTime: String?,

    val mode: String?,
    val name: String?,

    @SerialName("package_days")
    val packageDays: Int?,

    @SerialName("start_date")
    val startDate: String?,

    @SerialName("telegram_id")
    val telegram_id: Long?,

    @SerialName("usage_limit_GB")
    val usageLimitGB: Int?,

    val uuid: String?,

    @SerialName("wg_pk")
    val wgPrivateKey: String?,

    @SerialName("wg_psk")
    val wgPreSharedKey: String?,

    @SerialName("wg_pub")
    val wgPublicKey: String?
)
