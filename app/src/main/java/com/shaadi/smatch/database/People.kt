package com.shaadi.smatch.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@Entity
class People : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "location")
    var location: String? = null

    @ColumnInfo(name = "dob")
    var dob: String? = null

    @ColumnInfo(name = "picture")
    var picture: String? = null

    @ColumnInfo(name = "loginId")
    var loginId: String? = null

    @ColumnInfo(name = "status")
    var status: Boolean? = null

}