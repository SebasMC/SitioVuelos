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
            ps = connect().prepareStatement("select vuelos.IDVUELO, vuelos.FECHA, vuelos.PRECIO, vuelos.HORARIO, destino.CIUDAD, destino.AEROPUERTO, compania.NOMBRE FROM vuelos INNER JOIN destino ON vuelos.DESTINO_IDDESTINO = destino.IDDESTINO inner join compania on compania.IDCOMPANIA=vuelos.COMPANIA_IDCOMPANIA");
            vuelos v;
            rs = ps.executeQuery();
            while (rs.next()) {
                v = new vuelos();
                v.setIdvuelo(rs.getInt("IDVUELO"));
                v.setFecha(rs.getString("FECHA"));
                v.setPrecio(rs.getInt("PRECIO"));
               v.setHorario(rs.getString("HORARIO"));
                
                v.setCiudad(rs.getString("CIUDAD"));
                v.setAerolinea(rs.getString("AEROPUERTO"));
                v.setNombre_compania(rs.getString("NOMBRE"));
                list.add(v);
            }
            return list;
        } catch (Exception e) {
            return null;
        }

    }

}
