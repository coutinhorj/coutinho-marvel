package br.com.coutinho.marvel.data.model

data class Response(
        val code: Int,
        val etag: String,
        val data: Data
)