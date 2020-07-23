package br.com.coutinho.marvel.data.datasource

import androidx.paging.DataSource
import br.com.coutinho.marvel.data.Api
import br.com.coutinho.marvel.data.model.CharacterList
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSourceFactory(
        private val composite: CompositeDisposable,
        private val apiMarvel: Api
) : DataSource.Factory<Int, CharacterList>() {

    override fun create(): DataSource<Int, CharacterList> {
        return CharactersDataSource(apiMarvel, composite)
    }
}