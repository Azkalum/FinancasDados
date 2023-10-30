package br.projeto.financasdados.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val description: String,
    val amount: Double,
    val date: String,
    val savedMoney: Boolean
)
