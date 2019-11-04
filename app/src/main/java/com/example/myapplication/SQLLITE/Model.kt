package com.example.myapplication.SQLLITE

class Model {
    var id:Int=0
    var KEYNO:Int=0
    var SeriMiktari:Int=0
    var SeriAdi:String=""

    constructor(id: Int, KEYNO: Int, SeriMiktari: Int, SeriAdi: String) {
        this.id = id
        this.KEYNO = KEYNO
        this.SeriMiktari = SeriMiktari
        this.SeriAdi = SeriAdi
    }

    constructor()
    constructor(KEYNO: Int, SeriMiktari: Int, SeriAdi: String) {
        this.KEYNO = KEYNO
        this.SeriMiktari = SeriMiktari
        this.SeriAdi = SeriAdi
    }

    constructor(SeriMiktari: Int, SeriAdi: String) {
        this.SeriMiktari = SeriMiktari
        this.SeriAdi = SeriAdi
    }
}