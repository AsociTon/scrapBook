package com.example.scrapadderkotlin

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameArray = ArrayList<String>()
        val imageArray = ArrayList<Bitmap>()

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,nameArray)
        listView.adapter=arrayAdapter
        try{
            val database = openOrCreateDatabase("Arts",Context.MODE_PRIVATE,null)
            database.execSQL("CREATE TABLE IF NOT EXISTS ARTS(NAME VARCHAR,IMAGE BLOB)")

            val cursor = database.rawQuery("SELECT * FROM ARTS",null)

            val nameIndex = cursor.getColumnIndex("NAME")
            val imageIndex = cursor.getColumnIndex("IMAGE")

            cursor.moveToFirst()

            while(cursor!=null){
                nameArray.add(cursor.getString(nameIndex))

                val byteArray  = cursor.getBlob(imageIndex)
                val image = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
                imageArray.add(image)

                cursor.moveToNext()
                arrayAdapter.notifyDataSetChanged()

            }
            cursor?.close()

        }catch (e:Exception){
            e.printStackTrace()
        }

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val intent = Intent(applicationContext,Main2Activity::class.java)
            intent.putExtra("name",nameArray[position])
            intent.putExtra("info","old")

            val chosen = Globals.chosen
            chosen.chosenImage = imageArray[position]

            startActivity(intent)
        }
        val database = openOrCreateDatabase("Arts",Context.MODE_PRIVATE,null)
        database.execSQL("CREATE TABLE IF NOT EXISTS ARTS(NAME VARCHAR,IMAGE BLOB)")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_scrap,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==R.id.add_scrap) {

            val intent = Intent(applicationContext,Main2Activity::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }
            return super.onOptionsItemSelected(item)

        }
}
