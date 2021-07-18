package pt.ipg.projetocovid

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetocovid.DadosApp
import com.example.projetocovid.Locais
import com.example.projetocovid.R

class AdapterLocal (val fragment: pt.ipg.projetocovid.FragmentLocais): RecyclerView.Adapter<pt.ipg.projetocovid.AdapterLocal.ViewHolderLocal>() {
    public var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class ViewHolderLocal(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewNomelocalItem)
        private val textViewCodigoPostal = itemView.findViewById<TextView>(R.id.textViewCodigoPostalItem)


        private lateinit var locais: Locais

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaLocal(locais: Locais) {
            this.locais = locais

            textViewNome.text = locais.nome
            textViewCodigoPostal.text = locais.codigoPostal
        }
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }

        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
            DadosApp.Companion.locaisSelecionado = locais
           DadosApp.Companion.activity.atualizaMenuLocais(true)
        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selecionado : ViewHolderLocal? = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolderLocal {
        val itemLocal = fragment.layoutInflater.inflate(R.layout.item_local, parent, false)

        return ViewHolderLocal(itemLocal)
    }

    override fun onBindViewHolder(holder: ViewHolderLocal, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaLocal(
            Locais.Companion.fromCursor(
                cursor!!
            )
        )
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}


