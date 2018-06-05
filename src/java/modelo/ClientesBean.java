/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.ClientesFacade;
import entidad.Clientes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author sebas
 */
@Named(value = "clientes")
@RequestScoped
public class ClientesBean {

    private Integer idcliente = null;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String tel;
    private Integer edad = null;
    private Character gen;
    private String email;
    private String password;
    //private Clientes cliente;
    private ClientesFacade cfacade = new ClientesFacade();
    private boolean band;

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

    public String insertar() {
        Clientes cliente = new Clientes(idcliente, nombre, apellido_paterno, apellido_materno, tel, edad, gen, email, password);
        try {
            if (cfacade.buscarUsuario(email) != null) {
                cfacade.crearCl(cliente);
                return "index";
            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("", new FacesMessage("Usuario ya existente!!!"));
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(ClientesBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
