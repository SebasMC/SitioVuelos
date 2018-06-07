/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author Jonh
 */
public class Reservacion implements Serializable{
    
    private Integer idRESERVACION ;

    private String origen;
    private String destino;
    private String horario;
    private String fecha;    
    private int precio;

    public Integer getIdRESERVACION() {
        return idRESERVACION;
    }

    public void setIdRESERVACION(Integer idRESERVACION) {
        this.idRESERVACION = idRESERVACION;
    }



 
    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
   
    
    
    
    
}
