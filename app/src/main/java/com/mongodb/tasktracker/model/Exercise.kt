package com.mongodb.tasktracker.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId
import java.util.*


open class Exercise(name: String = "Name",
                  sets : Int = 1, reps : Int = 1, weight : Double = 1.0,userid: String = "",  date: Date ) : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var exerciseName: String = name
    var exerciseSets: Int = sets
    @Required
    var  exerciseReps: Int = reps
    @Required
    var  exerciseWeight: Double = weight
    @Required
    var  exerciseUserid: String =userid
    @Required
    var  exerciseDate: Date = date;

}