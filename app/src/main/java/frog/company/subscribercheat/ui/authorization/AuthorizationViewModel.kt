package frog.company.subscribercheat.ui.authorization

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
import frog.company.subscribercheat.ui.home.HomeFragment
import frog.company.subscribercheat.ui.home.MainHomeFragment
import frog.company.subscribercheat.ui.menu.MenuFragment
import frog.company.subscribercheat.ui.registration.RegistrationFragment
import io.paperdb.Paper

class AuthorizationViewModel : ViewModel() {

    var login : ObservableField<String> = ObservableField<String>("")
    var password : ObservableField<String> = ObservableField<String>("")

    fun onAuthorization(view: View){
        LoadingScreen.showDialog(view.context, false)

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
                    ).replace(R.id.viewPager, MenuFragment())
                    .commit()
            } else
                Toast.makeText(
                    view.context,
                    view.context.getString(R.string.error_login_or_password),
                    Toast.LENGTH_SHORT
                ).show()

            LoadingScreen.hideLoading()
        }
        utils.onLogin(login.get().toString(),password.get().toString())

    }

    fun onOpenRegistration(view: View){
        (view.context as AppCompatActivity).supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.fade_in,
                R.anim.slide_out
            ).addToBackStack(null).replace(R.id.viewPager, RegistrationFragment())
            .commit()
    }
}