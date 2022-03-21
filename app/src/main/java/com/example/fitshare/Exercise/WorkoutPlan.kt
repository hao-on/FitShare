package com.example.fitshare.Exercise.kt

import com.example.fitshare.User.User
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId
import java.util.*


open class WorkoutPlan(_name: String = "Exercise", _sets: Int = 1,
                    _reps: Int = 1, _weight: Double = 1.0,
                    _date: Date = Date()) : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var exerciseName: String = _name

    var sets: Int = _sets

    var reps: Int = _reps

    var weight: Double = _weight

    var date: Date = _date
    @LinkingObjects("exercises")
    val user: RealmResults<User>? = null
}