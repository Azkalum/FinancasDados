package br.projeto.financasdados.data

import android.app.Application
import br.projeto.financasdados.data.local.TransactionRoomDataBase
import br.projeto.financasdados.model.Balance
import br.projeto.financasdados.model.Transaction
import java.math.BigDecimal
import java.math.RoundingMode

class FinancesRepository(application: Application) {

    private val transactionDao: TransactionDAO

    init {
        val db = TransactionRoomDataBase.getDatabase(application)
        transactionDao = db.transactionDao()
    }

    suspend fun getAll(): List<Transaction> {
        return transactionDao.getAll()
    }

    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    suspend fun delete(id: String) {
        transactionDao.delete(id)
    }

    fun totalBalance(transactionList: List<Transaction>): Balance {
        val balance = Balance(0.0, 0.0, 0.0)

        transactionList.forEach {
            if (it.savedMoney) {
                balance.income += it.amount
            } else {
                balance.expense -= it.amount
            }
        }
        balance.income = BigDecimal(balance.income).setScale(2, RoundingMode.HALF_EVEN).toDouble()
        balance.expense = BigDecimal(balance.expense).setScale(2, RoundingMode.HALF_EVEN).toDouble()
        balance.total =
            BigDecimal(balance.income + balance.expense).setScale(2, RoundingMode.HALF_EVEN)
                .toDouble()

        return balance
    }
}