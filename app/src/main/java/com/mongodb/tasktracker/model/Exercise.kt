package com.mongodb.tasktracker.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId
import java.util.*


open class Exercise(_name: String = "Name",
                  _sets : Int = 1, _reps : Int = 1, _weight : Double = 1.0, _date: Date) : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var exerciseName: String = _name
    var sets: Int = _sets
    @Required
    var reps: Int = _reps
    @Required
    var weight: Double = _weight
    @Required
    var date: Date = _date;

}