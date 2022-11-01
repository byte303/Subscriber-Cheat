package frog.company.subscribercheat.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import frog.company.subscribercheat.R
import frog.company.subscribercheat.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private lateinit var binding : FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        binding.vm = RegistrationViewModel()
        return binding.root
    }
}