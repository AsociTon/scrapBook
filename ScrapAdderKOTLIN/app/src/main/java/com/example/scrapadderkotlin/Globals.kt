package com.example.scrapadderkotlin

import android.graphics.Bitmap

class Globals {

    companion object chosen{
        var chosenImage:Bitmap?=null

        fun returnChosenImage():Bitmap{

            return chosenImage!!
        }

    }
}