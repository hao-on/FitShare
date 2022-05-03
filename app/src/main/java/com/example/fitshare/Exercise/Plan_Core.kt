package com.mongodb.tasktracker.model

import com.example.fitshare.Exercise.Exercise
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import org.bson.types.ObjectId
/*
open class Plan_Core(name: String = "", _abs1: String = "", _abs1reps: Int = 0, _abs1sets: Int = 0,_abs2: String = "", _abs2reps: Int = 0, _abs2sets: Int = 0,
                     _abs3: String = "", _abs3reps: Int = 0, _abs3sets: Int = 0, ) : RealmObject() {
    // create array list of item
    // array list of exercises
    /*
        PlanCore =[Ex1, Ex2]
        Ex1= {name = crunch, rep = 15, set = 3}
        Ex2= {name = russian twist, rep = 20, set = 3}
     */
    object coreplan {
        var list : MutableList<Exercise> = mutableListOf<Exercise>()
    }

        @PrimaryKey @RealmField("_id")
        var id: ObjectId = ObjectId()

        @Required
}
*/
open class Plan_Core: RealmObject {
    @PrimaryKey
    @RealmField("_id")
    var id: ObjectId = ObjectId()
    var _partition: String = ""
    var plan_core: RealmList<Exercise>? = null

    constructor(
        id:ObjectId,
        _partition: String,
        plan_core: RealmList<Exercise>?
    ) {
        this.id = id
        this._partition = _partition
        this.plan_core = plan_core
    }

    constructor() {}
}