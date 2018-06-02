/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sebas
 */
@Entity
@Table(name = "reservaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservaciones.findAll", query = "SELECT r FROM Reservaciones r")
    , @NamedQuery(name = "Reservaciones.findByIdreservacion", query = "SELECT r FROM Reservaciones r WHERE r.reservacionesPK.idreservacion = :idreservacion")
    , @NamedQuery(name = "Reservaciones.findByClientesIdcliente", query = "SELECT r FROM Reservaciones r WHERE r.reservacionesPK.clientesIdcliente = :clientesIdcliente")
    , @NamedQuery(name = "Reservaciones.findByVuelosIdvuelo", query = "SELECT r FROM Reservaciones r WHERE r.reservacionesPK.vuelosIdvuelo = :vuelosIdvuelo")
    , @NamedQuery(name = "Reservaciones.findByNoAsiento", query = "SELECT r FROM Reservaciones r WHERE r.noAsiento = :noAsiento")})
public class Reservaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReservacionesPK reservacionesPK;
    @Column(name = "NO_ASIENTO")
    private Integer noAsiento;
    @JoinColumn(name = "CLIENTES_IDCLIENTE", referencedColumnName = "IDCLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Clientes clientes;
    @JoinColumn(name = "VUELOS_IDVUELO", referencedColumnName = "IDVUELO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Vuelos vuelos;

    public Reservaciones() {
    }

    public Reservaciones(ReservacionesPK reservacionesPK) {
        this.reservacionesPK = reservacionesPK;
    }

    public Reservaciones(int idreservacion, int clientesIdcliente, int vuelosIdvuelo) {
        this.reservacionesPK = new ReservacionesPK(idreservacion, clientesIdcliente, vuelosIdvuelo);
    }

    public ReservacionesPK getReservacionesPK() {
        return reservacionesPK;
    }

    public void setReservacionesPK(ReservacionesPK reservacionesPK) {
        this.reservacionesPK = reservacionesPK;
    }

    public Integer getNoAsiento() {
        return noAsiento;
    }

    public void setNoAsiento(Integer noAsiento) {
        this.noAsiento = noAsiento;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Vuelos getVuelos() {
        return vuelos;
    }

    public void setVuelos(Vuelos vuelos) {
        this.vuelos = vuelos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservacionesPK != null ? reservacionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservaciones)) {
            return false;
        }
        Reservaciones other = (Reservaciones) object;
        if ((this.reservacionesPK == null && other.reservacionesPK != null) || (this.reservacionesPK != null && !this.reservacionesPK.equals(other.reservacionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Reservaciones[ reservacionesPK=" + reservacionesPK + " ]";
    }
    
}
