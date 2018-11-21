package com.example.tmorimatsu.newsapp

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class NewsAdapter(private val context: Context, private val dataset: List<Article>): RecyclerView.Adapter<NewsViewHolder>() {

    // 1行のレイアウトをセットします
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder = NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_row, parent, false))

    // リストに表示する行数を返します
    override fun getItemCount(): Int = dataset.size

    // 1行のレイアウトの内容をセットします
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        // この行の記事を取得
        val article = dataset[position]

        // GlideによりimageToUrlの画像を holder.imageView にセット
        val options = RequestOptions().centerCrop()
        Glide.with(context).load(article.urlToImage).apply(options).into(holder.imageView)

        // サマリ・公開日をそれぞれセット
        // (公開日は時間まで含まれているのではじめの10文字を切り抜き年月日のみが表示される用に加工しています)
        holder.descriptionText.text = article.content
        holder.publishText.text = article.publishedAt?.substring(0, 10)

        // 行全体に対してクリック時の処理をセット
        holder.row.setOnClickListener {
            // urlのページを表示
            CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(article.url))
        }
    }
}