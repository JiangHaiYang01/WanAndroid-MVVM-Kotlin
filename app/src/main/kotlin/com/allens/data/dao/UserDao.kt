package com.allens.data.dao

import androidx.room.Insert
import com.allens.data.db.UserDB

interface UserDao {
    @Insert
    public fun inertUser(user: UserDB)   // 单个参数可以返回 long

    @Insert
    public fun insertUserList(array: Array<UserDB>)  // 参数为集合可以返回long[]
}