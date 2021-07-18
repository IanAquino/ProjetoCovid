package pt.ipg.projetocovid

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
import com.example.projetocovid.ContentProviderActivity
import com.example.projetocovid.DadosApp
import com.example.projetocovid.R
import pt.ipg.projetocovid.databinding.FragmentLocalBinding

class FragmentLocais : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentLocalBinding? = null
    private var adapterLocais : AdapterLocal? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_lista_local

        _binding = FragmentLocalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewLocal = view.findViewById<RecyclerView>(R.id.recyclerViewlocals)
        adapterLocais = AdapterLocal(this)
        recyclerViewLocal.adapter = adapterLocais
        recyclerViewLocal.layoutManager = LinearLayoutManager(requireContext())


        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_LOCAIS, null, this)


    }
    fun navegaNovoLocal() {
        findNavController().navigate(R.id.action_fragmentlocals2_to_novalocalFragment)
    }
    fun navegaAlterarLocal() {
        findNavController().navigate(R.id.action_fragmentlocals2_to_editalocalFragment)

    }

    fun navegaEliminarLocal() {
        findNavController().navigate(R.id.action_fragmentlocals2_to_eliminalocalFragment)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_nova_localizacao -> navegaNovoLocal()
            R.id.action_alterar_localizacao -> navegaAlterarLocal()
            R.id.action_eliminar_localizacao -> navegaEliminarLocal()
            else -> return false
        }

        return true
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        adapterLocais!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterLocais!!.cursor = null
    }

    companion object {
        const val ID_LOADER_MANAGER_LOCAIS = 0
    }
}