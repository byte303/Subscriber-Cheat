package frog.company.subscribercheat.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.rewarded.RewardedAd
import frog.company.subscribercheat.databinding.FragmentProfileBinding
import frog.company.subscribercheat.helper.AppConst.AD_UNIT_ID
import frog.company.subscribercheat.helper.RewardedAdYandexAdsEventListener

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

    private var rewardedAd: RewardedAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        rewardedAd = RewardedAd(requireContext())

        binding.vm = ProfileViewModel(requireActivity(),rewardedAd)
        return binding.root
    }

    override fun onDestroy() {
        rewardedAd?.destroy()
        rewardedAd = null
        super.onDestroy()
    }


}