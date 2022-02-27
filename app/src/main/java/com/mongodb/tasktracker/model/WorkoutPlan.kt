package com.mongodb.tasktracker.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId
import java.util.*


open class WorkoutPlan(_name: String = "Name",
                        _section: String ="Section", _list: Collection<Exercise>,_date: Date) : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var workoutPlanName: String = _name
    var section: String = _section
    @Required
    var list: Collection<Exercise> = _list
    @Required
    var date: Date = _date



}