package com.hogentessentials1.essentials.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author Ziggy Moens: made Parcelable
 * @author Kilian Hoefman: start of Dataclass
 */

// @Entity(tableName = "changeInitiative")
@Parcelize
data class ChangeInitiative(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "startDate")
    val startDate: String,
    @Json(name = "endDate")
    val endDate: String,
    @Json(name = "changeGroup")
    val changeGroup: ChangeGroup?,
    @Json(name = "changeSponsor")
    val changeSponsor: Employee?,
    @Json(name = "changeType")
    val changeType: ChangeType?,
    @Json(name = "roadMap")
    val roadMap: Array<RoadMapItem>,
    // val surveys: ArrayList<Survey> // TODO verwijderen
    @Json(name = "progress")
    val progress: String

) : Parcelable

// data class ChangeInitiative(
//    var title: String,
//    var surveys: ArrayList<Survey> = arrayListOf()
// ) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString()!!,
//        arrayListOf<Survey>().apply {
//            parcel.readList(this, SurveyQuestion::class.java.classLoader)
//        }
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(title)
//        parcel.writeList(surveys)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<ChangeInitiative> {
//        override fun createFromParcel(parcel: Parcel): ChangeInitiative {
//            return ChangeInitiative(parcel)
//        }
//
//        override fun newArray(size: Int): Array<ChangeInitiative?> {
//            return arrayOfNulls(size)
//        }
//    }
// }