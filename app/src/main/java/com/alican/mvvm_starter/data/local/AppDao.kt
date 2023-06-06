package com.alican.mvvm_starter.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.alican.mvvm_starter.data.local.model.DataModel

@Dao
interface AppDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertData(dataModel: DataModel)
}
