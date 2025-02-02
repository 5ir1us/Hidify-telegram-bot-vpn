package data.utils

import data.dto.response.GetInfoLinkSubscriber
import domain.model.Config

object ConfigMapper {
    fun toDomain(dto: GetInfoLinkSubscriber): Config {
        return Config(
            domain = dto.doh,
            link = dto.profileUrl,
            name = dto.profileTitle,
            protocol = "unknown",
            security = "unknown",
            transport = "unknown",
            type = "unknown",
            adminMessageHtml = dto.adminMessageHtml,
            adminMessageUrl = dto.adminMessageUrl,
            brandIconUrl = dto.brandIconUrl,
            brandTitle = dto.brandTitle,
            lang = dto.lang,
            profileRemainingDays = dto.profileRemainingDays,
            profileResetDays = dto.profileResetDays,
            profileUsageCurrent = dto.profileUsageCurrent,
            profileUsageTotal = dto.profileUsageTotal,
            speedtestEnable = dto.speedtestEnable,
            telegramBotUrl = dto.telegramBotUrl,
            telegramId = dto.telegramId ?: 0,
            telegramProxyEnable = dto.telegramProxyEnable
        )
    }
}