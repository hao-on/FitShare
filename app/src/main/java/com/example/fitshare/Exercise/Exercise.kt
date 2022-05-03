package com.example.fitshare.Exercise

import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.User.User
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId
import java.util.*
//
//
//open class Exercise(_name: String = "Exercise", _sets: Int = 1,
//                  _reps: Int = 1, _weight: Double = 1.0,
//                  _date: Date = Date()) : RealmObject() {
//    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
//    @Required
//    var exerciseName: String = _name
//    var sets: Int = _sets
//    var reps: Int = _reps
//    var weight: Double = _weight
//    var date: Date = _date
//
////    var userid: String = _userid
//     // @LinkingObjects("exercises")
//    //val user: RealmResults<User>? = null
//}

open class Exercise : RealmObject {
    @PrimaryKey @RealmField("_id") var id: ObjectId = ObjectId()
    @Required
    var exerciseName: String = ""
    var sets: Long = 0
    var reps: Long = 0
    var weight: Double = 0.0
    var date: Date = Date()
    var userid:String=""
    @LinkingObjects("exercises")
    val user: RealmResults<User>? = null

    constructor(
        exerciseName: String,
        sets: Long,
        reps: Long,
        weight: Double,
        userid: String
    ) {
        this.exerciseName = exerciseName
        this.sets = sets
        this.reps = reps
        this.weight = weight
        this.userid=userid

    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}
