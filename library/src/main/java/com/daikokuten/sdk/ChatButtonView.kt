package com.daikokuten.sdk

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import android.widget.Button

class ChatButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val userId: String = "user123",
    private val accessToken: String = "token",
    private val testMode: Boolean = false
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var webView: WebView
    private lateinit var modalView: ConstraintLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.chat_button_layout, this, true)
        setupButton()
    }

    private fun setupButton() {
        val button = findViewById<Button>(R.id.chat_button).apply {
            setOnClickListener { toggleModal() }
        }
    }

    private fun setupWebView() {
        if (::modalView.isInitialized) return

        modalView = ConstraintLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
                width = (resources.displayMetrics.widthPixels * 0.9).toInt()
                height = (resources.displayMetrics.heightPixels * 0.6).toInt()
                // Replace addRule with direct constraint properties
                leftToLeft = LayoutParams.PARENT_ID
                rightToRight = LayoutParams.PARENT_ID
                topToTop = LayoutParams.PARENT_ID
                bottomToBottom = LayoutParams.PARENT_ID
            }
            setBackgroundColor(context.getColor(android.R.color.white))
            visibility = View.GONE
        }

        webView = WebView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }
        
        modalView.addView(webView)
        addView(modalView)

        val html = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <script src="https://daikokuten-7c6ffc95ca37.herokuapp.com/sdk/web/index.js" async></script>
            <script>
            window.onload = function() {
                function initDaikokuten() {
                    console.log("Daikokuten INIT")
                    if (typeof Daikokuten !== 'undefined') {
                        console.log("Daikokuten NEW")
                        const daikokuten = new Daikokuten("$userId", "$accessToken", false, "android");
                    } else {
                        console.log("Daikokuten RETRYING")
                        setTimeout(initDaikokuten, 1000);
                    }
                }
                initDaikokuten();
                console.log("Daikokuten START")
            };
            </script>
        </head>
        <body></body>
        </html>
        """
        webView.loadData(html, "text/html", "UTF-8")
    }

    private fun toggleModal() {
        if (!::modalView.isInitialized) {
            setupWebView()
        }
        modalView.visibility = if (modalView.visibility == View.GONE) View.VISIBLE else View.GONE
    }
}