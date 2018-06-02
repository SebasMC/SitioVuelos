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
@Table(name = "compania")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compania.findAll", query = "SELECT c FROM Compania c")
    , @NamedQuery(name = "Compania.findByIdcompania", query = "SELECT c FROM Compania c WHERE c.idcompania = :idcompania")
    , @NamedQuery(name = "Compania.findByNombre", query = "SELECT c FROM Compania c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Compania.findByColonia", query = "SELECT c FROM Compania c WHERE c.colonia = :colonia")
    , @NamedQuery(name = "Compania.findByCalle", query = "SELECT c FROM Compania c WHERE c.calle = :calle")
    , @NamedQuery(name = "Compania.findByNumInt", query = "SELECT c FROM Compania c WHERE c.numInt = :numInt")
    , @NamedQuery(name = "Compania.findByNumExt", query = "SELECT c FROM Compania c WHERE c.numExt = :numExt")
    , @NamedQuery(name = "Compania.findByCp", query = "SELECT c FROM Compania c WHERE c.cp = :cp")
    , @NamedQuery(name = "Compania.findByCiudad", query = "SELECT c FROM Compania c WHERE c.ciudad = :ciudad")})
public class Compania implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCOMPANIA")
    private Integer idcompania;
    @Size(max = 45)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 45)
    @Column(name = "COLONIA")
    private String colonia;
    @Size(max = 45)
    @Column(name = "CALLE")
    private String calle;
    @Column(name = "NUM_INT")
    private Integer numInt;
    @Column(name = "NUM_EXT")
    private Integer numExt;
    @Column(name = "CP")
    private Integer cp;
    @Size(max = 45)
    @Column(name = "CIUDAD")
    private String ciudad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companiaIdcompania")
    private Collection<Vuelos> vuelosCollection;

    public Compania() {
    }

    public Compania(Integer idcompania) {
        this.idcompania = idcompania;
    }

    public Integer getIdcompania() {
        return idcompania;
    }

    public void setIdcompania(Integer idcompania) {
        this.idcompania = idcompania;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumInt() {
        return numInt;
    }

    public void setNumInt(Integer numInt) {
        this.numInt = numInt;
    }

    public Integer getNumExt() {
        return numExt;
    }

    public void setNumExt(Integer numExt) {
        this.numExt = numExt;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
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
        hash += (idcompania != null ? idcompania.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compania)) {
            return false;
        }
        Compania other = (Compania) object;
        if ((this.idcompania == null && other.idcompania != null) || (this.idcompania != null && !this.idcompania.equals(other.idcompania))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Compania[ idcompania=" + idcompania + " ]";
    }
    
}
