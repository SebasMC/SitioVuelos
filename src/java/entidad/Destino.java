/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "destino")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Destino.findAll", query = "SELECT d FROM Destino d")
    , @NamedQuery(name = "Destino.findByIddestino", query = "SELECT d FROM Destino d WHERE d.iddestino = :iddestino")
    , @NamedQuery(name = "Destino.findByAeropuerto", query = "SELECT d FROM Destino d WHERE d.aeropuerto = :aeropuerto")
    , @NamedQuery(name = "Destino.findByCiudad", query = "SELECT d FROM Destino d WHERE d.ciudad = :ciudad")})
public class Destino implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDDESTINO")
    private Integer iddestino;
    @Size(max = 45)
    @Column(name = "AEROPUERTO")
    private String aeropuerto;
    @Size(max = 45)
    @Column(name = "CIUDAD")
    private String ciudad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destinoIddestino")
    private Collection<Vuelos> vuelosCollection;

    public Destino() {
    }

    public Destino(Integer iddestino) {
        this.iddestino = iddestino;
    }

    public Integer getIddestino() {
        return iddestino;
    }

    public void setIddestino(Integer iddestino) {
        this.iddestino = iddestino;
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
        hash += (iddestino != null ? iddestino.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Destino)) {
            return false;
        }
        Destino other = (Destino) object;
        if ((this.iddestino == null && other.iddestino != null) || (this.iddestino != null && !this.iddestino.equals(other.iddestino))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Destino[ iddestino=" + iddestino + " ]";
    }
    
}
