package com.example.tmorimatsu.newsapp

// 1記事を保持(記事url, 画像url・発行日・記事内容)
data class Article(val url: String?, val urlToImage: String?, val publishedAt: String?, val content: String?)