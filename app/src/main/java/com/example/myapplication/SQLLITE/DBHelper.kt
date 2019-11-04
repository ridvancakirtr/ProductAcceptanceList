package com.example.myapplication.SQLLITE

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DBHelper(val context: Context) : SQLiteOpenHelper(context,DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION) {
    private val TABLE_NAME="SeriListesi"
    private val COL_ID = "id"
    private val COL_KEYNO = "keyno"
    private val COL_SERI_MIKTAR = "seri_miktar"
    private val COL_SERI_ADI = "seri_adi"
    companion object {
        private val DATABASE_NAME = "MALKABUL"//database adı
        private val DATABASE_VERSION = 1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_KEYNO  VARCHAR(256),$COL_SERI_MIKTAR INTEGER,$COL_SERI_ADI  VARCHAR(256))"
        p0?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData(seri:Model){
        val sqliteDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_KEYNO , seri.KEYNO)
        contentValues.put(COL_SERI_MIKTAR, seri.SeriAdi)
        contentValues.put(COL_SERI_ADI, seri.SeriMiktari)

        val result = sqliteDB.insert(TABLE_NAME,null,contentValues)

        Toast.makeText(context,if(result != -1L) "Kayıt Başarılı" else "Kayıt yapılamadı.", Toast.LENGTH_SHORT).show()
    }

    fun readData(key:Int):ArrayList<Model>{
        val userList = ArrayList<Model>()
        val sqliteDB = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_KEYNO=$key"
        val result = sqliteDB.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                val seri = Model()
                seri.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                seri.KEYNO = result.getInt(result.getColumnIndex(COL_KEYNO))
                seri.SeriAdi = result.getString(result.getColumnIndex(COL_SERI_MIKTAR))
                seri.SeriMiktari = result.getString(result.getColumnIndex(COL_SERI_ADI)).toInt()
                userList.add(seri)
            }while (result.moveToNext())
        }
        result.close()
        sqliteDB.close()
        return userList
    }

    fun readDataINC():ArrayList<Model>{
        val userList = ArrayList<Model>()
        val sqliteDB = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = sqliteDB.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                val seri = Model()
                seri.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                seri.KEYNO = result.getInt(result.getColumnIndex(COL_KEYNO))
                seri.SeriAdi = result.getString(result.getColumnIndex(COL_SERI_MIKTAR))
                seri.SeriMiktari = result.getString(result.getColumnIndex(COL_SERI_ADI)).toInt()
                userList.add(seri)
            }while (result.moveToNext())
        }
        result.close()
        sqliteDB.close()
        return userList
    }

    fun deleteData(pos:Int,seriListesi:ArrayList<Model>){
        var a=seriListesi[pos]
        Log.d("SWIPE",a.SeriAdi)
        var seriAdi=a.id
        val sqliteDB = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_ID = $seriAdi"
        val result = sqliteDB.rawQuery(query,null)
        if(result.moveToFirst()){
            sqliteDB.delete(TABLE_NAME, COL_ID + " = ?", arrayOf(seriAdi.toString()))
        }
        result.close()
        sqliteDB.close()

    }

}