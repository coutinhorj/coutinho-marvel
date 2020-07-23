package br.com.coutinho.marvel.view.characterslist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.coutinho.marvel.R
import br.com.coutinho.marvel.data.model.CharacterList
import br.com.coutinho.marvel.data.model.UrlList
import br.com.coutinho.marvel.infrastructure.utilities.load
import kotlinx.android.synthetic.main.item_character.view.*


class Adapter : PagedListAdapter<CharacterList, Adapter.viewHolder>(characterDiff), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        var txtMoreInfo = view.LinearLayoutCharacter
        txtMoreInfo.setOnClickListener(this)

        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val character = getItem(position)
        holder.imgThumbnail.load("${character?.thumbnail?.path}/standard_medium.${character?.thumbnail?.extension}")
        holder.txtName.text = character?.name
        holder.txtDescription.text = getCharPerLineString(character?.description.toString())
        holder.txtMoreInfo.text = "Click for more info!!!"
        holder.idCharacter.text = (((character as CharacterList).urls as java.util.ArrayList<*>)[1] as UrlList).url.toString()
        holder.linearLayoutCharacter.setOnClickListener(){
            onItemClickCallback
                .onItemClick(((character.urls as java.util.ArrayList<*>)[1] as UrlList).url)
        }
    }

    fun getCharPerLineString(text: String): String? {
        var text = text
        var tenCharPerLineString = ""
        while (text.length > 30) {
            val buffer = text.substring(0, 30)
            tenCharPerLineString = "$tenCharPerLineString$buffer"
            text = text.substring(30)
        }
        tenCharPerLineString = tenCharPerLineString + text.substring(0)
        return tenCharPerLineString
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idCharacter = itemView.idCharacter
        val imgThumbnail = itemView.imgThumbnail
        val txtName = itemView.txtName
        val txtDescription = itemView.txtDescription
        val txtMoreInfo = itemView.txtMoreInfo
        val linearLayoutCharacter = itemView.LinearLayoutCharacter
    }

    companion object {
        private lateinit var onItemClickCallback: OnItemClickCallback

        fun setOnItemClickCallback(
            onItemClickCallback: OnItemClickCallback) {
            this.onItemClickCallback = onItemClickCallback
        }
        interface OnItemClickCallback {
            fun onItemClick(data: String)
        }

        val characterDiff = object: DiffUtil.ItemCallback<CharacterList>() {
            override fun areItemsTheSame(old: CharacterList, new: CharacterList): Boolean {
                return old.id == new.id

            }

            override fun areContentsTheSame(old: CharacterList, new: CharacterList): Boolean {
                return old == new
            }

        }
    }

    override fun onClick(v: View?) {
        var url = v?.idCharacter?.text.toString()
        Log.d("COU","URL - $url")

    }
}