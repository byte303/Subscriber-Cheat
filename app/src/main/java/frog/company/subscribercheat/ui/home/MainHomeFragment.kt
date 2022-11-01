package frog.company.subscribercheat.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import frog.company.subscribercheat.databinding.FragmentHomeMainBinding

class MainHomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeMainBinding

    private val tabNames = arrayListOf("Instagram","Вконтакте","TikTok","Facebook")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeMainBinding.inflate(inflater, container, false)

        binding.viewPagerHome.adapter = HomePagerAdapter(requireActivity())

        TabLayoutMediator(binding.tabs, binding.viewPagerHome) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

        return binding.root
    }
}