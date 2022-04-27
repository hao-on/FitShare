package com.example.fitshare.MessageForum

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class ForumComment (
        @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId(),
        @Required
        var message: String = "",
        @Required
        var creator: String = "",
        @Required
        var dateCreated: String = "",
        @LinkingObjects("comments")
        val forumPost: RealmResults<ForumPost>?= null
) : RealmObject() {}