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
import pt.ipg.projetocovid.MainActivity


class EliminaLocalFragment : Fragment() {

    private lateinit var textViewlocalEliminar: TextView
    private lateinit var textViewCodigoPostalEliminar: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_local

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_local, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewlocalEliminar = view.findViewById(R.id.textViewEliminaNomeLocalizacao2)
        textViewCodigoPostalEliminar = view.findViewById(R.id.textViewEliminaCodigoPostal2)


        val local = DadosApp.locaisSelecionado!!
        textViewlocalEliminar.setText(local.nome)
        textViewCodigoPostalEliminar.setText(local.codigoPostal)

    }

    fun navegaLocal() {
        findNavController().navigate(R.id.action_eliminalocalFragment_to_fragmentPaginaInicial)
    }

    fun elimina() {
        val uriLocal = Uri.withAppendedPath(
            ContentProviderActivity.ENDEREÇO_LOCALIZACAO,
            DadosApp.locaisSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriLocal,
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
            R.id.action_confirma_eliminar_local -> elimina()
            R.id.action_cancelar_eliminar_local -> navegaLocal()
            else -> return false
        }

        return true
    }
}