package com.example.projetocovid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import pt.ipg.projetocovid.TabelaVacinas

data class Vacina(var id: Long = -1, var nomeVacina: String, var data: String, var idlocal: Long, var nomelocal: String? = null) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues().apply {
            put(TabelaVacinas.CAMPO_NOME_VACINA,nomeVacina)
            put(TabelaVacinas.CAMPO_DATA,data)
            put(TabelaVacinas.CAMPO_ID_LOCAL,idlocal)

        }
        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor) : Vacina{
            val colunaId = cursor.getColumnIndex(BaseColumns._ID)
            val colunaNomeVacina = cursor.getColumnIndex(TabelaVacinas.CAMPO_NOME_VACINA)
            val colunaData = cursor.getColumnIndex(TabelaVacinas.CAMPO_DATA)
            val colunaIdlocal = cursor.getColumnIndex(TabelaVacinas.CAMPO_ID_LOCAL)
            val colunaNomelocal = cursor.getColumnIndex(TabelaVacinas.CAMPO_NOME_LOCAL)


            val id = cursor.getLong(colunaId)
            val nomeVacina = cursor.getString(colunaNomeVacina)
            val data = cursor.getString(colunaData)
            val idlocal = cursor.getLong(colunaIdlocal)
            val nomelocal = if (colunaNomelocal != -1) cursor.getString(colunaNomelocal) else null


            return Vacina( id,nomeVacina,data, idlocal, nomelocal)
        }
    }
}