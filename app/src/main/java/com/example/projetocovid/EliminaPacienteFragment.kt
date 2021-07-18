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


class EliminaPacienteFragment : Fragment() {

    private lateinit var textViewNomepacienteEliminar: TextView
    private lateinit var textViewNumeropacienteEliminar: TextView
    private lateinit var textViewDatapacienteEliminar: TextView
    private lateinit var textViewVacinapacienteEliminar: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_pacientes

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_paciente, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNomepacienteEliminar = view.findViewById(R.id.textViewNomepacienteEliminar2)
        textViewNumeropacienteEliminar = view.findViewById(R.id.textViewNumeropacienteEliminar2)
        textViewDatapacienteEliminar = view.findViewById(R.id.textViewDatapacienteEliminar2)
        textViewVacinapacienteEliminar = view.findViewById(R.id.textViewVacinapacienteEliminar2)



        val paciente= DadosApp.pacienteSelecionado!!
        textViewNomepacienteEliminar.setText(paciente.nome)
        textViewNumeropacienteEliminar.setText(paciente.numeroutente)
        textViewDatapacienteEliminar.setText(paciente.dnascimento)
        textViewVacinapacienteEliminar.setText(paciente.nomeVacina)

    }

    fun navegaLocal() {
        findNavController().navigate(R.id.action_eliminapacienteFragment_to_fragmentPaginaInicial)
    }

    fun elimina() {
        val uriLocal = Uri.withAppendedPath(
            ContentProviderActivity.ENDEREÇO_pacientes,
            DadosApp.pacienteSelecionado!!.id.toString()
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
            "Locais eliminados com sucesso",
            Toast.LENGTH_LONG
        ).show()

        navegaLocal()
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_pacientes -> elimina()
            R.id.action_cancelar_eliminar_pacientes -> navegaLocal()
            else -> return false
        }

        return true
    }
}