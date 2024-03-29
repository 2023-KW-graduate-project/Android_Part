package com.example.whattodo.dao

import androidx.room.*
import com.example.whattodo.entity.Member


/* 사용자 데이터 베이스를 위한 Dao객체 */
@Dao
interface MemberDao {
    @Query("SELECT * FROM members")
    fun getMembers():List<Member>

    @Query("SELECT * FROM members WHERE memberId=(:id) LIMIT 1")
    fun getMember(id:String): Member

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(member: Member)

    @Update
    fun updateMember(member:Member)

    @Query("DELETE FROM members WHERE memberId=(:id)")
     fun delete(id:String)
}