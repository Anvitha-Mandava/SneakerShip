package com.example.sneakership.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sneakership.dao.CartDAO
import com.example.sneakership.models.SneakersCart

@Database(entities = [SneakersCart::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDAO(): CartDAO

    companion object {
        @Volatile
        private var instance: CartDatabase? = null

        fun getInstance(context: Context): CartDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): CartDatabase {
            return Room.databaseBuilder(
                context.applicationContext, CartDatabase::class.java, "SneakersDatabase"
            ).fallbackToDestructiveMigration().build()
        }
    }
}
