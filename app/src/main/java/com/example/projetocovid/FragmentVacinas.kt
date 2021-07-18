package com.example.projetocovid

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetocovid.*
import com.example.projetocovid.databinding.FragmentVacinasBinding


class FragmentVacinas : Fragment(),LoaderManager.LoaderCallbacks<Cursor>{

    private var _binding: FragmentVacinasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapterVacinas : AdapterVacinas? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_lista_vacinas


        _binding = FragmentVacinasBinding.inflate(inflater, container, false)
        return binding.root

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_nova_vacina -> navegaNovaVacina()
            R.id.action_alterar_vacina-> navegaAlterarVacina()
            R.id.action_eliminar_vacina -> navegaEliminarVacina()
            else -> return false
        }

        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_VACINAS, null, this)

        val recyclerViewVacinas = view.findViewById<RecyclerView>(R.id.recyclerViewVacinas)

        adapterVacinas = AdapterVacinas(this)
        recyclerViewVacinas.adapter = adapterVacinas
        recyclerViewVacinas.layoutManager = LinearLayoutManager(requireContext())


    }



    fun navegaNovaVacina() {
        findNavController().navigate(R.id.action_fragmentVacinas22_to_novaVacinaFragment2)
    }
    fun navegaAlterarVacina() {
        findNavController().navigate(R.id.action_fragmentVacinas22_to_editaVacinaFragment)
    }

    fun navegaEliminarVacina() {
        findNavController().navigate(R.id.action_fragmentVacinas22_to_eliminaVacinaFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        adapterVacinas!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterVacinas!!.cursor = null
    }

    companion object{
        const val ID_LOADER_MANAGER_VACINAS = 0
    }
}