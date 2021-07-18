package pt.ipg.projetocovid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.projetocovid.R
import pt.ipg.projetocovid.R.menu.*
import pt.ipg.projetocovid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu
    var menuAtual = menu_lista_pacientes

        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        DadosApp.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        menuInflater.inflate(menuAtual, menu)
        this.menu = menu

        if (menuAtual == menu_lista_pacientes ) {
            atualizaMenuPacientes(false)
        }
        if (menuAtual == menu_lista_local ) {
            atualizaMenuLocais(false)
        }
        if (menuAtual == menu_lista_vacinas ) {
            atualizaMenuVacinas(false)
        }


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val opcaoProcessada = when (item.itemId) {

            R.id.action_settings -> {
                Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()
                true
            }
            else -> when(menuAtual) {
                menu_lista_pacientes -> (DadosApp.fragment as FragmentPacientes).processaOpcaoMenu(item)
                menu_lista_local -> (DadosApp.fragment as FragmentLocais).processaOpcaoMenu(item)
                menu_lista_vacinas -> (DadosApp.fragment as FragmentVacinas).processaOpcaoMenu(item)
                menu_novo_paciente -> (DadosApp.fragment as NovoPacienteFragment).processaOpcaoMenu(item)
                menu_novo_local -> (DadosApp.fragment as NovoLocalFragment).processaOpcaoMenu(item)
                menu_nova_vacina -> (DadosApp.fragment as NovaVacinaFragment).processaOpcaoMenu(item)
                menu_pagina_inicial -> (DadosApp.fragment as FragmentPaginaInicial).processaOpcaoMenu(item)
                menu_edita_local -> (DadosApp.fragment as EditaLocalFragment).processaOpcaoMenu(item)
                menu_edita_vacina -> (DadosApp.fragment as EditaVacinaFragment).processaOpcaoMenu(item)
                menu_edita_pacientes -> (DadosApp.fragment as EditaPacienteFragment).processaOpcaoMenu(item)
                menu_elimina_local -> (DadosApp.fragment as EliminaLocalFragment).processaOpcaoMenu(item)
                else -> false
            }
        }
        return if(opcaoProcessada) true else super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun atualizaMenuPacientes(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_paciente).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_paciente).setVisible(mostraBotoesAlterarEliminar)
    }
    fun atualizaMenuLocais(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_localizacao).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_localizacao).setVisible(mostraBotoesAlterarEliminar)
    }
    fun atualizaMenuVacinas(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_vacina).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_vacina).setVisible(mostraBotoesAlterarEliminar)
    }
}