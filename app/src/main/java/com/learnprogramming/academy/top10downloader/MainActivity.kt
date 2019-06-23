package com.learnprogramming.academy.top10downloader

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"OnCreate Called")
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

            override fun doInBackground(vararg params: String?): String? {
                  // TODO("not implemented")To change body of created functions use File | Settings | File Templates.
                Log.d(TAG,"doInBackground Called With Parameter: ${params[0]}")
                return "doInBackground Complete"
            }

        }
    }
}
