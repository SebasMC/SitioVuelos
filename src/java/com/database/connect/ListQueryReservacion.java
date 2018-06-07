/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database.connect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import modelo.Reservacion;

/**
 *
 * @author Jonh
 */
public class ListQueryReservacion extends DBConnect implements Serializable {

    public List<Reservacion> listReservacion() {
        List<Reservacion> list = new ArrayList<Reservacion>();
        try {
            ps = connect().prepareStatement(" select reservaciones.IDRESERVACION,  vuelos.FECHA, vuelos.PRECIO, vuelos.HORARIO, origen.AEROPUERTO, destino.AEROPUERTO as Destino FROM vuelos INNER JOIN  reservaciones ON  reservaciones.VUELOS_IDVUELO = vuelos.IDVUELO INNER JOIN destino ON destino.IDDESTINO = vuelos.DESTINO_IDDESTINO INNER JOIN origen on origen.IDORIGEN = vuelos.ORIGEN_IDORIGEN");
            Reservacion r;
            rs = ps.executeQuery();
            while (rs.next()) {
                r = new Reservacion();
              r.setIdRESERVACION(rs.getInt("IDRESERVACION"));
              r.setFecha(rs.getString("FECHA"));
               r.setPrecio(rs.getInt("PRECIO"));
                r.setHorario(rs.getString("HORARIO"));
                r.setOrigen(rs.getString("AEROPUERTO"));
                r.setDestino(rs.getString("Destino"));
               
               

                list.add(r);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }
}
