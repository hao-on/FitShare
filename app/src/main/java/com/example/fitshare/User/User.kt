package com.example.fitshare.User

import com.example.fitshare.Recipe.Recipe
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField


open class User(
    @PrimaryKey @RealmField("_id") var id: String = "",
    var _partition: String = "",
<<<<<<< HEAD:app/src/main/java/com/mongodb/tasktracker/model/User.kt
    var memberOf: RealmList<Project> = RealmList(),
    var recipes: RealmList<Recipe> = RealmList(),
=======
    var recipes: RealmList<Recipe> ?= null,
>>>>>>> hao_new_app:app/src/main/java/com/example/fitshare/User/User.kt
    var name: String = ""
): RealmObject()