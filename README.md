
Usage
-------
Add a dependency to your `build.gradle`:

    dependencies {
        implementation 'com.saurabharora.customtabs:customtabs:1.1'
    }

Now in your Activity/Fragment from where you want to launch the Chrome Custom Tabs:

    private val  customTabActivityHelper: CustomTabActivityHelper =
        CustomTabActivityHelper(context = this, lifecycle = lifecycle, connectionCallback = this)
        
    //If you know the potential URL that will be loaded:
    customTabActivityHelper.mayLaunchUrl(uri)
        
    val customTabsIntent = CustomTabsIntent.Builder(customTabActivityHelper.session)
                           .build()
                           
    customTabsIntent.launchWithFallback(activity = this, uri = uri)
            

