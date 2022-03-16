package com.example.fitshare.User

import com.example.fitshare.Recipe.Recipe
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

//open class Recipe(_name: String = "Recipe", _desc: String = "Description",
//                  _ingr: String = "Ingredients", _steps: String = "Steps",
//                  _time: String = "Prep_Time") : RealmObject() {
//    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
//    @Required
//    var recipeName: String = _name
//    @Required
//    var description: String = _desc
//    @Required
//    var ingredients: String = _ingr
//    @Required
//    var steps: String = _steps
//    @Required
//    var prepTime: String = _time
//    @LinkingObjects("recipes")
//    val user: RealmResults<User>? = null
//}

open class UserLocation(_userName: String ="UserName", _latitude:Double = 0.0,
                        _longitude:Double = 0.0)  : RealmObject(){
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var userName: String = _userName

    var latitude: Double = _latitude

    var longitude: Double = _longitude
                        }




