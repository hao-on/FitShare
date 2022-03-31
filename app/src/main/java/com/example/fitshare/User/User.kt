package com.example.fitshare.User

import com.example.fitshare.Profile.Profile
import com.example.fitshare.Recipe.Recipe
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
    var profile: Profile ?= null

    constructor(
        id: String,
        _partition: String,
        recipes: RealmList<Recipe>?,
        name: String,
        profile: Profile?
    ) {
        this.id = id
        this._partition = _partition
        this.name = name
        this.recipes = recipes
        this.profile = profile
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}