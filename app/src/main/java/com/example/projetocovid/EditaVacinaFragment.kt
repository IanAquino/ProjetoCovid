package com.example.projetocovid

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.projetocovid.MainActivity
import com.example.projetocovid.TabelaLocais


class EditaVacinaFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor>  {

    private lateinit var editTextNomeVacina: EditText
    private lateinit var editTextDataVacina: EditText
    private lateinit var spinnerlocals: Spinner



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_vacina

        return inflater.inflate(R.layout.fragment_edita_vacina, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeVacina= view.findViewById(R.id.editTextEditaNomeVacina)
        editTextDataVacina = view.findViewById(R.id.editTextEditaDataVacina)
        spinnerlocals = view.findViewById(R.id.spinnerEditaVacinas)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_CATEGORIAS, null, this)

        editTextNomeVacina.setText(DadosApp.vacinaSelecionado!!.nomeVacina)
        editTextDataVacina.setText(DadosApp.vacinaSelecionado!!.data)

    }

    fun navegaLocal() {
        findNavController().navigate(R.id.action_editaVacinaFragment_to_fragmentPaginaInicial)
    }

    fun guardar() {
        val NomeVacina = editTextNomeVacina.text.toString()
        if (NomeVacina.isEmpty()) {
            editTextNomeVacina.setError("Preenche este campo")
            editTextNomeVacina.requestFocus()
            return
        }

        val DataVacina = editTextDataVacina.text.toString()
        if (DataVacina.isEmpty()) {
            editTextDataVacina.setError("Preencha este campo")
            editTextDataVacina.requestFocus()
            return
        }

        val idlocal = spinnerlocals.selectedItemId

        val vacina = DadosApp.vacinaSelecionado!!
        vacina.nomeVacina = NomeVacina
        vacina.data = DataVacina
        vacina.idlocal = idlocal


        val uriLocal = Uri.withAppendedPath(
            ContentProviderActivity.ENDEREÇO_VACINAS,
            vacina.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriLocal,
            vacina.toContentValues(),
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
            R.id.action_guardar_edita_vacina -> guardar()
            R.id.action_cancelar_edita_vacina -> navegaLocal()
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
        atualizaSpinnerlocals(data)
        atualizalocalSelecionada()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        atualizaSpinnerlocals(null)
    }

    private fun atualizaSpinnerlocals(data: Cursor?) {
        spinnerlocals.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaLocais.NOME_LOCAL),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    private fun atualizalocalSelecionada() {
        val idlocal = DadosApp.vacinaSelecionado!!.idlocal

        val ultimalocal = spinnerlocals.count - 1
        for (i in 0..ultimalocal) {
            if (idlocal == spinnerlocals.getItemIdAtPosition(i)) {
                spinnerlocals.setSelection(i)
                return
            }
        }
    }

    companion object {
        const val ID_LOADER_MANAGER_CATEGORIAS = 0
    }
}