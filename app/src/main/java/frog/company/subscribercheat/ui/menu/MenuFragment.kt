package frog.company.subscribercheat.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.rewarded.RewardedAd
import frog.company.subscribercheat.R
import frog.company.subscribercheat.databinding.FragmentMenuBinding
import frog.company.subscribercheat.helper.AppConst
import frog.company.subscribercheat.helper.RewardedAdYandexAdsEventListener
import frog.company.subscribercheat.ui.home.MainHomeFragment
import frog.company.subscribercheat.ui.profile.ProfileFragment

class MenuFragment : Fragment() {

    private lateinit var binding : FragmentMenuBinding
    private lateinit var navView: BottomNavigationView
    private var rewardedAd: RewardedAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        navView = binding.navView
        navView.setOnNavigationItemSelectedListener(navListener)

        navView.background = null
        navView.menu.getItem(1).isEnabled = false

        rewardedAd = RewardedAd(requireContext())
        configureRewardedAd()

        binding.fab.setOnClickListener {
            rewardedAd?.loadAd(AdRequest.Builder().build())
        }
        navView.selectedItemId = R.id.navigation_home
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.viewPagerHome,
            MainHomeFragment()
        ).commit()
        return binding.root
    }

    private fun configureRewardedAd() {

        // Replace demo Ad Unit ID with actual Ad Unit ID
        rewardedAd?.setAdUnitId(AppConst.AD_UNIT_ID)
        rewardedAd?.setRewardedAdEventListener(RewardedAdYandexAdsEventListener(rewardedAd))
    }

    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {item ->
            var selectedFragment: Fragment? = null

            if(binding.navView.selectedItemId != item.itemId){
                when (item.itemId) {
                    R.id.navigation_home -> selectedFragment = MainHomeFragment()
                    R.id.navigation_profile -> selectedFragment = ProfileFragment()
                }
                if(selectedFragment != null)
                    requireActivity().supportFragmentManager.beginTransaction().replace(
                        R.id.viewPagerHome,
                        selectedFragment
                    ).commit()
                true
            }else
                false
        }
}