package com.example.projetocovid
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import com.example.projetocovid.TabelaPacientes
import java.sql.Date

data class Paciente(var id: Long = -1, var nome: String, var dnascimento: String, var numeroutente: String, var idVacina: Long, var nomeVacina: String? = null) {
    fun toContentValues(): ContentValues{
        val valores = ContentValues().apply {
            put(TabelaPacientes.CAMPO_NOME_PACIENTE,nome)
            put(TabelaPacientes.CAMPO_DATA_NASCIMENTO,dnascimento)
            put(TabelaPacientes.CAMPO_NUMERO_UTENTE,numeroutente)
            put(TabelaPacientes.CAMPO_ID_VACINA,idVacina)
        }
        return valores
    }


    companion object{
        fun fromCursor(cursor: Cursor): Paciente{
            val colunaId = cursor.getColumnIndex(BaseColumns._ID)
            val colunaNomepaciente= cursor.getColumnIndex(TabelaPacientes.CAMPO_NOME_PACIENTE)
            val colunaDatanascimento = cursor.getColumnIndex(TabelaPacientes.CAMPO_DATA_NASCIMENTO)
            val colunaNumeroutente = cursor.getColumnIndex(TabelaPacientes.CAMPO_NUMERO_UTENTE)
            val colunaIdVacina = cursor.getColumnIndex(TabelaPacientes.CAMPO_ID_VACINA)
            val colunaNomeVacina = cursor.getColumnIndex(TabelaPacientes.CAMPO_NOME_VACINA)

            val id= cursor.getLong(colunaId)
            val nomepaciente= cursor.getString(colunaNomepaciente)
            val datanascimento = cursor.getString(colunaDatanascimento)
            val numeroutente = cursor.getString(colunaNumeroutente)
            val idVacina = cursor.getLong(colunaIdVacina)
            val nomeVacina = if (colunaNomeVacina != -1) cursor.getString(colunaNomeVacina) else null

            return Paciente(id ,nomepaciente, datanascimento , numeroutente,idVacina, nomeVacina)

        }
    }
}