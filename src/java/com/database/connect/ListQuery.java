/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database.connect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import modelo.vuelos;

/**
 *
 * @author Jonh
 */
public class ListQuery extends DBConnect implements Serializable {

    public List<vuelos> listVuelos() {
        List<vuelos> list = new ArrayList<vuelos>();
        try {
            ps = connect().prepareStatement("select IDVUELO,FECHA,PRECIO,HORARIO FROM vuelos");
            vuelos v;
            rs = ps.executeQuery();
            while (rs.next()) {
                v = new vuelos();
                v.setIdvuelo(rs.getInt("IDVUELO"));
                v.setFecha(rs.getString("FECHA"));
                v.setPrecio(rs.getInt("PRECIO"));
                v.setHorario(rs.getInt("HORARIO"));
                list.add(v);
            }
            return list;
        } catch (Exception e) {
            return null;
        }

    }

}
