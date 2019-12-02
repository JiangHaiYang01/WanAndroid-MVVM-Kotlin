package com.allens.data.db
//
//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey


//使用@Entity注解实体类，Room会为实体中定义的每个字段创建一列，如果想避免使用@Ignore注解
//Room默认使用类名作为数据库表名，要修改表名使用 @Entity 的 tableName属性
//使用组合主键 使用@Entity 的@primaryKeys属性 eg @Entity(primaryKeys = ["id", "name"])  // 组合组件
//@Entity(tableName = "users")
//data class UserDB(
//    //@PrimaryKey ：至少定义一个字段作为主键
//    //如果自增长ID 使用设置@PrimaryKey的 autoGenerate 属性
//
//
//    @PrimaryKey(autoGenerate = true)    // 单个主键设置为自增长
//    val id: Int,
//
//    val icon: String,
//
//    val token: String,
//
//    //Room 默认使用字段名成作为列名，要修改使用 @ColumnInfo(name = "***")
//    @ColumnInfo(name = "name")            // 定义列名
//    val username: String
//)

