package com.carlos.messageit

class Member {
    var name : String? = null
    var email: String? = null
    var uid  : String? = null

    //You must include this default constructor to enable read and write operation to our database
    constructor(){

    }
    constructor(name: String?, email: String?, uid: String?){
        this.name  = name
        this.email = email
        this.uid   = uid
    }
}