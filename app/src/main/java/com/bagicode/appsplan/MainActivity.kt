package com.bagicode.appsplan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.appsplan.database.DbContract
import com.bagicode.appsplan.database.ReaderDbHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataAdapter.Callback {

    lateinit var dbHelper: ReaderDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = ReaderDbHelper(this)


//        getDataDummy()
        initListener()
    }

    private fun getData() {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(BaseColumns._ID, DbContract.DataEntry.COLUMN_NAMA_TITLE
            , DbContract.DataEntry.COLUMN_NAMA_DESC)

        val sortOrder = "${DbContract.DataEntry.COLUMN_NAMA_DESC} DESC"

        var cursor = db.query(
            DbContract.DataEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        val dataList = mutableListOf<DataModel>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndex(BaseColumns._ID))
                val title = getString(getColumnIndexOrThrow(DbContract.DataEntry.COLUMN_NAMA_TITLE))
                val desc = getString(getColumnIndexOrThrow(DbContract.DataEntry.COLUMN_NAMA_DESC))
                dataList.add(DataModel(id, title, desc))
            }
        }

        var dataAdapter = DataAdapter(this)
        rv_data.apply {
            var linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            adapter = dataAdapter
        }

        dataAdapter.setData(dataList)
        dataAdapter.notifyDataSetChanged()
    }

    private fun initListener() {
        fab_add.setOnClickListener {
            startActivity(Intent(this, DetailActivity::class.java).putExtra("status", true))
        }
    }

    private fun getDataDummy() {
        var dataList = ArrayList<DataModel>()
        dataList.add(DataModel(1,"Belajar Java", "Minggu ini baru masuk bagian variable"))
        dataList.add(DataModel(1,"Belajar Java", "Minggu ini baru masuk bagian variable"))
        dataList.add(DataModel(1,"Belajar Java", "Minggu ini baru masuk bagian variable"))
        dataList.add(DataModel(1,"Belajar Java", "Minggu ini baru masuk bagian variable"))
        dataList.add(DataModel(1,"Belajar Java", "Minggu ini baru masuk bagian variable"))

        var dataAdapter = DataAdapter(this)
        rv_data.apply {
            var linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            adapter = dataAdapter
        }

        dataAdapter.setData(dataList)
        dataAdapter.notifyDataSetChanged()
    }

    override fun onClick(data: DataModel) {
        startActivity(Intent(this, DetailActivity::class.java)
            .putExtra("status", false)
            .putExtra("data", data)
        )
    }

    override fun onResume() {
        super.onResume()

        getData()
    }
}
