package data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class GetInfoLinkSubscriber(
    val adminMessageHtml: String,
    val adminMessageUrl: String,
    val brandIconUrl: String,
    val brandTitle: String,
    val doh: String,
    val lang: String,
    val profileRemainingDays: Int,
    val profileResetDays: Int,
    val profileTitle: String,
    val profileUrl: String,
    val profileUsageCurrent: Int,
    val profileUsageTotal: Int,
    val speedtestEnable: Boolean,
    val telegramBotUrl: String,
    val telegramId: Int,
    val telegramProxyEnable: Boolean
)