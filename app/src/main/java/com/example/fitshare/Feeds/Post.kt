package com.example.fitshare.Feeds

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import org.bson.types.ObjectId

open class Post: RealmObject{
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    var content: String = ""
    var likes: Int = 0
    var ratings: RealmList<PostRating> ?= null
    var userID: String = ""


    constructor(
        id: ObjectId,
        content: String = "",
        likes: Int,
        ratings: RealmList<PostRating>?,
        userID: String
    ) {
        this.id = id
        this.content = content
        this.likes = likes
        this.ratings = ratings
        this.userID = userID
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}