package com.hogentessentials1.essentials.data.model

import android.os.Parcelable
import androidx.room.Entity
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * @author Kilian Hoefman
 *
 * @property id the id of the assessment
 * @property questions list of questions
 * @property feedback one question
 * @property roadMapItemId the id of the linked road map item
 */

@Entity(tableName = "assessment")
@Parcelize
data class Assessment(
    @Json(name = "id")
    val id: String,
    @Json(name = "questions")
    var questions: List<Question>,
    @Json(name = "feedback")
    val feedback: Question,
    @Json(name = "roadMapItemId")
    val roadMapItemId: String
) : Parcelable
