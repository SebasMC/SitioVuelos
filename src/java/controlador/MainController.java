/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.database.connect.ListQuery;
import com.sun.jndi.toolkit.dir.SearchFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.vuelos;

/**
 *
 * @author Jonh
 */
@ManagedBean (name="main")
@SessionScoped
public class MainController implements Serializable{

    ListQuery query = new ListQuery();
    private List<vuelos> list = new ArrayList<vuelos>();

    public List<vuelos> getList() {
        list = query.listVuelos();
        return list;
    }

    public void setList(List<vuelos> list) {
        this.list = list;
    }
    
    
}
