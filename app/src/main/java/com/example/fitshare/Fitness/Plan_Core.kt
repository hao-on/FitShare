package com.mongodb.tasktracker.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class Plan_Core(name: String = "", _abs1: String = "", _abs1reps: Int = 0, _abs1sets: Int = 0,_abs2: String = "", _abs2reps: Int = 0, _abs2sets: Int = 0,
                     _abs3: String = "", _abs3reps: Int = 0, _abs3sets: Int = 0,) : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var _name: String = name
    var abs1: String = _abs1
    var abs1reps: Int = _abs1reps
    var abs1sets: Int = _abs1sets
    var abs2: String = _abs2
    var abs2reps: Int = _abs2reps
    var abs2sets: Int = _abs2sets
    var abs3: String = _abs3
    var abs3reps: Int = _abs1reps
    var abs3sets: Int = _abs3sets


    //var tempmodel = Exercise(Plan_Core.abs2,abs2sets,abs2reps,0,userId())
    //code to create object example "exercise(Plan_Core.abs2,abs2sets,abs2reps,0,userId())
}