package com.example.projetocovid

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.projetocovid.MainActivity


class EliminaVacinaFragment : Fragment() {

    private lateinit var textViewNomeVacinaEliminar: TextView
    private lateinit var textViewDataVacinaEliminar: TextView
    private lateinit var textViewlocalVacinaEliminar: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_vacina

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_vacina, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNomeVacinaEliminar = view.findViewById(R.id.textViewEliminaNomeVacina2)
        textViewDataVacinaEliminar = view.findViewById(R.id.textViewEliminaDataVacina2)
        textViewlocalVacinaEliminar = view.findViewById(R.id.textViewEliminalocalVacina2)

        val vacina = DadosApp.vacinaSelecionado!!
        textViewNomeVacinaEliminar.setText(vacina.nomeVacina)
        textViewDataVacinaEliminar.setText(vacina.data)
        textViewlocalVacinaEliminar.setText(vacina.nomelocal)

    }

    fun navegaLocal() {
        findNavController().navigate(R.id.action_eliminaVacinaFragment_to_fragmentPaginaInicial)
    }

    fun elimina() {
        val uriVacina = Uri.withAppendedPath(
            ContentProviderActivity.ENDEREÇO_VACINAS,
            DadosApp.vacinaSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriVacina,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                "Erro ao eliminar local",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            "Locais eliminado com sucesso",
            Toast.LENGTH_LONG
        ).show()

        navegaLocal()
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_vacina -> elimina()
            R.id.action_cancelar_eliminar_vacina -> navegaLocal()
            else -> return false
        }

        return true
    }
}