package com.moksh.data.mappers

import com.moksh.data.entities.local.SavingsEntity
import com.moksh.domain.model.request.InsertSaving
import com.moksh.domain.model.response.Savings

object SavingsMapper {
    fun toDto(entity: SavingsEntity): Savings {
        return Savings(
            id = entity.id,
            name = entity.name,
            targetAmount = entity.targetAmount,
            currentAmount = entity.currentAmount,
            endDate = entity.endDate,
            progressBarColor = entity.progressBarColor,
            notes = entity.notes,
            isActive = entity.isActive,
            isSynced = entity.isSynced,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
        )
    }

    fun toEntity(dto: Savings): SavingsEntity {
        return SavingsEntity(
            id = dto.id,
            name = dto.name,
            targetAmount = dto.targetAmount,
            currentAmount = dto.currentAmount,
            endDate = dto.endDate,
            progressBarColor = dto.progressBarColor,
            notes = dto.notes,
            isActive = dto.isActive,
            isSynced = dto.isSynced,
            createdAt = dto.createdAt,
        )
    }

    fun toEntity(dto: InsertSaving):SavingsEntity{
        return SavingsEntity(
            name = dto.name,
            targetAmount = dto.targetAmount,
            endDate = dto.endDate,
            progressBarColor = dto.progressBarColor,
            notes = dto.notes,
        )
    }
}

fun SavingsEntity.toDto() = SavingsMapper.toDto(this)
fun InsertSaving.toEntity() = SavingsMapper.toEntity(this)
fun Savings.toEntity() = SavingsMapper.toEntity(this)