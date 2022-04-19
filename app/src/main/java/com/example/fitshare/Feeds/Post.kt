package com.example.fitshare.Feeds

import com.example.fitshare.Profile.Profile
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.mongodb.UserProfile
import org.bson.types.ObjectId
import java.util.*

open class Post: RealmObject{
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    var content: String = ""
    var likes: Int = 0
    var ratings: RealmList<PostRating> ?= null
    var date: Date = Date()
    //var username: String = ""
    var profile: Profile ?= null
//    var userID: String = ""


    constructor(
        content: String,
        likes: Int,
        ratings: RealmList<PostRating>?,
//        username: String
        profile: Profile
//        userID: String
    ) {
        this.content = content
        this.likes = likes
        this.ratings = ratings
//        this.username = username
        this.profile = profile
//        this.userID = userID
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}