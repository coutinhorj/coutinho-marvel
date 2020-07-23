package br.com.coutinho.marvel.data.model

data class Data(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<CharacterList>
)