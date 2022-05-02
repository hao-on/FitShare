package com.example.fitshare.MessageForum

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class ForumPost (
    @PrimaryKey @RealmField("_id") var id: ObjectId = ObjectId(),
    @Required
    var title: String = "",
    @Required
    var content: String = "",
    @Required
    var creator: String = "",
    @Required
    var dateCreated: String = "",
    var comments: RealmList<ForumComment> ?= null
) : RealmObject() {}