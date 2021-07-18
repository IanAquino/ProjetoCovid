package com.example.projetocovid

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.example.projetocovid.MainActivity
import com.example.projetocovid.TabelaLocais
import com.example.projetocovid.databinding.FragmentNovoLocalBinding


class NovoLocalFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentNovoLocalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var editTextNomeNovoLocal: EditText
    private lateinit var editTextNovoCodigoPostal: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_local

        _binding = FragmentNovoLocalBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextNomeNovoLocal = view.findViewById(R.id.editTextNomeNovoLocal)
        editTextNovoCodigoPostal = view.findViewById(R.id.editTextCodigoPostalNovo)


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun navegaLocal() {
        findNavController().navigate(R.id.action_novalocalFragment_to_fragmentPaginaInicial)
    }

    fun guardar() {
        val Novalocal = editTextNomeNovoLocal.text.toString()
        if (Novalocal.isEmpty()) {
            editTextNomeNovoLocal.setError("Preencha este campo")
            editTextNomeNovoLocal.requestFocus()
            return
        }

        val NovoCodigoPostal = editTextNovoCodigoPostal.text.toString()
        if (NovoCodigoPostal.isEmpty()) {
            editTextNovoCodigoPostal.setError("Preencha este campo")
            editTextNovoCodigoPostal.requestFocus()
            return
        }


        val local = Locais(nome = Novalocal, codigoPostal = NovoCodigoPostal)

        val uri = activity?.contentResolver?.insert(
            ContentProviderActivity.ENDEREÇO_LOCALIZACAO,
            local.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNomeNovoLocal,
                ("Erro ao inserir "),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        Toast.makeText(
            requireContext(),
            "Locais gravados com sucesso",
            Toast.LENGTH_LONG
        ).show()

        navegaLocal()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_nova_localizacao -> guardar()
            R.id.action_cancelar_nova_localizacao -> navegaLocal()
            else -> return false
        }

        return true
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderActivity.ENDEREÇO_LOCALIZACAO,
            TabelaLocais.TODAS_COLUNAS,
            null, null,
            TabelaLocais.NOME_LOCAL
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }
}