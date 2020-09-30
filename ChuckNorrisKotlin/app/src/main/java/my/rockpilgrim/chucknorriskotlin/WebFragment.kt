package my.rockpilgrim.chucknorriskotlin

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import my.rockpilgrim.chucknorriskotlin.databinding.WebFragmentBinding
import java.lang.Exception

class WebFragment : Fragment() {

    companion object{
        @JvmStatic
        private val TAG = WebFragment::class.java.simpleName

        private const val KEY_URL_SAVED = " my.rockpilgrim.chucknorris.savedurl"
        private const val BASE_URL = "http://www.icndb.com/api"
    }
    private lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedAttach()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = WebFragmentBinding.inflate(inflater, container, false)
        setupUI(binding)
        // Check information
        checkInputInformation(savedInstanceState)
        return binding.root
    }

    private fun checkInputInformation(state: Bundle?) {
        if (checkBundle(state)) {
            Log.i(TAG, "checkInputInformation() local url (after phone rotation)")
            return
        } else {
            loadUrl(BASE_URL)
        }
    }

    private fun checkBundle(savedInstanceState: Bundle?):Boolean {
        return if (savedInstanceState != null && savedInstanceState.getBoolean(KEY_URL_SAVED, false)) {
            webView.restoreState(savedInstanceState)
            true
        } else {
            false
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI(binding: WebFragmentBinding) {
        webView = binding.webView
        val refreshView = binding.refreshView

        // Setup WebView
        webView.setBackgroundColor(Color.TRANSPARENT)
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true)
        }

        webView.settings.apply {
            domStorageEnabled = true
            databaseEnabled = true
            setAppCacheEnabled(true)
            javaScriptEnabled = true
            allowContentAccess = true

            useWideViewPort = true;
            loadWithOverviewMode = true;
            builtInZoomControls = true;
            layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING;
            setSupportZoom(true);
        }
        webView.apply {
            isFocusable = true
            isFocusableInTouchMode = true
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            isSaveEnabled = true }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                when {
                    newProgress >= 100 ->
                        binding.refreshView.isRefreshing = false
                    newProgress in 21..99 ->
                        binding.refreshView.isRefreshing = true
                }
            }
        }
        webView.webViewClient = object :WebViewClient(){
            /// Get errors
            override fun onReceivedHttpError(
                view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
                Log.i(TAG, "WebViewClient() Http error")
            }
            /// Get errors and show info
            override fun onReceivedError(webView: WebView, errorCode: Int, description: String?, failingUrl: String) {
                try {
                    webView.stopLoading()
                } catch (e: java.lang.Exception) {
                }
                webView.loadUrl("about:blank")
                val alertDialog = AlertDialog.Builder(webView.context).create()
                alertDialog.setTitle("Error")
                onError(getString(R.string.connect_error))
                alertDialog.setMessage(getString(R.string.check_connection_message))
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.try_again)) { dialog, which ->
                    webView.loadUrl(failingUrl)
                }
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }

        refreshView.setOnRefreshListener {
            loadUrl(webView.url)
        }
    }

    private fun loadUrl(url:String) {
        Log.d(TAG, "loadUrl(${url})")
        webView.loadUrl(url)
    }
    private fun onError() {
        onError(getString(R.string.error_message))
        Log.d(TAG, "onError()")
    }

    private fun onError(message: String?) {
        makeToast(message ?: getString(R.string.error_message))
        Log.d(TAG, "onError() message")
    }
    private fun makeToast(line: String) {
        Toast.makeText(context, line, Toast.LENGTH_SHORT).show()
    }

    /// BackPressed listener (All right!!)
    private fun onBackPressedAttach() {
        Log.i(TAG, "onBackPressedAttach()")
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (webView.canGoBack()) {
                webView.goBack()
                Log.d(TAG, "onCreate() onBackPressed() web")
            } else {
                remove()
                requireActivity().onBackPressed()
                Log.d(TAG, "onCreate() onBackPressed() remove")
            }
        }
    }
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume()")
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause()")
        webView.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        try {
            Log.d(TAG, "onSaveInstanceState()")
            webView.saveState(outState)
            outState.putBoolean(KEY_URL_SAVED, true)
        }catch (ex: Exception){
            onError()
            Log.d(TAG, "onSaveInstanceState() error")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
        webView.destroy()
    }
}