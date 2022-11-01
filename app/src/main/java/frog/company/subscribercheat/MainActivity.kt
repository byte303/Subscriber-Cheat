package frog.company.subscribercheat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import frog.company.subscribercheat.helper.AppConst
import frog.company.subscribercheat.helper.UtilsTheme
import frog.company.subscribercheat.model.User
import frog.company.subscribercheat.ui.authorization.AuthorizationFragment
import frog.company.subscribercheat.ui.home.MainHomeFragment
import frog.company.subscribercheat.ui.menu.MenuFragment
import io.paperdb.Paper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(UtilsTheme().onCheckTheme(this))
            setTheme(R.style.ThemeLight)
        else
            setTheme(R.style.ThemeDark)

        setContentView(R.layout.activity_main)

        Paper.init(this)

        val user = Paper.book().read<User?>(AppConst.DATA_USER, null)
        val fragment = if(user == null) AuthorizationFragment() else MenuFragment()

        supportFragmentManager.beginTransaction().replace(
            R.id.viewPager,
            fragment
        ).commit()
    }
}