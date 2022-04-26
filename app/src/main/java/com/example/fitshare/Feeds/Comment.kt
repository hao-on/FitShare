package com.example.fitshare.Feeds

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import org.bson.types.ObjectId
import java.util.*

open class Comment: RealmObject {
    @PrimaryKey @RealmField("_id") var id: ObjectId = ObjectId()
    var comment: String = ""
    var userID: String = ""
    var date: Date = Date()
    @LinkingObjects("comments")
    val post: RealmResults<Post>? = null


    constructor(
        comment: String,
        userID: String = ""
    ) {
        this.comment = comment
        this.userID = userID
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}