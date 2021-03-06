package com.hogentessentials1.essentials.data.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hogentessentials1.essentials.DAOs.ChangeGroupDao
import com.hogentessentials1.essentials.DAOs.ChangeInitiativeDao
import com.hogentessentials1.essentials.DAOs.EmployeeDao
import com.hogentessentials1.essentials.DAOs.ProjectDao
import com.hogentessentials1.essentials.DAOs.RoadMapDao
import com.hogentessentials1.essentials.data.model.ChangeGroup
import com.hogentessentials1.essentials.data.model.ChangeInitiative
import com.hogentessentials1.essentials.data.model.Employee
import com.hogentessentials1.essentials.data.model.Project
import com.hogentessentials1.essentials.data.model.RoadMapItem
import com.hogentessentials1.essentials.data.network.converters.AssessmentConverter
import com.hogentessentials1.essentials.data.network.converters.ChangeGroupConverter
import com.hogentessentials1.essentials.data.network.converters.ChangeInitiativeConverter
import com.hogentessentials1.essentials.data.network.converters.ChangeTypeConverter
import com.hogentessentials1.essentials.data.network.converters.EmployeeChangeGroupArrayConverter
import com.hogentessentials1.essentials.data.network.converters.EmployeeConverter
import com.hogentessentials1.essentials.data.network.converters.ProjectConverter
import com.hogentessentials1.essentials.data.network.converters.RoadMapItemConverter

@Database(
    entities = [Project::class, Employee::class, ChangeInitiative::class, RoadMapItem::class, ChangeGroup::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(
    EmployeeConverter::class,
    ChangeInitiativeConverter::class,
    ChangeGroupConverter::class,
    ProjectConverter::class,
    RoadMapItemConverter::class,
    AssessmentConverter::class,
    EmployeeChangeGroupArrayConverter::class,
    ChangeTypeConverter::class
)
/**
 * The database for the application
 * @author Simon De Wilde
 * @author Kilian Hoefman
 *
 */
abstract class EssentialsDatabase : RoomDatabase() {
    abstract fun EmployeeDao(): EmployeeDao
    abstract fun ChangeInitiativeDao(): ChangeInitiativeDao
    abstract fun ChangeGroupDao(): ChangeGroupDao
    abstract fun ProjectDao(): ProjectDao
    abstract fun RoadMapDao(): RoadMapDao

    companion object {
        @Volatile
        private var INSTANCE: EssentialsDatabase? = null

        /**
         * Creates an instance of the database if necessary and returns it
         * @param context
         * @return instance of the database
         */
        fun getInstance(context: Context): EssentialsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }

        /**
         * builds the database
         * @param appContext
         * @return built database
         */
        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, EssentialsDatabase::class.java, "essentials_db")
                .fallbackToDestructiveMigration()
                .build()
    }

    suspend fun truncate() {
        this.EmployeeDao().deleteAll()
        this.ChangeGroupDao().deleteAll()
        this.ChangeInitiativeDao().deleteAll()
        this.ProjectDao().deleteAll()
        this.RoadMapDao().deleteAll()
    }
}
