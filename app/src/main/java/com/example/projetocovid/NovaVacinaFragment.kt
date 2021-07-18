package com.example.projetocovid

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.example.projetocovid.MainActivity
import com.example.projetocovid.TabelaLocais
import com.example.projetocovid.databinding.FragmentNovaVacinaBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovaVacinaFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentNovaVacinaBinding? = null

    private lateinit var editTextNomeVacina: EditText
    private lateinit var editTextDataVacina: EditText
    private lateinit var spinnerlocals: Spinner

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_vacina

        _binding = FragmentNovaVacinaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeVacina = view.findViewById(R.id.TextInputNomeVacina)
        editTextDataVacina = view.findViewById(R.id.TextInputDataVacina)
        spinnerlocals = view.findViewById(R.id.spinnerlocals)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_VACINAS, null, this)

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_nova_vacina -> guardar()
            R.id.action_cancelar_nova_vacina -> navegaListaVacinas()
            else -> return false
        }

        return true
    }

    fun guardar() {
        val NovaVacina = editTextNomeVacina.text.toString()
        if (NovaVacina.isEmpty()) {
            editTextNomeVacina.setError("Preencha este campo")
            editTextNomeVacina.requestFocus()
            return
        }

        val DataVacina = editTextDataVacina.text.toString()
        if  (DataVacina.isEmpty()) {
            editTextDataVacina.setError("Preencha este campo")
            editTextDataVacina.requestFocus()
            return
        }
        


        val idlocal = spinnerlocals.selectedItemId

        val vacina = Vacina(nomeVacina = NovaVacina, data = DataVacina, idlocal = idlocal)

        val uri = activity?.contentResolver?.insert(
            ContentProviderActivity.ENDEREÇO_VACINAS,
            vacina.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNomeVacina,
                ("Erro ao inserir "),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        Toast.makeText(
            requireContext(),
            "Vacina gravada com sucesso",
            Toast.LENGTH_LONG
        ).show()

        navegaListaVacinas()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaListaVacinas() {
        findNavController().navigate(R.id.action_novaVacinaFragment2_to_fragmentVacinas22)
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
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        atualizaSpinnerlocals(data = null)
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

    companion object {
        const val ID_LOADER_MANAGER_VACINAS = 0
    }

}