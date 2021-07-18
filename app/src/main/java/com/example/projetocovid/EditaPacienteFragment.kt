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
import com.example.projetocovid.TabelaVacinas


class EditaPacienteFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var editTextNomepaciente: EditText
    private lateinit var editTextNrpaciente: EditText
    private lateinit var editTextDataNascimento: EditText
    private lateinit var spinnerVacinas: Spinner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_pacientes


        return inflater.inflate(R.layout.fragment_edita_paciente, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomepaciente= view.findViewById(R.id.editTextEditaNomepaciente)
        editTextNrpaciente = view.findViewById(R.id.editTextEditaNumeroPaciente)
        editTextDataNascimento = view.findViewById(R.id.editTextEditaDataNascimento)
        spinnerVacinas = view.findViewById(R.id.spinnerEditapacientes)


        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_VACINAS, null, this)

        editTextNomepaciente.setText(DadosApp.pacienteSelecionado!!.nome)
        editTextNrpaciente.setText(DadosApp.pacienteSelecionado!!.numeroutente)
        editTextDataNascimento.setText(DadosApp.pacienteSelecionado!!.dnascimento)


    }
    fun navegaLocal() {
        findNavController().navigate(R.id.action_editapacienteFragment22_to_fragmentPaginaInicial)
    }

    fun guardar() {
        val Nomepaciente= editTextNomepaciente.text.toString()
        if (Nomepaciente.isEmpty()) {
            editTextNomepaciente.setError("Preenche este campo")
            editTextNomepaciente.requestFocus()
            return
        }

        val Numeropaciente= editTextNrpaciente.text.toString()
        if (Nomepaciente.isEmpty()) {
            editTextNrpaciente.setError("Preencha este campo")
            editTextNrpaciente.requestFocus()
            return
        }

        val DataNascimento = editTextDataNascimento.text.toString()
        if (DataNascimento.isEmpty()) {
            editTextDataNascimento.setError("Preencha este campo")
            editTextDataNascimento.requestFocus()
            return
        }

        val idVacina = spinnerVacinas.selectedItemId



        val paciente= DadosApp.pacienteSelecionado!!
        paciente.nome = Nomepaciente
        paciente.dnascimento = DataNascimento
        paciente.numeroutente = Numeropaciente
        paciente.idVacina = idVacina


        val uriLocal = Uri.withAppendedPath(
            ContentProviderActivity.ENDEREÇO_pacientes,
            paciente.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriLocal,
            paciente.toContentValues(),
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
            R.id.action_guardar_edita_pacientes -> guardar()
            R.id.action_cancelar_edita_pacientes -> navegaLocal()
            else -> return false
        }

        return true
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        atualizaSpinnerVacinas(data)
        atualizaVacinaSelecionada()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        atualizaSpinnerVacinas(null)
    }

    private fun atualizaSpinnerVacinas(data: Cursor?) {
        spinnerVacinas.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaVacinas.CAMPO_NOME_VACINA),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    private fun atualizaVacinaSelecionada() {
        val idVacina = DadosApp.pacienteSelecionado!!.idVacina

        val ultimaVacina = spinnerVacinas.count - 1
        for (i in 0..ultimaVacina) {
            if (idVacina == spinnerVacinas.getItemIdAtPosition(i)) {
                spinnerVacinas.setSelection(i)
                return
            }
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderActivity.ENDEREÇO_VACINAS,
            TabelaVacinas.TODAS_COLUNAS,
            null, null,
            TabelaVacinas.CAMPO_NOME_VACINA
        )
    }

    companion object {
        const val ID_LOADER_MANAGER_VACINAS = 0
    }
}