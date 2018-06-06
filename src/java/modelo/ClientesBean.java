/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.ClientesFacade;
import entidad.Clientes;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author sebas
 */
@Named(value = "clientes")
@RequestScoped
public class ClientesBean {

    private Integer idcliente;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String tel;
    private Integer edad = null;
    private Character gen;
    private String email;
    private String password;
    private Clientes cliente;
    private ClientesFacade cfacade = new ClientesFacade();
    private boolean band = false;
    private FacesContext fc = FacesContext.getCurrentInstance();
    private ExternalContext ec = fc.getExternalContext();

    public ClientesBean() {
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

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Character getGen() {
        return gen;
    }

    public void setGen(Character gen) {
        this.gen = gen;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public boolean isBand() {
        return band;
    }

    public void setBand(boolean band) {
        this.band = band;
    }

    public void insertar() {
        Clientes cliente = new Clientes(null, nombre, apellido_paterno, apellido_materno, tel, edad, gen, email, password);
        try {
            cfacade.crearCl(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ClientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verificarSesion() {
        if (band != true) {
            try {
                ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ClientesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void iniciarSesion() {
        cliente = cfacade.buscarCliente(idcliente);

        if (cliente != null) {
            if (cliente.getPassword().equals(this.password)) {
                this.band = true;
                String parameter = "?num=" + idcliente + "&pass=" + band;
                {
                    try {
                        ec.redirect(ec.getRequestContextPath() + "/faces/vuelos.xhtml" + parameter);
                    } catch (IOException ex) {
                        Logger.getLogger(ClientesBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                fc.addMessage("", new FacesMessage("Contraseña incorrecta!!!"));
            }
        } else {
            fc.addMessage("", new FacesMessage("Usuario no registrado"));
        }
    }

    public void logout() throws IOException {
        this.band = false;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Te has desconectado", "Debes iniciar sesión nuevamente"));
        ec.getFlash().setKeepMessages(true);
        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        ec.redirect(ec.getRequestContextPath()+"/faces/index.xhtml");
    }
}
