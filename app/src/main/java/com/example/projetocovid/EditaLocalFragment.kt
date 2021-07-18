package com.example.projetocovid

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.projetocovid.MainActivity


class EditaLocalFragment : Fragment()  {
    private lateinit var editTextNomelocal: EditText
    private lateinit var editTextCodigoPostal: EditText



    private var param1: String? = null
    private var param2: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_local

        return inflater.inflate(R.layout.fragment_edita_local, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomelocal= view.findViewById(R.id.editTextNomelocal)
        editTextCodigoPostal = view.findViewById(R.id.editTextCodigoPostal)


        editTextNomelocal.setText(DadosApp.locaisSelecionado!!.nome)
        editTextCodigoPostal.setText(DadosApp.locaisSelecionado!!.codigoPostal)

    }
    fun navegaLocal() {
        findNavController().navigate(R.id.action_editalocalFragment_to_fragmentPaginaInicial)
    }

    fun guardar() {
        val NomeCidade = editTextNomelocal.text.toString()
        if (NomeCidade.isEmpty()) {
            editTextNomelocal.setError("Preenche este campo")
            editTextNomelocal.requestFocus()
            return
        }

        val CodigoPostal = editTextCodigoPostal.text.toString()
        if (CodigoPostal.isEmpty()) {
            editTextCodigoPostal.setError("Preencha este campo")
            editTextCodigoPostal.requestFocus()
            return
        }



        val local = DadosApp.locaisSelecionado!!
        local.nome = NomeCidade
        local.codigoPostal = CodigoPostal


        val uriLocal = Uri.withAppendedPath(
            ContentProviderActivity.ENDEREÇO_LOCALIZACAO,
            local.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriLocal,
            local.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                "Erro ao alterar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            "Alterado com sucesso",
            Toast.LENGTH_LONG
        ).show()
        navegaLocal()
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_localizacao -> guardar()
            R.id.action_cancelar_edita_localizacao -> navegaLocal()
            else -> return false
        }

        return true
    }
}