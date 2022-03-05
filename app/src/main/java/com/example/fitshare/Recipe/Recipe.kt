package com.example.fitshare.Recipe

import com.example.fitshare.User.User
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

/*
open class Recipe(_name: String = "Recipe", _desc: String = "Description",
                  _ingr: String = "Ingredients", _steps: String = "Steps") : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var name: String = _name
    var description: String = _desc
    @Required
    var ingredients: String = _ingr
    @Required
    var steps: String = _steps
}
*/

open class Recipe : RealmObject{
    @PrimaryKey @RealmField("_id") var id: ObjectId = ObjectId()
    @Required
    var name: String? = null
    @Required
    var description: String? = null
    @Required
    var ingredients: String? = null
    @Required
    var steps: String? = null
    @LinkingObjects("recipes")
    val user: RealmResults<User>? = null

    constructor(name: String?, description: String?,
                ingredients: String?, steps: String?){
        this.name = name
        this.description = description
        this.ingredients = ingredients
        this.steps = steps
    }

    constructor(){}
}
