package com.example.fitshare.Feeds

import com.example.fitshare.Profile.Profile
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.mongodb.User
import io.realm.mongodb.UserProfile
import org.bson.types.ObjectId
import java.util.*

open class Post: RealmObject{
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    var content: String = ""
    var comments: RealmList<Comment> ?= null
    var likesList: RealmList<User> ?= null
    var date: Date = Date()
    var profile: Profile ?= null
//    var userID: String = ""


    constructor(
        content: String,
        comments: RealmList<Comment>?,
        likesList: RealmList<User>?,
        profile: Profile
//        userID: String
    ) {
        this.content = content
        this.comments = comments
        this.likesList = likesList
        this.profile = profile
//        this.userID = userID
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}