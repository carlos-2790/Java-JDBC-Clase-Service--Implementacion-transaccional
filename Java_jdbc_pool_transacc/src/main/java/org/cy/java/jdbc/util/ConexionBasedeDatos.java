package org.cy.java.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBasedeDatos {

    private static String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "Wabi16Cy";
    private static BasicDataSource pool;


    public static BasicDataSource getInstance() throws SQLException { // singleton Datasource

        if (pool == null){
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(username);
            pool.setPassword(password);
            pool.setInitialSize(3); // comienza el pool con 3 conexiones habilitadas -- el valor por defecto es 0 y se incrementa hasta 8
            pool.setMinIdle(3); // minimo de conexiones inactivas- el valor por defecto es 0
            pool.setMaxIdle(8);// maximo de conexiones inactivas
            pool.setMaxTotal(8);// entre activas e inactivas conexiones
        }
           return pool;
    }
    // implementamos para conectarnos, obtener una nueva conexion de la base de datos, este devuelve un objeto conection

    public static Connection getConnection() throws SQLException{
        return getInstance().getConnection();  // getInstance invoca el método 'BasicDataSource getInstance()' y desde el getConnection obtenemos una conexión a la base de datos a través del pool

    }

}
