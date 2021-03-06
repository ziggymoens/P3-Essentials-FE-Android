package com.hogentessentials1.essentials.data.model

import android.os.Parcelable
import androidx.room.Entity
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * A part of an organization --> factory, team, country, ...
 *
 * @author Kilian Hoefman
 *
 * @property id
 * @property name
 * @property employeeOrganizationParts
 * @property type
 */

@Entity(tableName = "organizationPart")
@Parcelize
data class OrganizationPart(
    @Json(name = "name")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "employeeOrganizationParts")
    val employeeOrganizationParts: String,
    @Json(name = "type")
    val type: String
) : Parcelable
