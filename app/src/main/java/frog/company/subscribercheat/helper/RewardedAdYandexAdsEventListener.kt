package frog.company.subscribercheat.helper

import android.util.Log
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener
import frog.company.subscribercheat.database.UtilsDB
import frog.company.subscribercheat.model.User
import io.paperdb.Paper

class RewardedAdYandexAdsEventListener(private val rewardedAd : RewardedAd?) : RewardedAdEventListener {

    override fun onAdLoaded() {
        Log.d("ProfileFragment","onAdLoaded")
        rewardedAd?.show()
    }

    override fun onRewarded(reward: Reward) {
        val message = "onRewarded, amount = ${reward.amount}, type = ${reward.type}"
        Log.d("ProfileFragment",message)

        val utils = UtilsDB()
        val user = Paper.book().read<User>(AppConst.DATA_USER, User())!!
        utils.onAddCoin(user.token,10)
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

