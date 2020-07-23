package com.coutinho.marvel.view.characterlist

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import br.com.coutinho.marvel.data.datasource.CharactersDataSourceFactory
import br.com.coutinho.marvel.data.model.CharacterList
import br.com.coutinho.marvel.view.characterslist.CharacterViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class ViewModelTest {
    private val sourceFactory: CharactersDataSourceFactory
        get() {
            TODO()
        }
    var characterList: Observable<PagedList<CharacterList>>
        get() {
            TODO()
        }
        set(value) {}

    @Test
    fun `teste viewModel` (){
        var viewModel = instantiateWiewMode()

        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20 * 2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()
        characterList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()

    }
    private fun instantiateWiewMode(): ViewModel {
        return CharacterViewModel()
    }
}