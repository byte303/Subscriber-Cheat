package frog.company.subscribercheat.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import frog.company.subscribercheat.databinding.FragmentCreateBinding

class CreateFragment : Fragment() {


    private lateinit var binding : FragmentCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        binding.vm = CreateViewModel(requireContext())


        return binding.root
    }


}