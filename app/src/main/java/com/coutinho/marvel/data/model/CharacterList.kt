package br.com.coutinho.marvel.data.model

data class CharacterList(
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: Thumbnail,
        val urls: List<UrlList>
)
