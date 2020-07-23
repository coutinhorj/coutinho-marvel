package br.com.coutinho.marvel.data.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import br.com.coutinho.marvel.data.Api
import br.com.coutinho.marvel.data.model.CharacterList
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSource(
        private val apiMarvel: Api,
        private val composite: CompositeDisposable
) : PageKeyedDataSource<Int, CharacterList>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CharacterList>) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterList>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterList>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

     fun createObservable(requestedPage: Int,
                                 adjacentPage: Int,
                                 requestedLoadSize: Int,
                                 initialCallback: LoadInitialCallback<Int, CharacterList>?,
                                 callback: LoadCallback<Int, CharacterList>?) {
        composite.add(
                apiMarvel.allCharacters(requestedPage * requestedLoadSize)
                        .subscribe(
                                { response ->
                                    initialCallback?.onResult(response.data.results, null, adjacentPage)
                                    callback?.onResult(response.data.results, adjacentPage)
                                },
                                { e ->
                                    Log.d("ERR", "ERROR TO LOAD PAGE: $requestedPage", e)
                                }
                        )
        );
    }
}