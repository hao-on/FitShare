package com.example.fitshare.Feeds

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import org.bson.types.ObjectId

open class PostRating: RealmObject {
    @PrimaryKey @RealmField("_id") var id: ObjectId = ObjectId()
    var like: Boolean = false
    var comment: String = ""
    var userID: String = ""
    @LinkingObjects("ratings")
    val post: RealmResults<Post>? = null


    constructor(
        id: ObjectId,
        like: Boolean,
        comment: String,
        userID: String = ""
    ) {
        this.id = id
        this.like= like
        this.comment = comment
        this.userID = userID
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}