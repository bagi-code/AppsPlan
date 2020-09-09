package com.bagicode.appsplan.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class ReaderDbHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_ENTRIES = "CREATE TABLE ${DbContract.DataEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${DbContract.DataEntry.COLUMN_NAMA_TITLE} TEXT," +
            "${DbContract.DataEntry.COLUMN_NAMA_DESC} TEXT)"

    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    companion object {
        const val DATABASE_NAME = "entry.db"
        const val DATABASE_VERSION = 1
    }

}