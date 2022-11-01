package frog.company.subscribercheat.ui.registration

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import frog.company.subscribercheat.R
import frog.company.subscribercheat.database.UtilsDB
import frog.company.subscribercheat.helper.AppConst
import frog.company.subscribercheat.helper.LoadingScreen
import frog.company.subscribercheat.model.User
import frog.company.subscribercheat.ui.home.MainHomeFragment
import frog.company.subscribercheat.ui.menu.MenuFragment
import io.paperdb.Paper


class RegistrationViewModel : ViewModel() {

    var login: ObservableField<String> = ObservableField<String>("")
    var password: ObservableField<String> = ObservableField<String>("")
    var reloadPassword: ObservableField<String> = ObservableField<String>("")

    fun onRegistration(view: View){

        val utils = ViewModelProvider(view.context as AppCompatActivity)[UtilsDB::class.java]

        utils.getUser().observe((view.context as AppCompatActivity)) {
            if (it != null) {
                Paper.book().write(AppConst.DATA_USER, it)

                (view.context as AppCompatActivity).supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    .replace(R.id.viewPager, MenuFragment())
                    .commit()
            } else
                Toast.makeText(
                    view.context,
                    view.context.getString(R.string.login_yes),
                    Toast.LENGTH_SHORT
                ).show()

            LoadingScreen.hideLoading()
        }
        if(password.get() == reloadPassword.get()){
            LoadingScreen.showDialog(view.context, false)
            val user = User(
                0,"",login.get().toString(),password.get().toString()
            )
            utils.onInsertUser(user)
        }else
            Toast.makeText(view.context, view.context.getString(R.string.password_not_reload_password), Toast.LENGTH_SHORT).show()
    }

    fun onOpenAuthorization(view: View){
        (view.context as AppCompatActivity).supportFragmentManager.popBackStack()
    }
}