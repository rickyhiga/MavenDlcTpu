/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
//DBAccessSQLite deberia llamarse
public abstract class DBAccessMySql extends AbstractDB {
    /*
     public DBAccessMySql(String dbFile) {
     super.dbPath.append(dbFile);
     super.crearTablas();
     }

     */

    private int getIntFromQuery() throws SQLException {
        ResultSet returnRows = super.getResultsFromQuery();
        int i = -1;
        if (returnRows.next()) {
            i = returnRows.getInt(1);
        }
        return i;
    }

    public int getId(String tabla, String clave, String condicion) throws SQLException {

        int id = -1;
        StringBuilder st = new StringBuilder("SELECT ");
        st.append(clave);
        st.append(" AS id FROM ");
        st.append(tabla);
        st.append(" WHERE ");
        st.append(condicion);
        st.append(";");
        super.setQuery(st.toString());
        super.openConnection();
        id = this.getIntFromQuery();
        super.closeConnection();
        return id;
    }

    @Override
    public int insertar(String tabla, String[] columnas, String[] values) {
        StringBuilder st = new StringBuilder("");
        int res = -1;
        st.append("INSERT INTO ");
        st.append(tabla);
        st.append("(");
        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            if (i != columnas.length - 1) {
                st.append(", ");
            }
        }
        st.append(") ");
        st.append("VALUES(");
        for (int i = 0; i < values.length; i++) {
            st.append("'");
            st.append(values[i]);
            st.append("'");
            if (i != values.length - 1) {
                st.append(", ");
            }
        }
        st.append(");");
        System.out.println(""+st.toString()+"\n");
        super.setQuery(st.toString());
        super.openConnection();
        res = super.insertQueryGetId();
        //Nunca cerrabamos la conexión
        super.closeConnection();
        return res;
    }

    public int insertarSinAbrirCerrarConexion(String tabla, String[] columnas, String[] values) {
        StringBuilder st = new StringBuilder("");
        int res = -1;
        st.append("INSERT INTO ");
        st.append(tabla);
        st.append("(");
        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            if (i != columnas.length - 1) {
                st.append(", ");
            }
        }
        st.append(") ");
        st.append("VALUES(");
        for (int i = 0; i < values.length; i++) {
            st.append("'");
            st.append(values[i]);
            st.append("'");
            if (i != values.length - 1) {
                st.append(", ");
            }
        }
        st.append(");");
        super.setQuery(st.toString());

        res = super.insertQueryGetId();

        return res;
    }

    @Override
    public void actualizar(String tabla, String[] columnas, String[] values, String clause) {
        StringBuilder st = new StringBuilder("");
        st.append(" UPDATE ");
        st.append(tabla);
        st.append(" SET ");
        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            st.append(" = ");
            st.append(values[i]);
            if (i != values.length - 1) {
                st.append(", ");
            }
        }

        st.append(" WHERE ");
        st.append(clause);
        super.setQuery(st.toString());
        super.openConnection();
        super.executeSingleQuery();
        super.closeConnection();
    }
    public void actualizarSinAbrirCerrarConexion(String tabla, String[] columnas, String[] values, String clause) {
        StringBuilder st = new StringBuilder("");
        st.append(" UPDATE ");
        st.append(tabla);
        st.append(" SET ");
        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            st.append(" = ");
            st.append(values[i]);
            if (i != values.length - 1) {
                st.append(", ");
            }
        }

        st.append(" WHERE ");
        st.append(clause);
        super.setQuery(st.toString());
        super.executeSingleQuery();
    }

    @Override
    public ResultSet seleccionSimple(String tabla, String[] columnas, String clause) {
        ResultSet rS = null;

        StringBuilder st = new StringBuilder("");
        st.append("SELECT ");

        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            if (i != columnas.length - 1) {
                st.append(", ");
            }
        }

        st.append(" FROM ");
        st.append(tabla);

        if (!"".equals(clause)) {
            st.append(" WHERE ");
            st.append(clause);
        }

        super.setQuery(st.toString());
        //System.out.println("El statement usado es: "+ st.toString());
        super.openConnection();

        rS = super.getResultsFromQuery();
        //super.closeConnection();
        return rS;
    }

    public ResultSet seleccionSimpleLimitOne(String tabla, String[] columnas, String clause) {
        ResultSet rS = null;

        StringBuilder st = new StringBuilder("");
        st.append("SELECT ");

        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            if (i != columnas.length - 1) {
                st.append(", ");
            }
        }

        st.append(" FROM ");
        st.append(tabla);

        if (!"".equals(clause)) {
            st.append(" WHERE ");
            st.append(clause);
        }
        st.append(" LIMIT 1");
        super.setQuery(st.toString());
        //System.out.println("El statement usado es: "+ st.toString());
        super.openConnection();
        rS = super.getResultsFromQuery();
        //super.closeConnection();
        return rS;
    }
      public ResultSet seleccionSimpleLimitOneSinAbrirCerrarConexion(String tabla, String[] columnas, String clause) {
        ResultSet rS = null;

        StringBuilder st = new StringBuilder("");
        st.append("SELECT ");

        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            if (i != columnas.length - 1) {
                st.append(", ");
            }
        }

        st.append(" FROM ");
        st.append(tabla);

        if (!"".equals(clause)) {
            st.append(" WHERE ");
            st.append(clause);
        }
        st.append(" LIMIT 1");
        super.setQuery(st.toString());
        //System.out.println("El statement usado es: "+ st.toString());
        
        rS = super.getResultsFromQuery();
        //super.closeConnection();
        return rS;
    }

    @Override
    public ResultSet seleccionCompuesta(String tabla, String[] columnas, String[] joins, String clause) {
        ResultSet rS = null;

        StringBuilder st = new StringBuilder("");
        st.append("SELECT ");

        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            if (i != columnas.length - 1) {
                st.append(", ");
            }
        }

        st.append(" FROM ");
        st.append(tabla);

        for (int i = 0; i < joins.length; i++) {
            st.append(joins[i]);
        }

        if (!"".equals(clause)) {
            st.append(" WHERE ");
            st.append(clause);
            st.append(" ");
        }
        
        super.setQuery(st.toString());

        super.openConnection();
        rS = super.getResultsFromQuery();
        //super.closeConnection();
        return rS;
    }

    public ResultSet seleccionSimpleOrdenado(String tabla, String[] columnas, String clause, String order) {
        ResultSet rS = null;

        StringBuilder st = new StringBuilder("");
        st.append("SELECT ");

        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            if (i != columnas.length - 1) {
                st.append(", ");
            }
        }

        st.append(" FROM ");
        st.append(tabla);

        if (!"".equals(clause)) {
            st.append(" WHERE ");
            st.append(clause);
        }
        if (!"".equals(order)) {
            st.append(" ORDER BY ");
            st.append(order);
            st.append(" ");
        }

        super.setQuery(st.toString());
        //System.out.println("El statement usado es: "+ st.toString());
        super.openConnection();
        rS = super.getResultsFromQuery();
        //super.closeConnection();
        return rS;
    }

    public ResultSet seleccionCompuestaOrdenado(String tabla, String[] columnas, String[] joins, String clause, String order) {
        ResultSet rS = null;

        StringBuilder st = new StringBuilder("");
        st.append("SELECT ");

        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            if (i != columnas.length - 1) {
                st.append(", ");
            }
        }

        st.append(" FROM ");
        st.append(tabla);

        for (int i = 0; i < joins.length; i++) {
            st.append(joins[i]);
        }

        if (!"".equals(clause)) {
            st.append(" WHERE ");
            st.append(clause);
            st.append(" ");
        }
        if (!"".equals(order)) {
            st.append(" ORDER BY ");
            st.append(order);
            st.append(" ");
        }

        super.setQuery(st.toString());
        super.openConnection();
        rS = super.getResultsFromQuery();
        //super.closeConnection();
        return rS;
    }

    public void eliminar(String tabla, String clause) {
        StringBuilder st = new StringBuilder("");

        st.append("DELETE FROM ");
        st.append(tabla);

        if (!"".equals(clause)) {
            st.append(" WHERE ");
            st.append(clause);
            st.append(" ");
        }

        super.setQuery(st.toString());
        super.openConnection();
        super.executeSingleQuery();
        super.closeConnection();

    }

    public void eliminarSinAbrirCerrarConexion(String tabla, String clause) {
        StringBuilder st = new StringBuilder("");

        st.append("DELETE FROM ");
        st.append(tabla);

        if (!"".equals(clause)) {
            st.append(" WHERE ");
            st.append(clause);
            st.append(" ");
        }

        super.setQuery(st.toString());

        super.executeSingleQuery();

    }

    public void modificarSinAbrirCerrarConexion(String tabla, String[] columnas, String[] values, String clause) {
        StringBuilder st = new StringBuilder("");
        int res = -1;
        st.append("UPDATE ");
        st.append(tabla);
        st.append(" SET ");
        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            st.append(" = '");
            st.append(values[i]);
            st.append("' ");
            if (i != columnas.length - 1) {
                st.append(", ");
            }
        }

        st.append(" WHERE ");
        st.append(clause);
        st.append(";");

        super.setQuery(st.toString());
        super.executeSingleQuery();
    }

    public void modificar(String tabla, String[] columnas, String[] values, String clause) {
        StringBuilder st = new StringBuilder("");
        int res = -1;
        st.append("UPDATE ");
        st.append(tabla);
        st.append(" SET ");
        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            st.append(" = '");
            st.append(values[i]);
            st.append("' ");
            if (i != columnas.length - 1) {
                st.append(", ");
            }
        }

        st.append(" WHERE ");
        st.append(clause);
        st.append(";");

        super.setQuery(st.toString());
        super.executeSingleQuery();
        super.closeConnection();
    }
    public void modificarNullColumna(String tabla, String columna, String clause) {
        StringBuilder st = new StringBuilder("");
        int res = -1;
        st.append("UPDATE ");
        st.append(tabla);
        st.append(" SET ");
            st.append(columna);
            st.append(" = NULL");
           
        

        st.append(" WHERE ");
        st.append(clause);
        st.append(";");

        super.setQuery(st.toString());
        super.openConnection();
        super.executeSingleQuery();
        super.closeConnection();
    }

    public ResultSet max(String tabla, String[] columnas, String clause) {
        ResultSet rS = null;

        StringBuilder st = new StringBuilder("");
        st.append("SELECT MAX(");

        for (int i = 0; i < columnas.length; i++) {
            st.append(columnas[i]);
            if (i != columnas.length - 1) {
                st.append(", ");
            }
        }

        st.append(") FROM ");
        st.append(tabla);

        if (!"".equals(clause)) {
            st.append(" WHERE ");
            st.append(clause);
        }

        super.setQuery(st.toString());
        //System.out.println("El statement usado es: "+ st.toString());
        super.openConnection();

        rS = super.getResultsFromQuery();
        //super.closeConnection();
        return rS;
    }

}
