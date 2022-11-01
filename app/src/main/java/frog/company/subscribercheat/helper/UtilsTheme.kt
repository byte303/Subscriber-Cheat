package frog.company.subscribercheat.helper

import android.content.Context
import io.paperdb.Paper

class UtilsTheme {

    fun onSetTheme(context : Context, theme : Boolean){
        Paper.init(context)
        Paper.book().write(AppConst.THEME, theme)
    }

    fun onCheckTheme(context : Context) : Boolean{

        Paper.init(context)
        return Paper.book().read(AppConst.THEME, true)!!
    }

}