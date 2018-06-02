/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sebas
 */
@Entity
@Table(name = "vuelos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vuelos.findAll", query = "SELECT v FROM Vuelos v")
    , @NamedQuery(name = "Vuelos.findByIdvuelo", query = "SELECT v FROM Vuelos v WHERE v.idvuelo = :idvuelo")
    , @NamedQuery(name = "Vuelos.findByFecha", query = "SELECT v FROM Vuelos v WHERE v.fecha = :fecha")
    , @NamedQuery(name = "Vuelos.findByPrecio", query = "SELECT v FROM Vuelos v WHERE v.precio = :precio")
    , @NamedQuery(name = "Vuelos.findByHorario", query = "SELECT v FROM Vuelos v WHERE v.horario = :horario")})
public class Vuelos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDVUELO")
    private Integer idvuelo;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @Column(name = "HORARIO")
    private Integer horario;
    @JoinColumn(name = "ORIGEN_IDORIGEN", referencedColumnName = "IDORIGEN")
    @ManyToOne
    private Origen origenIdorigen;
    @JoinColumn(name = "DESTINO_IDDESTINO", referencedColumnName = "IDDESTINO")
    @ManyToOne(optional = false)
    private Destino destinoIddestino;
    @JoinColumn(name = "COMPANIA_IDCOMPANIA", referencedColumnName = "IDCOMPANIA")
    @ManyToOne(optional = false)
    private Compania companiaIdcompania;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vuelos")
    private Collection<Reservaciones> reservacionesCollection;

    public Vuelos() {
    }

    public Vuelos(Integer idvuelo) {
        this.idvuelo = idvuelo;
    }

    public Integer getIdvuelo() {
        return idvuelo;
    }

    public void setIdvuelo(Integer idvuelo) {
        this.idvuelo = idvuelo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getHorario() {
        return horario;
    }

    public void setHorario(Integer horario) {
        this.horario = horario;
    }

    public Origen getOrigenIdorigen() {
        return origenIdorigen;
    }

    public void setOrigenIdorigen(Origen origenIdorigen) {
        this.origenIdorigen = origenIdorigen;
    }

    public Destino getDestinoIddestino() {
        return destinoIddestino;
    }

    public void setDestinoIddestino(Destino destinoIddestino) {
        this.destinoIddestino = destinoIddestino;
    }

    public Compania getCompaniaIdcompania() {
        return companiaIdcompania;
    }

    public void setCompaniaIdcompania(Compania companiaIdcompania) {
        this.companiaIdcompania = companiaIdcompania;
    }

    @XmlTransient
    public Collection<Reservaciones> getReservacionesCollection() {
        return reservacionesCollection;
    }

    public void setReservacionesCollection(Collection<Reservaciones> reservacionesCollection) {
        this.reservacionesCollection = reservacionesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvuelo != null ? idvuelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vuelos)) {
            return false;
        }
        Vuelos other = (Vuelos) object;
        if ((this.idvuelo == null && other.idvuelo != null) || (this.idvuelo != null && !this.idvuelo.equals(other.idvuelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Vuelos[ idvuelo=" + idvuelo + " ]";
    }
    
}
