package com.mongodb.tasktracker.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId
import java.util.*


open class Food(name: String = "Name",
                    calories : Double = 0.0, protein : Double = 0.0, carbs : Double = 0.0, fats : Double = 0.0, userid: String = "", date: Date) : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var foodName: String = name
    var foodCalories: Double = calories
    @Required
    var foodProtein: Double = protein
    @Required
    var foodCarbs: Double = carbs
    @Required
    var foodfats: Double = fats
    @Required
    var foodUserid: String =userid
    @Required
    var foodDate: Date = date;

}