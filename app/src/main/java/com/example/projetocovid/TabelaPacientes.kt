package pt.ipg.projetocovid

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPacientes(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME_PACIENTE TEXT NOT NULL, $CAMPO_NUMERO_UTENTE NUMERIC NOT NULL, $CAMPO_DATA_NASCIMENTO INTEGER NOT NULL, $CAMPO_ID_VACINA NUMERIC NOT NULL, FOREIGN KEY ($CAMPO_ID_VACINA) REFERENCES ${TabelaVacinas.NOME_TABELA})")
    }

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {

        val ultimaColuna = columns.size - 1

         var posColunaNomeVacina = -1 // -1 indica que a coluna não foi pedida
         for (i in 0..ultimaColuna) {
             if (columns[i] == CAMPO_EXTERNO_NOME_VACINA) {
                 posColunaNomeVacina = i
                 break
             }
         }

         if (posColunaNomeVacina == -1) {
             return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
         }

         var colunas = ""
         for (i in 0..ultimaColuna) {
             if (i > 0) colunas += ","

             colunas += if (i == posColunaNomeVacina) {
                 "${TabelaVacinas.NOME_TABELA}.${TabelaVacinas.CAMPO_NOME_VACINA} AS $CAMPO_EXTERNO_NOME_VACINA"
             } else {
                 "${NOME_TABELA}.${columns[i]}"
             }
         }

         val tabelas = "$NOME_TABELA INNER JOIN ${TabelaVacinas.NOME_TABELA} ON ${TabelaVacinas.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_VACINA"

         var sql = "SELECT $colunas FROM $tabelas"

         if (selection != null) sql += " WHERE $selection"

         if (groupBy != null) {
             sql += " GROUP BY $groupBy"
             if (having != null) " HAVING $having"
         }

         if (orderBy != null) sql += " ORDER BY $orderBy"

         return db.rawQuery(sql, selectionArgs)
    }




    companion object {
        const val NOME_TABELA = "TabelaPacientes"
        const val CAMPO_NOME_PACIENTE= "Nome"
        const val CAMPO_NUMERO_UTENTE = "Numeroutente"
        const val CAMPO_DATA_NASCIMENTO = "DataNascimento"
        const val CAMPO_ID_VACINA = "IdVacina"
        const val CAMPO_EXTERNO_NOME_VACINA = "NomeVacina"


        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME_PACIENTE, CAMPO_NUMERO_UTENTE, CAMPO_DATA_NASCIMENTO, CAMPO_ID_VACINA, CAMPO_EXTERNO_NOME_VACINA)
    }
}