

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.saurabharora.customtabs.extensions.launchWithFallback


class MainActivity : AppCompatActivity() {
    var mywebview: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Check for device is connceted to Internet or not
        if (!isNetworkAvailable(applicationContext)) {
            Toast.makeText(applicationContext, "Network is not available", Toast.LENGTH_SHORT).show()
        }
        //Set default url for toolbar
        val mToolBar = findViewById<mozilla.components.browser.toolbar.BrowserToolbar>(R.id.toolbar)
        mToolBar.url = "https://www.google.co.in/"
        val uri = Uri.parse(mToolBar.url.toString())

        // THIS IS DEPRECATED OR NOT AVAILABLE
        // val reload = BrowserToolbar.Button(resources.getDrawable(1 , mozilla.components.ui.icons.R.drawable.mozac_ic_refresh){}


        // Load any url in the webview instead of adding a browser engiene
        mywebview = findViewById<WebView>(R.id.webview)
        mywebview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        mywebview!!.loadUrl("https://www.google.co.in/")
        val arrayAdapter: ArrayAdapter<*>
        val web = arrayOf(
                "Way2Online", "BBC", "FoxNews"
        )


        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.list)
        arrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, web)
        mListView.adapter = arrayAdapter

        mListView.setOnItemClickListener { adapterView, view, i, l ->
            val customTabsIntent = CustomTabsIntent.Builder().build()
            when (i) {
                0 -> customTabsIntent.launchWithFallback(this, Uri.parse(getString(R.string.way2online_url)))
                1 -> customTabsIntent.launchWithFallback(this, Uri.parse(getString(R.string.bbc_url)))
                2 -> customTabsIntent.launchWithFallback(this, Uri.parse(getString(R.string.foxnews_url)))
            }
        }


    }

    // check for the Active Internet Connection
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val actNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}
