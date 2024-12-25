package com.moksh.data.mappers

import com.moksh.data.entities.local.PaymentModeEntity
import com.moksh.domain.model.request.SavePaymentModeRequest
import com.moksh.domain.model.response.PaymentMode
import com.moksh.domain.usecases.payment_mode.SavePaymentMode

object PaymentModeMapper {
    fun toDto(entity: PaymentModeEntity) = PaymentMode(
        id = entity.id,
        name = entity.name,
        icon = entity.icon,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt
    )

    fun toEntity(dto: PaymentMode) = PaymentModeEntity(
        id = dto.id,
        name = dto.name,
        icon = dto.icon,
        createdAt = dto.createdAt,
        updatedAt = dto.updatedAt
    )

    fun toEntity(dto: SavePaymentModeRequest) = PaymentModeEntity(
        name = dto.name,
        icon = dto.icon,
    )
}

fun PaymentModeEntity.toDto() = PaymentModeMapper.toDto(this)
fun PaymentMode.toEntity() = PaymentModeMapper.toEntity(this)
fun SavePaymentModeRequest.toEntity() = PaymentModeMapper.toEntity(this)