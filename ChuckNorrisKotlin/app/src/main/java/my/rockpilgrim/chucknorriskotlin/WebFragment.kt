package my.rockpilgrim.chucknorriskotlin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.system.Os.remove
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.web_fragment.*
import my.rockpilgrim.chucknorriskotlin.databinding.WebFragmentBinding
import my.rockpilgrim.chucknorriskotlin.viewModels.WebViewModel

class WebFragment : Fragment() {

    companion object{
        @JvmStatic
        private val TAG = WebFragment::class.java.simpleName
    }

    private lateinit var webViewModel: WebViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webViewModel = ViewModelProvider(this).get(WebViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = WebFragmentBinding.inflate(inflater, container, false)
        setupUI(binding)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI(binding: WebFragmentBinding) {
        val webView = binding.webView
        val refreshView = binding.refreshView
        refreshView.isRefreshing = true
        // Setup WebView
        webView.webViewClient = WebViewClient()
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.settings.apply {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            textZoom = 200
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                when {
                    newProgress >= 100 -> {
                        Log.i(TAG, "loadUrl: ${webView.url}")
                        refreshView.isRefreshing = false
                    }
                }
            }
        }
        subscribeUrl(webView)

        refreshView.setOnRefreshListener {
            loadUrl(webView.url)
        }
    }

    private fun subscribeUrl(webView: WebView) {
        webViewModel.url.observe(viewLifecycleOwner, Observer { url:String ->
            loadUrl(url)
        })
    }

    private fun loadUrl(url:String) {
        webView.loadUrl(url)
    }

    private fun onBackPressedAttach() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Log.d(TAG,"onBackPressedAttach()")
            if (webView.canGoBack()) {
                webView.goBack()
                Log.d(TAG, "onBackPressed() web")
            } else {
                remove()
                requireActivity().onBackPressed()
                Log.d(TAG, "onBackPressed() remove")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
        onBackPressedAttach()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onStop() {
        super.onStop()
        webViewModel.setUrl(webView.url)
    }

}