package com.example.myapplication.Model

import ir.mirrajabi.searchdialog.core.Searchable

class CurrentListModel(var text:String,var value:String):Searchable{
    override fun getTitle(): String {
        return text
    }

}