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
public class Vuelos_disponibles implements Serializable{
    private  int IDVUELO;
    private String CIUDAD;
    private String FECHA;
    private int PRECIO;
    private int HORARIO;

    public int getIDVUELO() {
        return IDVUELO;
    }

    public void setIDVUELO(int IDVUELO) {
        this.IDVUELO = IDVUELO;
    }

    public String getCIUDAD() {
        return CIUDAD;
    }

    public void setCIUDAD(String CIUDAD) {
        this.CIUDAD = CIUDAD;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public int getPRECIO() {
        return PRECIO;
    }

    public void setPRECIO(int PRECIO) {
        this.PRECIO = PRECIO;
    }

    public int getHORARIO() {
        return HORARIO;
    }

    public void setHORARIO(int HORARIO) {
        this.HORARIO = HORARIO;
    }
    
}
