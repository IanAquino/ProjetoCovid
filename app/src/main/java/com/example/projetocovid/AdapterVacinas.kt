package pt.ipg.projetocovid

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetocovid.DadosApp
import com.example.projetocovid.R
import com.example.projetocovid.Vacina

class AdapterVacinas(val fragment: FragmentVacinas): RecyclerView.Adapter<AdapterVacinas.ViewHolderVacina>() {

    public var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderVacina(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewNomelocalItem)
        private val textViewDataVacina = itemView.findViewById<TextView>(R.id.textViewDataVacina)
        private val textViewlocalVacina = itemView.findViewById<TextView>(R.id.textViewlocalItem)

        private lateinit var vacina: Vacina

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaVacina(vacina: Vacina) {
            this.vacina = vacina

            textViewNome.text = vacina.nomeVacina
            textViewDataVacina.text = vacina.data
            textViewlocalVacina.text = vacina.nomelocal
        }


        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }

        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
            DadosApp.vacinaSelecionado = vacina
            DadosApp.activity.atualizaMenuVacinas(true)

        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }



        companion object {
            var selecionado: ViewHolderVacina? = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVacina {
        val itemVacina = DadosApp.fragment.layoutInflater.inflate(R.layout.item_vacina, parent, false)

        return ViewHolderVacina(itemVacina)
    }

    override fun onBindViewHolder(holder: ViewHolderVacina, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaVacina(Vacina.fromCursor(cursor!!))

    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

}