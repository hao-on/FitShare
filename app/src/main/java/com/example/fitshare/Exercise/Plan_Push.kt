package com.mongodb.tasktracker.model

import com.example.fitshare.Exercise.kt.Exercise
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class Plan_Push(name: String = "", _chest1: String = "", _chest1reps: Int = 0, _chest1sets: Int = 0,_chest2: String = "", _chest2reps: Int = 0, _chest2sets: Int = 0,
                     _sho1 : String = "", _sho1reps : Int = 0, _sho1sets : Int = 0,_sho2 : String ="", _sho2reps : Int = 0, _sho2sets : Int = 0) : RealmObject() {

    object pushplan {
        var list : MutableList<Exercise> = mutableListOf<Exercise>()
        /*
        var name1 : String = "Bench Press"
        var reps1 : Int = 15
        var sets1 : Int = 3
        var name2 : String = "Shoulder Press"
        var reps2 : Int = 20
        var sets2 : Int = 3
        */
    }
    /*
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var _name: String = name
    var chest1 : String = _chest1
    var chest1reps : Int = _chest1reps
    var chest1sets : Int = _chest1sets
    var chest2 : String = _chest2
    var chest2reps : Int = _chest2reps
    var chest2sets : Int = _chest2sets
    var sho1 : String = _sho1
    var sho1reps : Int = _sho1reps
    var sho1sets : Int = _sho1sets
    var sho2 : String = _sho2
    var sho2reps : Int = _sho2reps
    var sho2sets :Int =_sho2sets
     */
}