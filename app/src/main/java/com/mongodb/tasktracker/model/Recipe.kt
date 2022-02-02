package com.mongodb.tasktracker.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class Recipe(_name: String = "Recipe") : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var name: String = _name
    var description: String = ""
    @Required
    var ingredients: String = ""
    @Required
    var steps: String = ""

}