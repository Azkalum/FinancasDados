package br.projeto.financasdados.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.projeto.financasdados.data.TransactionDAO
import br.projeto.financasdados.model.Transaction

@Database(entities = [Transaction::class], version = 1)
abstract class TransactionRoomDataBase : RoomDatabase(){
    abstract fun transactionDao(): TransactionDAO

    companion object {

        private var instance: TransactionRoomDataBase? = null

        fun getDatabase(context: Context): TransactionRoomDataBase {
            if (instance == null) {
                synchronized(TransactionRoomDataBase::class.java) {
                    // Criação do DB
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TransactionRoomDataBase::class.java,
                        "transaction_database"
                    ).build()
                }
            }
            return instance!!
        }
    }
}
