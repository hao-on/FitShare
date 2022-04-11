package com.example.fitshare.Recipe

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import org.bson.types.ObjectId

open class RecipeRating: RealmObject {
    @PrimaryKey
    @RealmField("_id") var id: ObjectId = ObjectId()
    var rate: Double = 0.0
    var userID: String = ""
//    @LinkingObjects("ratings")
//    val recipe: RealmResults<Recipe>? = null


    constructor(
        id: ObjectId,
        rate: Double,
        userID: String = ""
    ) {
        this.id = id
        this.rate  = rate
        this.userID = userID
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}