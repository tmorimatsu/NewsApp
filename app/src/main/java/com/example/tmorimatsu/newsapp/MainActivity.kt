package com.example.tmorimatsu.newsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
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
        api.getNews("APIキー", "jp").enqueue(object: Callback<ResponseData> {

            // 通信が失敗したときの処理
            override fun onFailure(call: Call<ResponseData>?, t: Throwable?) {
                // 今回は失敗したときは無視しています。
            }

            // 通信が成功したときの処理
            override fun onResponse(call: Call<ResponseData>?, response: Response<ResponseData>?) {
                // レスポンスのnullチェック
                val res = response?.body() ?: return

                // NewsAdapterへ渡すデータセットを作成
                val dataset = ArrayList<Article>()
                for (article in res.articles) {
                    // 取得できた記事が空であればデータセットに追加しない
                    if(article.content.isNullOrEmpty()) {
                        continue
                    }
                    dataset.add(article)
                }

                // RecyclerViewの紐づけ
                findViewById<RecyclerView>(R.id.list).apply {
                    // リストの罫線を設定
                    addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

                    // RecyclerVeiwの生成したNewsAdapter をセット
                    adapter = NewsAdapter(this@MainActivity, dataset)

                    // 生成したLinearLayoutManagerをセット
                    layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }
        })
    }
}
