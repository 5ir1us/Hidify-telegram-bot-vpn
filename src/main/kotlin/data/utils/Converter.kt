package data.utils

import data.dto.payment.RecipientDto
import data.dto.payment.AmountDto
import data.dto.payment.ConfirmationResponseDto
import data.dto.payment.PaymentDto
import data.dto.payment.webhook.WebhookDto
import data.dto.vpn.response.AllConfigUserDto
import data.dto.vpn.response.UserDto
import domain.model.Amount
import domain.model.Confirmation
import domain.model.AllConfigUser
import domain.model.Payment
import domain.model.Recipient
import domain.model.User
import domain.model.Webhook


object UserMapper {


    fun toUser(userDto: UserDto): User {
        return User(
            addedByUuid = userDto.addedByUuid,
            comment = userDto.comment,
            currentUsageGB = userDto.currentUsageGB,
            ed25519PrivateKey = userDto.ed25519PrivateKey,
            ed25519PublicKey = userDto.ed25519PublicKey,
            enable = userDto.enable,
            id = userDto.id,
            isActive = userDto.isActive,
            lang = userDto.lang,
            lastOnline = userDto.lastOnline,
            lastResetTime = userDto.lastResetTime,
            mode = userDto.mode,
            name = userDto.name,
            packageDays = userDto.packageDays,
            startDate = userDto.startDate,
            telegramId = userDto.telegramId,
            usageLimitGB = userDto.usageLimitGB,
            uuid = userDto.uuid,
            wgPk = userDto.wgPk,
            wgPsk = userDto.wgPsk,
            wgPub = userDto.wgPub
        )
    }
    fun fromPayment (paymentDto: PaymentDto): Payment{
        return Payment(
            id = paymentDto.id,
            status = paymentDto.status,
            amount = toAmount(paymentDto.amount),
            recipient = paymentDto.recipient?.toRecipient(),
            createdAt = paymentDto.createdAt,
            test = paymentDto.test,
            paid = paymentDto.paid,
            refundable = paymentDto.refundable,
            confirmation = paymentDto.confirmation?.toConfirmation()
        )
    }

    fun fromWebhook(webhook: WebhookDto): Webhook{
        return Webhook(
            event = webhook.event,
            id = webhook.id,
            url = webhook.url
        )
    }

    fun toAmount (amountDto: AmountDto): Amount {
        return Amount(
            currency = amountDto.currency,
            value = amountDto.value
        )
    }
    fun ConfirmationResponseDto.toConfirmation (): Confirmation{
        return Confirmation(
            type = this.type,
            returnUrl = this.confirmation_url,
            confirmation_url = null
        )
    }
    fun RecipientDto.toRecipient(): Recipient {
        return Recipient(
            accountId = this.accountId,
            gatewayId = this.gatewayId
        )
    }

    fun AllConfigUserDto.toAllConf (): AllConfigUser {
        return AllConfigUser(
            allUsers = this.allUsers.map {toUser(it)}
        )
    }

}