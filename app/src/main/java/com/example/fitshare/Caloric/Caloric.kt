package com.example.fitshare.Caloric

import com.example.fitshare.User.User
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId
import java.util.*

open class Caloric(_goal: Int = 1,
                   _food: Int = 1, _exercise: Int = 1,
                   _remaining: Int = 1,
                _date: Date = Date()
) : RealmObject() {
    @PrimaryKey
    @RealmField("_id")
    var id: ObjectId = ObjectId()


    var goal: Int = _goal

    var food: Int = _food

    var exercise: Int = _exercise

    var remaining: Int = _remaining

    var date: Date = _date

    //@LinkingObjects("calorics")
    //val user: RealmResults<User>? = null
}