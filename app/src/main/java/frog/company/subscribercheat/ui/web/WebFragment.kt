package frog.company.subscribercheat.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import frog.company.subscribercheat.databinding.FragmentWebBinding

class WebFragment : Fragment() {

    private var _binding : FragmentWebBinding? = null
    private val binding get() = _binding!!

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_PARAM1)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWebBinding.inflate(inflater, container, false)

        binding.web.settings.javaScriptEnabled = true

        binding.web.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }
        binding.web.loadUrl(url!!)

        return binding.root
    }

    companion object {
        private const val ARG_PARAM1 = "param1"

        fun newInstance(param1: String) =
            WebFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}