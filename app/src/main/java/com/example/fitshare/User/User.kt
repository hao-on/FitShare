package com.example.fitshare.User

import com.example.fitshare.Exercise.kt.Exercise
import com.example.fitshare.Recipe.Recipe
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField

open class User : RealmObject {
    @PrimaryKey @RealmField("_id")
    var id: String = ""
    var _partition: String = ""
    var recipes: RealmList<Recipe> ?= null
    var exercises: RealmList<Exercise> ?= null
    var name: String = ""

    constructor(
        id: String,
        _partition: String,
        recipes: RealmList<Recipe>?,
        exercises: RealmList<Exercise>?,
        name: String
    ) {
        this.id = id
        this._partition = _partition
        this.name = name
        this.recipes = recipes
        this.exercises = exercises
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}