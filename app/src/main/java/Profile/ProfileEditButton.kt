package Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.realm.Realm

class ProfileEditButton : BottomSheetDialogFragment() {
    private lateinit var profileRealm: Realm
    private lateinit var userRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var submitButton: Button
    private  lateinit var partition: String

//    @Nullable
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        @Nullable container: ViewGroup?,
//        @Nullable savedInstanceState: Bundle?
//    ): View? {
//        val view: View = inflater.inflate()
//    }
}