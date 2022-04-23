package com.example.fitshare.Messaging

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId
import java.util.*

open class Message(
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId(),
    @Required
    var username: String = "",
    @Required
    var message: String = "",
    @Required
    var time: String = ""
) : RealmObject() {}