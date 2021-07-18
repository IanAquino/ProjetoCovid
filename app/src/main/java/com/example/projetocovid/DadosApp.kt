package com.example.projetocovid

import androidx.fragment.app.Fragment
import pt.ipg.projetocovid.MainActivity

class DadosApp {
    companion object {

        lateinit var activity: MainActivity
        lateinit var fragment: Fragment

        var locaisSelecionado : Locais? = null
        var pacienteSelecionado: Paciente? = null
        var vacinaSelecionado: Vacina? = null
    }
}