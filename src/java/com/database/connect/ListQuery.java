/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database.connect;

import modelo.Vuelos_disponibles;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonh
 */
public class ListQuery extends DBConnect implements Serializable {

    public List<Vuelos_disponibles> listVuelos() {
        List<Vuelos_disponibles> list = new ArrayList<>();
        try {
            ps = connect().prepareStatement("select v.IDVUELO, d.CIUDAD, v.FECHA, v.PRECIO, v.HORARIO\n"
                    + "from vuelos v , destino d\n"
                    + "where  v.DESTINO_IDDESTINO = d.IDDESTINO;");
            Vuelos_disponibles v;
            rs = ps.executeQuery();
            while (rs.next()) {
                v = new Vuelos_disponibles();
                v.setIDVUELO(rs.getInt("IDVUELO"));
                v.setCIUDAD(rs.getNString("CIUDAD"));
                v.setFECHA(rs.getNString("FECHA"));
                v.setHORARIO(rs.getInt("HORA"));
                v.setPRECIO(rs.getInt("PRECIO"));
                list.add(v);
                
            }
            return list;
        } catch (Exception e) {
        }
        return null;

    }
}
