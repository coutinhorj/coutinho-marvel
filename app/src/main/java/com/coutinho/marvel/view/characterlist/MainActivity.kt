package br.com.coutinho.marvel.view.characterslist


import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import br.com.coutinho.marvel.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_characters.*

 class MainActivity : AppCompatActivity() {

     private val viewModel: CharacterViewModel by lazy {
         ViewModelProviders.of(this).get(CharacterViewModel::class.java)
     }

     private val adapter: Adapter by lazy {
         Adapter()
     }

     private var recyclerState: Parcelable? = null

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_characters)
         val llm = LinearLayoutManager(this)
         recyclerCharacters.layoutManager = llm
         recyclerCharacters.adapter = adapter
         subscribeToList()
         setListClickAction()
     }

     private fun setListClickAction() {
         Adapter.setOnItemClickCallback(
             object : Adapter.Companion.OnItemClickCallback {
                 override fun onItemClick(data: String) {
                     teste(data)
                 }
             })
     }

     override fun onSaveInstanceState(outState: Bundle?) {
         super.onSaveInstanceState(outState)
         outState?.putParcelable("State", recyclerCharacters.layoutManager?.onSaveInstanceState())
     }

     override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
         super.onRestoreInstanceState(savedInstanceState)
         recyclerState = savedInstanceState?.getParcelable("State")
     }

     private fun subscribeToList() {
         val disposable = viewModel.characterList
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(
                 { list ->
                     adapter.submitList(list)
                     if (recyclerState != null) {
                         recyclerCharacters.layoutManager?.onRestoreInstanceState(recyclerState)
                         recyclerState = null
                     }
                 },
                 { e ->
                     Log.e("ERR", "Error", e)
                 }
             )
     }

     fun teste(url: String) {
         intent = Intent(this,MoreDescriprionActivity::class.java)
         intent.putExtra("url",url)
         startActivity(intent)
         Log.d("COU","passou por aqui")
     }

 }
