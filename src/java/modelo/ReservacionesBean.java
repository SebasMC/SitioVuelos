/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.ReservacionesFacade;
import entidad.Clientes;
import entidad.Reservaciones;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Jonh
 */
@Named(value = "reservacion")
@RequestScoped
public class ReservacionesBean {

    private Integer idreservacion;
    private Integer clientes_idclientes;
    private Integer vuelos_idvuelos;
    private FacesContext fc = FacesContext.getCurrentInstance();
    private ExternalContext ec = fc.getExternalContext();

    private ReservacionesFacade rfacade = new ReservacionesFacade();

    public Integer getIdreservacion() {
        return idreservacion;
    }

    public void setIdreservacion(Integer idreservacion) {
        this.idreservacion = idreservacion;
    }

    public Integer getClientes_idclientes() {
        return clientes_idclientes;
    }

    public void setClientes_idclientes(Integer clientes_idclientes) {
        this.clientes_idclientes = clientes_idclientes;
    }

    public Integer getVuelos_idvuelos() {
        return vuelos_idvuelos;
    }

    public void setVuelos_idvuelos(Integer vuelos_idvuelos) {
        this.vuelos_idvuelos = vuelos_idvuelos;
    }

    public void insertar() {
        Reservaciones reservacion = new Reservaciones(idreservacion, clientes_idclientes, vuelos_idvuelos);
        try {
            rfacade.crearReservacion(reservacion);
             ec.redirect(ec.getRequestContextPath() + "/faces/index_1.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(ClientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
