package com.example.tmorimatsu.newsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウトファイルのセット
        setContentView(R.layout.activity_main);

        // Retrofitクライアントの取得
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org/").addConverterFactory(GsonConverterFactory.create()).build();
        // APIエンドポイントの生成
        ApiService api = retrofit.create(ApiService.class);
        // 引数によってapiエンドポイントを指定し、リクエスト
        api.getNews("APIキー", "jp").enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(@NonNull Call<ResponseData> call, @NonNull Response<ResponseData> response) {
                // レスポンスのnullチェック
                ResponseData res = response.body();
                if (res == null) {
                    return;
                }

                // NewsAdapterへ渡すデータセットを作成
                List<Article> dataset = new ArrayList<>();
                for (Article article: res.articles) {
                    // 取得できた記事が空であればデータセットに追加しない
                    if(article.content == null || article.content.isEmpty()) {
                        continue;
                    }
                    dataset.add(article);
                }

                // RecyclerViewの紐づけ
                RecyclerView list = findViewById(R.id.list);

                        // NewsAdapterの生成
                NewsAdapter adapter = new NewsAdapter(MainActivity.this, dataset);

                // ①
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

                // リストの罫線を設定
                list.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

                // RecyclerVeiwの生成したNewsAdapter をセット
                list.setAdapter(adapter);

                // 生成したLinearLayoutManagerをセット
                list.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseData> call, @NonNull Throwable t) {

            }
        });
    }
}