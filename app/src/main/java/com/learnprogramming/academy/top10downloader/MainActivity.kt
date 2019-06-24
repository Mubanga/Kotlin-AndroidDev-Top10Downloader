package com.learnprogramming.academy.top10downloader

import android.content.pm.PackageManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.Manifest;
import android.support.v4.app.ActivityCompat.requestPermissions
import android.widget.Toast
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

enum class XML_DATA_READER_PERMISSIONS(InternalCode: Int){
    INTERNET_PERMISSION_REQUEST(1),
    CONTACTS_PERMISSION_REQUEST(2)
}


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var FEEDURL = "https://rss.itunes.apple.com/api/v1/us/apple-music/top-albums/all/25/explicit.rss"
    private var _INTERNET_PERMISSION_GRANTED = false


    // Check To See If We Have The Required Permission For INTERNET ACCESS Before Continuing
    private fun AskForPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            _INTERNET_PERMISSION_GRANTED = false
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.INTERNET
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                /// TODO: Show An AlertDialogue About Why The Use Of The Internet Is Important For Downloading The XML
                Toast.makeText(this,"Internet Permission Is Required To Download The RSS Feed",Toast.LENGTH_LONG).show()
                requestPermissions(this, arrayOf(Manifest.permission.INTERNET),XML_DATA_READER_PERMISSIONS.INTERNET_PERMISSION_REQUEST.hashCode())
            }
            else {
                // No explanation needed, we can request the permission.
                requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    XML_DATA_READER_PERMISSIONS.INTERNET_PERMISSION_REQUEST.hashCode())

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            Log.d("MainActivity","MainActivity Permissions: INTERNET_PERMISSION ALREADY GRANTED")
            _INTERNET_PERMISSION_GRANTED = true

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when(requestCode){
            XML_DATA_READER_PERMISSIONS.INTERNET_PERMISSION_REQUEST.hashCode() ->{
                if ((grantResults.size!=0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                   // XML_DATA_READER_PERMISSIONS(1)
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    /// TODO: Permission Granted Everything Is Cauchy
                    _INTERNET_PERMISSION_GRANTED = true
                    Log.d("MainActivity","onRequestPermissionResult: INTERNET PERMISSION GRANTED")

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    /// TODO: Permission Denied You Essentially Can't Continue
                    Log.d("MainActivity","onRequestPermissionResult: INTERNET PERMISSION DENIED")


                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }


        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "OnCreate Called")
        val _DownloadData = DownloadData()
        AskForPermissions()
        if(_INTERNET_PERMISSION_GRANTED == true) {
            _DownloadData.execute(FEEDURL)
        }
        Log.d(TAG, "OnCreate Finished")
        // Wait Till I Get My Money Right
    }

    // Companion Objects Are Kotlin's Equivalents To Static
    companion object {
        private class DownloadData : AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.d(TAG, "OnPostExecute Called With Parameter: ${result}")
            }

            fun DownloadXML(URLPath: String?): String {
                var XMLResult = StringBuilder()
                val _XMLDataReader = XmlDataReader(URLPath!!, 500)
                XMLResult = _XMLDataReader.DownloadXMLData()
                Log.d(TAG, "$TAG: XML RESULT = $XMLResult")

                return XMLResult.toString()
            }

            override fun doInBackground(vararg url: String?): String? {
                // TODO("not implemented")To change body of created functions use File | Settings | File Templates.
                Log.d(TAG, "doInBackground Called With Parameter: ${url[0]}")
                val RSSFeed = DownloadXML(url[0])
                if (RSSFeed == "") {
                    Log.e(TAG, "doInBackground: Error Downloading XML")
                }
                return RSSFeed
            }

        }
    }
}
