package com.mongodb.tasktracker.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class WorkoutPlan(_name: String = "Name", _desc: String = "Description"
                   ) : RealmObject() {
    @PrimaryKey
    @RealmField("_id") var id: ObjectId = ObjectId()
    @Required
    var recipeName: String = _name
    var description: String = _desc
   

}