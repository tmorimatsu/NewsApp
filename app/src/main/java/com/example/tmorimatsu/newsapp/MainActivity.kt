package com.example.tmorimatsu.newsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // レイアウトファイルのセット
        setContentView(R.layout.activity_main)

        // Retrofitクライアントの取得
        val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/").addConverterFactory(GsonConverterFactory.create()).build()
        // APIエンドポイントの生成
        val api = retrofit.create(ApiService::class.java)
        // 引数によってapiエンドポイントを指定し、リクエスト
        api.getNews("aa3faa9851384a3580a72712fe6f5af0", "jp").enqueue(object: Callback<ResponseData> {

            // 通信が失敗したときの処理
            override fun onFailure(call: Call<ResponseData>?, t: Throwable?) {
                // 今回は失敗したときは無視しています。
            }

            // 通信が成功したときの処理
            override fun onResponse(call: Call<ResponseData>?, response: Response<ResponseData>?) {
                // 紐づけたTextVeiwに取得したデータをそのまま表示
                findViewById<TextView>(R.id.textview).text = response?.body().toString()
            }
        })
    }
}
