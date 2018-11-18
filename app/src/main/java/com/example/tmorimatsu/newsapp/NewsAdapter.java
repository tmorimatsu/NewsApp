package com.example.tmorimatsu.newsapp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private Context context;
    private List<Article> dataset;

    NewsAdapter(Context context, List<Article> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    // 1行のレイアウトをセットします
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.news_row, parent, false));
    }

    // リストに表示する行数を返します
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    // 1行のレイアウトの内容をセットします
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        // この行の記事を取得
        final Article article = dataset.get(position);

        // GlideによりimageToUrlの画像を holder.imageView にセット
        RequestOptions options = new RequestOptions().centerCrop();
        Glide.with(context).load(article.urlToImage).apply(options).into(holder.imageView);

        // サマリ・公開日をそれぞれセット
        // (公開日は時間まで含まれているのではじめの10文字を切り抜き年月日のみが表示される用に加工しています)
        holder.descriptionText.setText(article.content);
        holder.publishText.setText(article.publishedAt.substring(0, 10));

        // 行全体に対してクリック時の処理をセット
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // urlのページを表示
                new CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(article.url));
            }
        });
    }
}
