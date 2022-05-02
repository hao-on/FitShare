package com.example.fitshare.User

import com.example.fitshare.Exercise.kt.Exercise
import com.example.fitshare.Profile.Profile
import com.example.fitshare.Recipe.Recipe
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField


/*
open class User(
    @PrimaryKey @RealmField("_id") var id: String = "",
    var _partition: String = "",
    var recipes: RealmList<Recipe> ?= null,
    var name: String = ""

): RealmObject()

*/
open class User : RealmObject {
    @PrimaryKey @RealmField("_id")
    var id: String = ""
    var _partition: String = ""
    var recipes: RealmList<Recipe> ?= null
    var name: String = ""
<<<<<<< HEAD


=======
    var profile: Profile ?= null
    var exercises: RealmList<Exercise> ?= null
>>>>>>> c84fc4429d1d0454bb2c33a9a0744de23263abdf
    constructor(
        id: String,
        _partition: String,
        recipes: RealmList<Recipe>?,
<<<<<<< HEAD
        name: String
=======
        name: String,
        profile: Profile?,
        exercises: RealmList<Exercise> ?= null
>>>>>>> c84fc4429d1d0454bb2c33a9a0744de23263abdf

    ) {
        this.id = id
        this._partition = _partition
        this.name = name
        this.recipes = recipes
<<<<<<< HEAD

=======
        this.profile = profile
        this.exercises = exercises
>>>>>>> c84fc4429d1d0454bb2c33a9a0744de23263abdf
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor


     fun retrieveName(): String {
        return this.name
    }
}

