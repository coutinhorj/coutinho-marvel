package br.com.coutinho.marvel.view.characterslist

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import br.com.coutinho.marvel.R


class MoreDescriprionActivity  : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var webSettings: WebSettings
    private lateinit var pb: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var url =
            "http://marvel.com/universe/Abomination?utm_campaign=apiRef&utm_source=282e15bba823bea55500f21aa6569cb6"
        val extras = intent.extras
        if (extras != null) {
            url = extras.getString("url")
        }
        setContentView(R.layout.more_description)
        webView = findViewById(R.id.webview_description)
        webView.loadUrl(url)
        webView.webViewClient = WebViewClient()
        webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.pluginState = WebSettings.PluginState.ON

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(
                view: WebView,
                url: String,
                favicon: Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
                pb = findViewById(R.id.progress_circular)
                pb.visibility = View.VISIBLE

                // some code
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                pb = findViewById(R.id.progress_circular)
                pb.visibility = View.INVISIBLE
                webView.visibility =  View.VISIBLE
                // some code
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler, error: SslError
            ) {
                //  some code
            }
        }
        return
    }

    fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        view.loadUrl(request.url.toString())
        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}

