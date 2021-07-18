package com.example.projetocovid

import android.database.Cursor
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
import com.google.android.material.snackbar.Snackbar
import com.example.projetocovid.MainActivity
import com.example.projetocovid.TabelaVacinas
import com.example.projetocovid.databinding.FragmentNovoPacienteBinding
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovoPacienteFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentNovoPacienteBinding? = null

    private lateinit var editTextNomepaciente: EditText
    private lateinit var editTextNumeroutente: EditText
    private lateinit var editTextDataNascimento: EditText
    //private lateinit var editTextCodigoPostal: EditText
    private lateinit var spinnerVacinas: Spinner

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_paciente

        _binding = FragmentNovoPacienteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomepaciente= view.findViewById(R.id.TextInputNomepaciente)
        editTextNumeroutente= view.findViewById(R.id.TextInputNumeroutente)
        editTextDataNascimento = view.findViewById(R.id.TextInputDataNascimento)
        spinnerVacinas = view.findViewById(R.id.spinnerVacinas)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_VACINAS, null, this)

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_paciente-> guardar()
            R.id.action_cancelar_novo_paciente-> navegaListapacientes()
            else -> return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaListapacientes() {
        findNavController().navigate(R.id.action_NovopacienteFragment_to_pacientesFragment)
    }

    fun guardar() {
        val Novopaciente= editTextNomepaciente.text.toString()
        if (Novopaciente.isEmpty()) {
            editTextNomepaciente.setError("Preencha este campo")
            editTextNomepaciente.requestFocus()
            return
        }

        val numeroutente= editTextNumeroutente.text.toString()
        if (numeroutente.isEmpty()) {
            editTextNumeroutente.setError("Preencha este campo")
            editTextNumeroutente.requestFocus()
            return
        }

        val dataNascimento = editTextDataNascimento.text.toString()
        if (dataNascimento.isEmpty()) {
            editTextDataNascimento.setError("Preencha este campo")
            editTextDataNascimento.requestFocus()
            return
        }

        val idVacina = spinnerVacinas.selectedItemId


        val paciente= Paciente(nome = Novopaciente, numeroutente = numeroutente, dnascimento = dataNascimento , idVacina = idVacina)

        val uri = activity?.contentResolver?.insert(
            ContentProviderActivity.ENDEREÇO_pacientes,
            paciente.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNomepaciente,
                ("Erro ao inserir "),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        Toast.makeText(
            requireContext(),
            "Paciente gravado com sucesso",
            Toast.LENGTH_LONG
        ).show()

        navegaListapacientes()
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

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        atualizaSpinnerVacinas(data)
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

    companion object {
        const val ID_LOADER_MANAGER_VACINAS = 0
    }

}