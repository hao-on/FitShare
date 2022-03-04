package Profile

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class Profile (_fname: String = "First Name", _lname: String = "Last Name",
                    _bio: String = "Bio", _addr: String = "Address",
                    _zip: String = "Zipcode", _phone: String = "Phone Number",
                    _meet: Boolean = false) : RealmObject(){
    @PrimaryKey @RealmField ("_id") var id: ObjectId = ObjectId()
    @Required
    var firstName: String = _fname
    @Required
    var lastName: String = _lname
    @Required
    var bio: String = _bio
    @Required
    var address: String = _addr
    @Required
    var zipcode: String = _zip
    @Required
    var phoneNumber: String = _phone
    var meetUp: Boolean = _meet

}