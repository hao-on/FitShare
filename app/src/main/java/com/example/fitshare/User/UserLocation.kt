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


open class UserLocation(_userName: String ="UserName", _userID:String="UserID",_latitude:Double = 0.0,
                        _longitude:Double = 0.0)  : RealmObject(){
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var userName: String = _userName
    var userID: String = _userID

    var latitude: Double = _latitude

    var longitude: Double = _longitude
                        }




