package com.example.projetocovid
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import pt.ipg.projetocovid.TabelaLocais

data class Locais(var id: Long = -1, var nome: String, var codigoPostal: String) {
    fun toContentValues() : ContentValues{
        val valores = ContentValues()
        valores.put(TabelaLocais.NOME_LOCAL,nome)
        valores.put(TabelaLocais.CODIGO_POSTAL,codigoPostal)


        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor) : Locais{
            val colunaId = cursor.getColumnIndex(BaseColumns._ID)
            val colunaNome = cursor.getColumnIndex(TabelaLocais.NOME_LOCAL)
            val colunaCodigoPostal = cursor.getColumnIndex(TabelaLocais.CODIGO_POSTAL)

            val id = cursor.getLong(colunaId)
            val nome = cursor.getString(colunaNome)
            val codigoPostal = cursor.getString(colunaCodigoPostal)


            return Locais( id, nome, codigoPostal)
        }
    }
}