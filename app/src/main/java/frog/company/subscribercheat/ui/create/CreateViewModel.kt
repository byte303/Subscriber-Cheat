package frog.company.subscribercheat.ui.create

import android.content.Context
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import frog.company.subscribercheat.R
import frog.company.subscribercheat.database.UtilsDB
import frog.company.subscribercheat.helper.AppConst
import frog.company.subscribercheat.helper.LoadingScreen
import frog.company.subscribercheat.model.Orders
import frog.company.subscribercheat.model.User
import io.paperdb.Paper

class CreateViewModel(val context : Context) : ViewModel() {

    var price = ObservableField("")
    var name = ObservableField("")
    var count = ObservableField("")
    var url = ObservableField("")
    var coins = ObservableInt(0)

    private var checked = 0

    fun onSplitTypeChanged(radioGroup: RadioGroup?, id: Int) {
        when(id){
            R.id.button1 -> checked = 0
            R.id.button2 -> checked = 1
            R.id.button3 -> checked = 2
        }
    }

    init {
        LoadingScreen.showDialog(context, false)

        val user = Paper.book().read<User>(AppConst.DATA_USER, User())!!
        val utils = UtilsDB()
        utils.getUser().observe((context as AppCompatActivity)) {
            if (it != null) {
                Paper.book().write(AppConst.DATA_USER, it)
                coins.set(it.money)
            }
            LoadingScreen.hideLoading()
        }
        utils.onLogin(user.login,user.password)
    }

    fun onNext(view : View){
        if(count.get() != "" && url.get() != "" && name.get() != "" && price.get() != ""){
            val prices = price.get()!!.toInt() * count.get()!!.toInt()

            if(coins.get() > prices){
                val utils = UtilsDB()

                val user = Paper.book().read<User>(AppConst.DATA_USER, User())!!
                val token = user.token
                val order = Orders(0, name.get()!!,url.get()!!,price.get()!!.toInt(),count.get()!!.toInt(),checked)

                utils.onCreateOrder(token, prices, order)

                Toast.makeText(view.context, context.getString(R.string.work_good_done), Toast.LENGTH_SHORT).show()
                (view.context as AppCompatActivity).supportFragmentManager.popBackStack()

            }else
                Toast.makeText(view.context, context.getString(R.string.not_coins), Toast.LENGTH_SHORT).show()
        }else
            Toast.makeText(view.context, context.getString(R.string.not_full_all_line), Toast.LENGTH_SHORT).show()
    }
}