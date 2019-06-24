package com.learnprogramming.academy.top10downloader

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        Log.d(TAG,"OnCreate Called")
        val _DownloadData = DownloadData()
        _DownloadData.execute("URL Goes Here")
        Log.d(TAG,"OnCreate Finished")
        // Wait Till I Get My Money Right
    }

    // Companion Objects Are Kotlin's Equivalents To Static
    companion object {
        private class DownloadData: AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.d(TAG,"OnPostExecute Called With Parameter: ${result}")
            }

            fun DownloadXML(URLPath: String?) : String
            {
                val XMLResult = StringBuilder()
                try {
                    val url = URL(URLPath) // Create the URL Path Object
                    val connection = url.openConnection() as HttpURLConnection // Create A Connection Object And Cast It TO An HTTPURLConnection
                    val response = connection.responseCode // ResponseCode That The Website Server Will Send Back
                    Log.d(TAG,"DownloadXML: The Response Code Was $response")

                    // Reading From The Actual XML
                    // We Need The Following 1.) InputStream 2.) InputStreamReader 3.) BufferedReader
//                    val inputStream = connection.inputStream
//                    val inputStreamReader = InputStreamReader(inputStream)
                    val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                }
                catch (e: MalformedURLException)
                {
                    Log.e(TAG,"DownloadXML: Invalid URL ${e.message} ")
                }catch(e: IOException){
                    Log.e(TAG,"DownloadXML: IOException ${e.message} ")

                } catch(e: Exception){
                    Log.e(TAG,"DownloadXML: Unknown Exception ${e.message}")
                }
                return XMLResult.toString()
            }

            override fun doInBackground(vararg url: String?): String? {
                  // TODO("not implemented")To change body of created functions use File | Settings | File Templates.
                Log.d(TAG,"doInBackground Called With Parameter: ${url[0]}")
                val RSSFeed = DownloadXML(url[0])
                if(RSSFeed.isEmpty())
                {
                    Log.e(TAG,"doInBackground: Error Downloading XML")
                }
                return RSSFeed
            }

        }
    }
}
