package org.cy.java.jdbc;

import org.cy.java.jdbc.modelo.Categoria;
import org.cy.java.jdbc.modelo.Producto;

import org.cy.java.jdbc.servicio.CatalogoServicio;
import org.cy.java.jdbc.servicio.Servicio;

import java.sql.SQLException;
import java.util.Date;

public class EjemploJdbc {
    public static void main(String[] args) throws SQLException {


        Servicio servicio = new CatalogoServicio();
        System.out.println("==================== Listar ====================");
        servicio.listar().forEach(System.out::println);// lista la completa

        // creamos nueva categoria
        Categoria categoria = new Categoria();
        categoria.setNombre("Iluminación");


        System.out.println("==================== Insertar nuevo producto ====================");

        Producto producto = new Producto();
        producto.setNombre("Lámpara Led escritorio");
        producto.setPrecio(990);
        producto.setFechaRegistro(new Date());
        producto.setSku("abcdefh12");
        servicio.guardarProductoConCategoria(producto, categoria);


        System.out.println("Producto guardado con éxito: " + producto.getId());
        servicio.listar().forEach(System.out::println);// lista la completa


    }
}


