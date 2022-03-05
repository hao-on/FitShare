package com.mongodb.tasktracker.model

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class Recipe(_name: String = "Recipe", _desc: String = "Description",
                  _ingr: String = "Ingredients", _steps: String = "Steps") : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var recipeName: String = _name
    var description: String = _desc
    @Required
    var ingredients: String = _ingr
    @Required
    var steps: String = _steps
//    @Required
//    var UserID: String = _userID
   // @LinkingObjects("recipes")
    //val user: RealmResults<User>? = null

}