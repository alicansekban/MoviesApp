package com.alican.mvvm_starter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alican.mvvm_starter.data.local.model.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<RemoteKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: RemoteKey)

    @Query("select * from movie_remote_keys where id=:key")
    suspend fun getKeyByMovie(key: String): RemoteKey?

    @Query("delete from movie_remote_keys")
    suspend fun clearKeys()
}