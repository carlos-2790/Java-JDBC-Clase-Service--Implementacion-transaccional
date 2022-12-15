package org.cy.java.jdbc.repositorio;

import org.cy.java.jdbc.modelo.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositorioImpl  implements Repositorio<Categoria>{
    // crear atributo a la conexion a la base de datos
    private Connection conn;

    // constructor

    public CategoriaRepositorioImpl() {
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public CategoriaRepositorioImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Categoria> listar() throws SQLException {

        // se crea un array list para guardar la categoria
        List<Categoria>categorias = new ArrayList<>();
       // es un statement por q no tenemos ningun parametro, es una consulta select a todas las categorias
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT*FROM categorias")){
            while (rs.next()){
                categorias.add(crearCategoria(rs));
            }

        }


        return null;
    }

    @Override
    public Categoria porId(Long id) throws SQLException {

        Categoria categoria = new Categoria();
        // 'PreparedStatement' por que es una consulta con el id( un parametro)
        try(PreparedStatement stmt = conn.prepareStatement("SELECT*FROM categorias as c WHERE c.id=?")){
           // pasamos el parametro
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()){ // ->nos posicionamos en el primer registro, si encuentra la categoria por el Id

                    categoria = crearCategoria(rs);
                }
            }
        }
        return categoria;
    }

    @Override
    public Categoria guardar(Categoria categoria) throws SQLException {
      String sql = null;
       if(categoria.getId() != null && categoria.getId() > 0 ){
           sql = "UPDATE categorias SET nombre=? WHERE id=?";
       }else {
           sql = "INSERT INTO categorias(nombre) VALUES(?)";
       }
       try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
           stmt.setString(1, categoria.getNombre());
           if (categoria.getId() != null && categoria.getId()> 0 ){
               stmt.setLong(2, categoria.getId());
           }
           stmt.executeUpdate();

           if (categoria.getId() == null){
               try (ResultSet rs = stmt.getGeneratedKeys()){
                   if (rs.next()){
                       categoria.setId(rs.getLong(1));// va a traer 1 id, puede traer varias columnas
                   }

               }
           }
       }
        return categoria; // el objeto categoria trae el id , que desp se pasa a producto y en el insert del producto ya vamos a tener id de categoria ( q es la foreing key)
    }

    @Override
    public void eliminar(Long id) throws SQLException {

        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM categorias WHERE id=?")){
             stmt.setLong(1,id);
             stmt.executeUpdate();
        }
    }

    private static Categoria crearCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setId(rs.getLong("id"));
        c.setNombre(rs.getString("nombre"));
        return c;
    }
}
