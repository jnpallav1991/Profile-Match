package com.shaadi.smatch.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeople(
        people: People
    ): Long?

    @Query("SELECT * FROM People")
    fun getPeopleDetails(): LiveData<List<People>>?

    @Query("SELECT * FROM People where loginId=:id")
    fun getPeopleById(id:String): People?

    @Query("update People set status=:declineOrAccept where loginId=:loginId")
    fun updatePeople(
        declineOrAccept: Boolean?,
        loginId: String?
    )
}