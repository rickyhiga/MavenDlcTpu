package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ricky
 */
public abstract class AbstractDB {

    //Deberian declararse en cada DBAccess
    protected static final StringBuilder dbType = new StringBuilder("com.mysql.jdbc.Driver");
    protected StringBuilder dbPath = new StringBuilder("jdbc:mysql://localhost/viverosys");
    protected StringBuilder query = new StringBuilder("");
    protected ResultSet rows;
    private Connection con;
    private Statement stmt;
    private File fileConfig;

    protected void setQuery(String sql) {
        query.setLength(0);
        query.append(sql);
    }

    public void openConnection() {
        //agregar comprobacion por null
//        System.out.println("Abrir conexion");

        try {
            if (con == null || con.isClosed()) {

                Class.forName(dbType.toString());
                // System.out.println(dbPath.toString());
                String fijo = "jdbc:mysql://";

                // ACA LOS DATOS EN HARDCORE POR DEFAULT NO SE USA MAS ESTA EL LECTOR AHORA
                String host = "localhost/";
                String nombreDb = "buscadordlc";
                String path = fijo+host+nombreDb;
                String usuarioDb = "root";
                String passwordDb = "";
//                fileConfig = new File("config.ini");
//                Lector l = new Lector(fileConfig);
//                String host = l.getHost();
//                String nombreDb = l.getDb();
//                String usuarioDb = l.getUser();
//                String passwordDb = l.getPass();
//                String path = fijo + host + nombreDb;

                //JUST FOR DEBUGGING
//                System.out.println("EL HOST: "+host);
//                System.out.println("EL DB: "+nombreDb);
//                System.out.println("EL USER: "+usuarioDb);
//                System.out.println("EL PASS: "+ passwordDb);
//                System.out.println("EL PATH: "+path);
                con = DriverManager.getConnection(path, usuarioDb, passwordDb);
                //con.setAutoCommit(false);
                //System.out.println("DB abierta con éxito");

            } else {
//                System.out.println("Ya se encuentra abierta");
            }
        } catch (ClassNotFoundException | SQLException e) {
//            System.out.println("Error al abrir DB - " + e.toString());
            JOptionPane.showMessageDialog(new JFrame(), "ERROR DB " + e.toString());
            Logger.getLogger(AbstractDB.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void closeConnection() {
//        if (con.isClosed() == false) {
//        System.out.println("Cerrar conexion");
        try {
            //this.con.commit();
            // this.stmt.close();
            this.con.close();
            this.con = null;
        } catch (SQLException ex) {
//            System.out.println("Error en el cierre de la conexión db -" + ex.toString());
            Logger.getLogger(AbstractDB.class.getName()).log(Level.SEVERE, null, ex);
        }
//        }else{
//            System.out.println("Ya se encuentra cerrada la con");
//        }

    }

    protected void executeSingleQuery() {

        try {
            this.openConnection();
            this.stmt = this.con.createStatement();
            this.stmt.executeUpdate(query.toString());
//            System.out.println("SingleQuery ejecutada con éxito");
        } catch (SQLException ex) {

//            System.out.println("Error al ejecutar SingleQuery - " + ex.toString());
            Logger.getLogger(AbstractDB.class.getName()).log(Level.SEVERE, null, ex);

        }

    }


    protected int insertQueryGetId() {
        int res = -1;
        try {
            this.openConnection();
            this.stmt = this.con.createStatement();
            this.stmt.execute(query.toString(), Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = this.stmt.getGeneratedKeys();
            while (rs.next()) {
                res = rs.getInt(1);
            }

//            System.out.println("SingleQuery ejecutada con éxito");
        } catch (SQLException ex) {

//            System.out.println("Error al ejecutar SingleQuery - " + ex.toString());
            Logger.getLogger(AbstractDB.class.getName()).log(Level.SEVERE, null, ex);


        }
        return res;
    }

    protected ResultSet getResultsFromQuery() {
        ResultSet rows = null;
        try {
            //PARCHE
            //this.openConnection();

            stmt = this.con.createStatement();
            rows = stmt.executeQuery(query.toString());
            //rows.next();
            //System.out.println("Las rows son " + rows.getInt(1));
        } catch (SQLException ex) {

            Logger.getLogger(AbstractDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }

    protected void closeResultSet() throws SQLException {
        this.stmt.close();
    }

    public abstract int insertar(String tabla, String[] columnas, String[] values);

    public abstract void actualizar(String tabla, String[] columnas, String[] values, String clause);

    public abstract ResultSet seleccionSimple(String tabla, String[] columnas, String clause);

    public abstract ResultSet seleccionCompuesta(String tabla, String[] columnas, String[] joins, String clause);

    protected void setAutoCommit(boolean autoCom) {
        try {
            this.con.setAutoCommit(autoCom);
        } catch (SQLException ex) {
            Logger.getLogger(AbstractDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

}
