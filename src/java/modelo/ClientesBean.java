/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.ClientesFacade;
import entidad.Clientes;
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

    private Integer idcliente=2;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private int edad;
    private String ciudad;
    private String email;
    private String password;
    private Clientes cliente;
    private ClientesFacade cfacade = new ClientesFacade();

    public ClientesBean() {
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido_paterno
     */
    public String getApellido_paterno() {
        return apellido_paterno;
    }

    /**
     * @param apellido_paterno the apellido_paterno to set
     */
    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    /**
     * @return the apellido_materno
     */
    public String getApellido_materno() {
        return apellido_materno;
    }

    /**
     * @param apellido_materno the apellido_materno to set
     */
    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    /**
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the cfacade
     */
    public ClientesFacade getCfacade() {
        return cfacade;
    }

    /**
     * @param cfacade the cfacade to set
     */
    public void setCfacade(ClientesFacade cfacade) {
        this.cfacade = cfacade;
    }

    public String busca(String email, String password) {
        return null;
    }

    public void insertar() throws Exception {
        boolean band1;
        cliente = new Clientes();

        cliente.setIdcliente(idcliente);
        cliente.setNombre(nombre);
        cliente.setApellidoPaterno(apellido_paterno);
        cliente.setApellidoMaterno(apellido_materno);
        cliente.setEdad(edad);
        cliente.setCiudad(ciudad);
        cliente.setEmail(email);
        cliente.setPassword(password);

        cfacade.registrar(cliente);


    }
}
