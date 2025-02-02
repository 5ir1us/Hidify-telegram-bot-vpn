package domain.model

data class Config(
    val domain: String,
    val link: String,
    val name: String,
    val protocol: String,
    val security: String,
    val transport: String,
    val type: String,
    val adminMessageHtml: String,
    val adminMessageUrl: String,
    val brandIconUrl: String,
    val brandTitle: String,
    val lang: String,
    val profileRemainingDays: Int,
    val profileResetDays: Int,
    val profileUsageCurrent: Int,
    val profileUsageTotal: Int,
    val speedtestEnable: Boolean,
    val telegramBotUrl: String,
    val telegramId: Int,
    val telegramProxyEnable: Boolean
)