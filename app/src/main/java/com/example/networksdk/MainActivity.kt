package com.example.networksdk

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.networksdk.databinding.ActivityMainBinding
import com.example.networksdk.response.PopularListResponse
import com.example.request_get.NetworkBuilder
import com.example.request_get.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeStatusBarTransparent()


        /**Sample Example
         * 1: Build Network
         * 2: Specify the type of Response
         * 3: get the specified response using get()
         *    inside coroutine.
         * */
        NetworkBuilder.Build()
            .baseUrl(Constants.BASE_URL)
            .endPoint(Constants.END_POINT_POPULAR_LIST)
            .path("")
            .query(
                hashMapOf(
                    Constants.KEY_QUERY_PAGE to "1",
                    Constants.KEY_QUERY_KEY to Constants.API_KEY
                )
            )
            .create()

        val popularMovieListResponse = NetworkCall<PopularListResponse>(
            responseType = PopularListResponse::class.java
        ) { data, message ->
            lifecycleScope.launch(Dispatchers.Main) {
                showToast("Response Code:$message")
                binding.tvName.text = data.toString()
            }
        }

        lifecycleScope.launch {
            popularMovieListResponse.get()
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun makeStatusBarTransparent() {
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}