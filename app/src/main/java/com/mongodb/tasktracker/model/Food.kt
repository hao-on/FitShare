package com.mongodb.tasktracker.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId
import java.util.*


open class Food(_name: String = "Name",
                    _calories : Double = 0.0, _protein : Double = 0.0, _carbs : Double = 0.0, _fats : Double = 0.0, _userid: String = "", _date: Date) : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var foodName: String = _name
    var calories: Double = _calories
    @Required
    var protein: Double = _protein
    @Required
    var carbs: Double = _carbs
    @Required
    var fats: Double = _fats
    @Required
    var userid: String =_userid
    @Required
    var date: Date = _date;

}