/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.database.connect.ListQuery;
import com.database.connect.ListQueryReservacion;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Reservacion;


/**
 *
 * @author Jonh
 */

@ManagedBean (name="main_RES")
@SessionScoped
public class MainController_reser {
      ListQueryReservacion query = new ListQueryReservacion();
    private List<Reservacion> list = new ArrayList<Reservacion>();

    public List<Reservacion> getList() {
        list = query.listReservacion();
        return list;
    }

    public void setList(List<Reservacion> list) {
        this.list = list;
    }

}
