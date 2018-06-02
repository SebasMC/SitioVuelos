/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sebas
 */
@Embeddable
public class ReservacionesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDRESERVACION")
    private int idreservacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLIENTES_IDCLIENTE")
    private int clientesIdcliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VUELOS_IDVUELO")
    private int vuelosIdvuelo;

    public ReservacionesPK() {
    }

    public ReservacionesPK(int idreservacion, int clientesIdcliente, int vuelosIdvuelo) {
        this.idreservacion = idreservacion;
        this.clientesIdcliente = clientesIdcliente;
        this.vuelosIdvuelo = vuelosIdvuelo;
    }

    public int getIdreservacion() {
        return idreservacion;
    }

    public void setIdreservacion(int idreservacion) {
        this.idreservacion = idreservacion;
    }

    public int getClientesIdcliente() {
        return clientesIdcliente;
    }

    public void setClientesIdcliente(int clientesIdcliente) {
        this.clientesIdcliente = clientesIdcliente;
    }

    public int getVuelosIdvuelo() {
        return vuelosIdvuelo;
    }

    public void setVuelosIdvuelo(int vuelosIdvuelo) {
        this.vuelosIdvuelo = vuelosIdvuelo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idreservacion;
        hash += (int) clientesIdcliente;
        hash += (int) vuelosIdvuelo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservacionesPK)) {
            return false;
        }
        ReservacionesPK other = (ReservacionesPK) object;
        if (this.idreservacion != other.idreservacion) {
            return false;
        }
        if (this.clientesIdcliente != other.clientesIdcliente) {
            return false;
        }
        if (this.vuelosIdvuelo != other.vuelosIdvuelo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.ReservacionesPK[ idreservacion=" + idreservacion + ", clientesIdcliente=" + clientesIdcliente + ", vuelosIdvuelo=" + vuelosIdvuelo + " ]";
    }
    
}
