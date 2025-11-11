package edu.cdm.conexionbd.seccion6_3_1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author JJBH
 */
public class inserConProcAlma {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Cargar el driver de mysql => obsoleto
            // Class.forName("com.mysql.jdbc.Driver");

            // para que esto funcione, hay que ejecutar con anterioridad los scripts de
            // src/main/resources/seccion_6_3_1/MySQL

            // Cadena de conexión para conectar con MySQL en localhost,
            // seleccionar la base de datos llamada ‘test’
            // con usuario y contraseña del servidor de MySQL: root y admin
            String connectionUrl = "jdbc:mysql://localhost/test?" +
                    "user=root&password=abc123.";
            // Obtener la conexión
            Connection con = DriverManager.getConnection(connectionUrl);

            // El procedimiento almacenado tendrá tres parámetros
            CallableStatement prcProcedimientoAlmacenado = con.prepareCall("{ call insertaCliente(?, ?,?) }");

            // cargar parametros en el procedimiento almacenado
            prcProcedimientoAlmacenado.setInt("Cod_Cliente", 765);
            prcProcedimientoAlmacenado.setString("Nombre", "Antonio Pérez");
            prcProcedimientoAlmacenado.setString("Telefono", "950121314");

            // ejecutar el procedimiento
            prcProcedimientoAlmacenado.execute();

            mostrarClientes(con);

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.toString());
        }

    }

    private static void mostrarClientes(Connection conexion) {
        try (// Vemos los departamentos actuales:
             // creamos el objeto Statement
                Statement sentencia = conexion.createStatement();
                ResultSet resul = sentencia.executeQuery("SELECT * FROM clientes");) {

            // Recorremos el resultado para visualizar cada fila // Se hace un bucle
            // mientras haya registros y se van visualizando
            while (resul.next()) {
                System.out.printf("%d, %s, %s %n", resul.getInt(1), resul.getString(2), resul.getString(3));
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
