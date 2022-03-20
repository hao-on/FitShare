package com.example.fitshare.User

import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.Exercise.kt.Exercise
import com.example.fitshare.Caloric.Caloric
import com.example.fitshare.Food.Food
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
    var exercises: RealmList<Exercise> ?= null
    var calorics: RealmList<Caloric> ?= null
    var foods: RealmList<Food> ?= null



    var name: String = ""

    constructor(
        id: String,
        _partition: String,
        recipes: RealmList<Recipe>?,
        exercises: RealmList<Exercise>?,
        foods: RealmList<Food>?,
        calorics: RealmList<Caloric>?,
        name: String
    ) {
        this.id = id
        this._partition = _partition
        this.name = name
        this.recipes = recipes
        this.exercises=exercises
        this.foods=foods
        this.calorics=calorics
    }

    constructor() {} // RealmObject subclasses must provide an empty constructor
}