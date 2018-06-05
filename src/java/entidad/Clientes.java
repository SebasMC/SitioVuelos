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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sebas
 */
@Entity
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")
    , @NamedQuery(name = "Clientes.findByIdcliente", query = "SELECT c FROM Clientes c WHERE c.idcliente = :idcliente")
    , @NamedQuery(name = "Clientes.findByNombre", query = "SELECT c FROM Clientes c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Clientes.findByApellidoPaterno", query = "SELECT c FROM Clientes c WHERE c.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "Clientes.findByApellidoMaterno", query = "SELECT c FROM Clientes c WHERE c.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "Clientes.findByTel", query = "SELECT c FROM Clientes c WHERE c.tel = :tel")
    , @NamedQuery(name = "Clientes.findByEdad", query = "SELECT c FROM Clientes c WHERE c.edad = :edad")
    , @NamedQuery(name = "Clientes.findByGenero", query = "SELECT c FROM Clientes c WHERE c.genero = :genero")
    , @NamedQuery(name = "Clientes.findByEmail", query = "SELECT c FROM Clientes c WHERE c.email = :email")
    , @NamedQuery(name = "Clientes.findByPassword", query = "SELECT c FROM Clientes c WHERE c.password = :password")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "IDCLIENTE")
    private Integer idcliente;
    @Size(max = 45)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 45)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Size(max = 45)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Size(max = 10)
    @Column(name = "TEL")
    private String tel;
    @Column(name = "EDAD")
    private Integer edad;
    @Column(name = "GENERO")
    private Character genero;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 8)
    @Column(name = "PASSWORD")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientes")
    private Collection<Reservaciones> reservacionesCollection;

    public Clientes() {
    }

    public Clientes(String nombre, String apellidoPaterno, String apellidoMaterno, String tel, Integer edad, Character genero, String email, String password, Collection<Reservaciones> reservacionesCollection) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.tel = tel;
        this.edad = edad;
        this.genero = genero;
        this.email = email;
        this.password = password;
        this.reservacionesCollection = reservacionesCollection;
    }
    
    

    public Clientes(Integer idcliente, String nombre, String apellidoPaterno, String apellidoMaterno, String tel, Integer edad, Character genero, String email, String password) {
        this.idcliente = idcliente;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.tel = tel;
        this.edad = edad;
        this.genero = genero;
        this.email = email;
        this.password = password;
    }

    public Clientes(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        hash += (idcliente != null ? idcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Clientes[ idcliente=" + idcliente + " ]";
    }
    
}
