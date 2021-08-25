package com.shaadi.smatch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(People::class),
    version = 1
)
public abstract class ShaadiDatabase : RoomDatabase() {

    abstract fun getPeopleDao(): PeopleDao?

    companion object {
        @Volatile
        private var INSTANCE: ShaadiDatabase? = null

        fun getDatabase(context: Context): ShaadiDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShaadiDatabase::class.java,
                    "shaadi_database"
                ).setJournalMode(JournalMode.TRUNCATE)
                    .addMigrations()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}