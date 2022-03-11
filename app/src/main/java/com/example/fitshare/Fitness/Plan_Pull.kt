package com.mongodb.tasktracker.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class Plan_Pull(name: String = "", _arms1: String = "", _arms1reps: Int = 0, _arms1sets: Int = 0,_arms2: String = "", _arms2reps: Int = 0, _arms2sets: Int = 0,
                     _arms3: String = "", _arms3reps: Int = 0, _arms3sets: Int = 0,_back1 : String ="", _back1reps : Int = 0,
                     _back1sets : Int = 0,_back2 : String ="", _back2reps : Int = 0, _back2sets : Int = 0,
                     _back3 : String ="", _back3reps : Int = 0, _back3sets : Int = 0) : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var _name: String = name
    var arms1: String = _arms1
    var arms1reps: Int = _arms1reps
    var arms1sets: Int = _arms1sets
    var arms2: String = _arms2
    var arms2reps: Int = _arms2reps
    var arms2sets: Int = _arms2sets
    var arms3: String = _arms3
    var arms3reps: Int = _arms1reps
    var arms3sets: Int = _arms3sets
    var back1 : String = _back1
    var back1reps : Int = _back1reps
    var back1sets : Int = _back1sets
    var back2 : String = _back2
    var back2reps : Int = _back2reps
    var back2sets : Int = _back2sets
    var back3 : String = _back3
    var back3reps : Int = _back3reps
    var back3sets : Int = _back3sets
}