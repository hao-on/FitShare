package com.mongodb.tasktracker.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import io.realm.mongodb.UserIdentity
import org.bson.types.ObjectId

open class Recipe(_partition: String ="Partition",
                    _name: String = "Recipe",
                  _desc: String = "Description",
                  _ingr: String = "Ingredients",
                  _steps: String = "Steps",
                  _UserID: String = "UserID",

                  ) : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var _partition: String? = null
    var recipeName: String = _name
    var description: String = _desc
    @Required
    var ingredients: String = _ingr
    @Required
    var steps: String = _steps
    var UserID:String = _UserID


}