package com.mongodb.tasktracker.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class Recipe (
    @PrimaryKey
    @RealmField("_id") var id: ObjectId = ObjectId(),
    @Required
    var _partition: String = "",
    var recipeName: String = "",
    var description: String = ""
): RealmObject()