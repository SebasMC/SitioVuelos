/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.database.connect.ListQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Vuelos_disponibles;

/**
 *
 * @author Jonh
 */
@ManagedBean(name = "main")
@SessionScoped
public class execute_mostrar_vuelos implements Serializable {

//    private Vuelos_disponibles vuelos_disponibles = new Vuelos_disponibles();
//    ListQuery query = new ListQuery();
//   private List<Vuelos_disponibles> list = new ArrayList<Vuelos_disponibles>();
//
//
//    public List<Vuelos_disponibles> getList() {
//        return list;
//    }
//
//    public void setList(List<Vuelos_disponibles> list) {
//        this.list = list;
//    }
//    
    ListQuery query = new ListQuery();
    private List<Vuelos_disponibles> list = new ArrayList<Vuelos_disponibles>();

    public List<Vuelos_disponibles> getList() {
        list = query.listVuelos();
        return list;
    }

    public void setList(List<Vuelos_disponibles> list) {
        this.list = list;
    }

}
