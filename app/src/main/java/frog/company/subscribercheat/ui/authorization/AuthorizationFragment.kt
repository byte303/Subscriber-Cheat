package frog.company.subscribercheat.ui.authorization

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import frog.company.subscribercheat.R
import frog.company.subscribercheat.databinding.FragmentAuthorizationBinding

class AuthorizationFragment : Fragment() {

    private lateinit var binding : FragmentAuthorizationBinding
    private lateinit var viewModel: AuthorizationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AuthorizationViewModel::class.java]

        binding.vm = viewModel
        return binding.root
    }


}