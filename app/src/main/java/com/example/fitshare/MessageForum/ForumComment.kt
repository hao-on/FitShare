package com.example.fitshare.MessageForum

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

class ForumComment (
        @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId(),
        @Required
        var response: String = "",
        @Required
        var creator: String = "",
        @Required
        var dateCreated: String = ""
) : RealmObject() {}