package com.example.fitshare.Food



import com.example.fitshare.User.User
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId
import java.util.*


open class Food( _name: String = "Food", _calories: Double = 1.0,
                 _protein: Double = 1.0, _carbs: Double = 1.0,
                 _fats: Double = 1.0,
                 _userid: String = "",
                 _date: Date = Date()

) : RealmObject() {
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var foodName: String = _name

    var calories: Double = _calories

    var protein: Double = _protein

    var carbs: Double = _carbs

    var fats: Double = _fats

    var date: Date = _date

    var userid: String = _userid
    //@LinkingObjects("foods")
    //val user: RealmResults<User>? = null
}