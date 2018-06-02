/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sebas
 */
@Entity
@Table(name = "origen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Origen.findAll", query = "SELECT o FROM Origen o")
    , @NamedQuery(name = "Origen.findByIdorigen", query = "SELECT o FROM Origen o WHERE o.idorigen = :idorigen")
    , @NamedQuery(name = "Origen.findByAeropuerto", query = "SELECT o FROM Origen o WHERE o.aeropuerto = :aeropuerto")
    , @NamedQuery(name = "Origen.findByCiudad", query = "SELECT o FROM Origen o WHERE o.ciudad = :ciudad")})
public class Origen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDORIGEN")
    private Integer idorigen;
    @Size(max = 45)
    @Column(name = "AEROPUERTO")
    private String aeropuerto;
    @Size(max = 45)
    @Column(name = "CIUDAD")
    private String ciudad;
    @OneToMany(mappedBy = "origenIdorigen")
    private Collection<Vuelos> vuelosCollection;

    public Origen() {
    }

    public Origen(Integer idorigen) {
        this.idorigen = idorigen;
    }

    public Integer getIdorigen() {
        return idorigen;
    }

    public void setIdorigen(Integer idorigen) {
        this.idorigen = idorigen;
    }

    public String getAeropuerto() {
        return aeropuerto;
    }

    public void setAeropuerto(String aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @XmlTransient
    public Collection<Vuelos> getVuelosCollection() {
        return vuelosCollection;
    }

    public void setVuelosCollection(Collection<Vuelos> vuelosCollection) {
        this.vuelosCollection = vuelosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idorigen != null ? idorigen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Origen)) {
            return false;
        }
        Origen other = (Origen) object;
        if ((this.idorigen == null && other.idorigen != null) || (this.idorigen != null && !this.idorigen.equals(other.idorigen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Origen[ idorigen=" + idorigen + " ]";
    }
    
}
