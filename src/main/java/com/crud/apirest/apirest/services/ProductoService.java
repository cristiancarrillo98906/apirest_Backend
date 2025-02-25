package com.crud.apirest.apirest.services;

import com.crud.apirest.apirest.models.Producto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService implements IProductoService{
    private static final String FILE_PATH = "productos.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Obtener todos los productos
    @Override
    public ArrayList<Producto> obtenerTodosProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            File archivo = new File(getClass().getClassLoader().getResource(FILE_PATH).toURI());
            productos = objectMapper.readValue(archivo, new
                    TypeReference<ArrayList<Producto>>(){});
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    @Override
    public Producto obtenerProductoPorId(Long id) {
        ArrayList<Producto> productos = this.obtenerTodosProductos();
        for(Producto p:productos){
            if (p.getId() == id){
                return p;
            }
        }
        return new Producto();
    }

    @Override
    public Producto crearProducto(Producto p) {
     ArrayList<Producto> productos = this.obtenerTodosProductos();
     //Crear el ID
     Long id = 1L; // int id = 1;
     if (!productos.isEmpty()){
         id =productos.get(productos.size() -1).getId() + 1;
     }
     p.setId(id);
     //EN el Array de Productos (añadir el producto)
        productos.add(p);
     //Guardar el JSON (mapear el arrayList en un JSON)
        try {
            File archivo = new File(getClass().getClassLoader().getResource(FILE_PATH).toURI());
            objectMapper.writeValue(archivo,productos);
        } catch (IOException | URISyntaxException e){
            throw new RuntimeException(e);
        }
        return p;
    }

    @Override
    public Producto actualizarProducto(Long id, Producto p) {
        //obtener el array
        ArrayList<Producto> productos = obtenerTodosProductos();
        Producto productoEncontrado = null;

        //BUscarlo en el array
        for(Producto prod : productos){
            if (prod.getId() == id){
                //Actualiza el Array con el producto modificado
                prod.setNombre(p.getNombre());
                prod.setPrecio(p.getPrecio());
                productoEncontrado = prod;
                break;
            }
        }
        if (productoEncontrado != null && productoEncontrado.getId() == id){
            //Guardar en el JSON
            try {
                File archivo = new File(getClass().getClassLoader().getResource(FILE_PATH).toURI());
                objectMapper.writeValue(archivo,productos);
            } catch (IOException | URISyntaxException e){
                throw new RuntimeException(e);
            }
        }
        return productoEncontrado;
    }

    @Override
    public Producto borrarProducto(Long id) {
        //1.- Volvar JSON a ArrayList - obtener el array
        ArrayList<Producto> productos = obtenerTodosProductos();
        Producto productoEliminado = null;

        for (Producto producto: productos){ //2.- Encontrar ID en Array
            if (producto.getId() == id){
                //3.- Borrar item del Array
                productoEliminado = producto; // se asigna el producto encontrado
                productos.remove(producto);
                break;
            }
        }
        if (productoEliminado !=null && productoEliminado.getId() == id){
            //4.- Guardar en el JSON
            try {
                File archivo = new File(getClass().getClassLoader().getResource(FILE_PATH).toURI());
                objectMapper.writeValue(archivo,productos);
            } catch (IOException | URISyntaxException e){
                throw new RuntimeException(e);
            }
        }
        return productoEliminado; //5.- Return item borrado
    }

}
