package com.hogentessentials1.essentials.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * @author Kilian Hoefman: start of dataclass
 */

// @Entity(tableName = "employee")
// @PrimaryKey @ColumnInfo(name = "employee_id")
// @ColumnInfo(name = "organization_id")
@Parcelize
data class Employee(
    @Json(name = "id")
    val id: Int,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "email")
    val email: String,
    // @Json(name = "employeeOrganizationParts")
    // val employeeOrganizationParts: List<EmployeeOrganizationPart>?
) : Parcelable {
    // TODO invullen met logische gegevens
}