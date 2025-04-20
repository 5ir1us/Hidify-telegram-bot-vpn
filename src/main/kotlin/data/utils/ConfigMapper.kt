package data.utils

import data.dto.vpn.response.GetInfoLinkSubscriber
import domain.model.Config

object ConfigMapper {
    fun toDomain(dto: GetInfoLinkSubscriber): Config {
        return Config(
            expire_in = dto.expire_in,
            full_url = dto.full_url,
            short = dto.short
        )
    }
}