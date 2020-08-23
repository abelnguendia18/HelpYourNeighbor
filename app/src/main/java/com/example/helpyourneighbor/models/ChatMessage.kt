package com.example.helpyourneighbor.models

class ChatMessage(val messageId : String, val fromId : String, val toId : String,
                  val message : String, val announcementId : String, val timestamp : Long){
    constructor() : this("", "", "", "","", -1)
}