package com.example.zeolous.Models

class message {

    var message : String?=null
    var senderID : String?=null
     var type : String?=null
    var Image : String?=null
    var time : String?=null
    constructor(){}
    constructor( message : String?,
                 senderID : String?,
                 type:String?,
                 Image : String?,
                 time : String?
    ){
        this.message = message
        this.senderID=senderID
        this.type =type
        this.Image=Image
        this.time =time
    }


}