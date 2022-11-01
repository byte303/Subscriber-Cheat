package frog.company.subscribercheat.ui.profile

import android.content.Context
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener
import frog.company.subscribercheat.R
import frog.company.subscribercheat.database.UtilsDB
import frog.company.subscribercheat.helper.AppConst
import frog.company.subscribercheat.helper.LoadingScreen
import frog.company.subscribercheat.helper.UtilsTheme
import frog.company.subscribercheat.model.User
import frog.company.subscribercheat.ui.authorization.AuthorizationFragment
import frog.company.subscribercheat.ui.create.CreateFragment
import io.paperdb.Paper

class ProfileViewModel(val context : Context, val rewardedAd: RewardedAd?) : ViewModel() {

    var coins = ObservableField(0)

    init {
        configureRewardedAd()
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

    fun btnAds(view : View){
        rewardedAd?.loadAd(AdRequest.Builder().build())
    }

    fun btnTheme(view : View){
        if(UtilsTheme().onCheckTheme(view.context))
            UtilsTheme().onSetTheme(view.context,false)
        else
            UtilsTheme().onSetTheme(view.context,true)

        (view.context as AppCompatActivity).finish()
        view.context.startActivity((view.context as AppCompatActivity).intent)
        (view.context as AppCompatActivity).overridePendingTransition(0, 0)
    }

    fun btnCreate(view : View){
        (view.context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(
            R.id.viewPager,
            CreateFragment()
        ).addToBackStack(null).commit()
    }

    fun btnExit(view : View){
        Paper.book().delete(AppConst.DATA_USER)

        (view.context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(
            R.id.viewPager,
            AuthorizationFragment()
        ).commit()
    }

    private fun configureRewardedAd() {
        rewardedAd?.setAdUnitId(AppConst.AD_UNIT_ID)
        rewardedAd?.setRewardedAdEventListener(RewardedAdYandexAdsEventListener())
    }

    inner class RewardedAdYandexAdsEventListener :
        RewardedAdEventListener {

        private var check = false

        override fun onAdLoaded() {
            Log.d("ProfileFragment","onAdLoaded")
            check = false
            rewardedAd?.show()
        }

        override fun onRewarded(reward: Reward) {
            val message = "onRewarded, amount = ${reward.amount}, type = ${reward.type}"
            Log.d("ProfileFragment",message)
            check = true
        }

        override fun onAdFailedToLoad(adRequestError: AdRequestError) {
            val message = "onAdFailedToLoad, error = ${adRequestError.description}"
            Log.e("ProfileFragment",message)
        }

        override fun onImpression(impressionData: ImpressionData?) {
            Log.d("ProfileFragment","onImpression")
        }

        override fun onAdShown() {
            Log.d("ProfileFragment","onAdShown")
        }

        override fun onAdDismissed() {
            Log.d("ProfileFragment","onAdDismissed")
            if(check){
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
                utils.onAddCoin(user.token,10)
                check = false
            }
        }

        override fun onAdClicked() {
            Log.d( "ProfileFragment","onAdClicked")
        }

        override fun onLeftApplication() {
            Log.d("ProfileFragment","onLeftApplication")
        }

        override fun onReturnedToApplication() {
            Log.d("ProfileFragment","onReturnedToApplication")
        }
    }
}