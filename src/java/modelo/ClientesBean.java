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

/**
 *
 * @author sebas
 */
@Named(value = "clientes")
@RequestScoped
public class ClientesBean {

    private Integer idcliente = 0;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String tel;
    private Integer edad;
    private Character genero;
    private String email;
    private String password;
    private Clientes cliente;
    private ClientesFacade cfacade = new ClientesFacade();
    private boolean band;

    public ClientesBean() {
    }

    /**
     * @return the idcliente
     */
    public Integer getIdcliente() {
        return idcliente;
    }

    /**
     * @param idcliente the idcliente to set
     */
    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
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
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the edad
     */
    public Integer getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    /**
     * @return the genero
     */
    public Character getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(Character genero) {
        this.genero = genero;
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

    public String insertar() throws Exception {

        cliente = new Clientes();

        cliente.setIdcliente(this.idcliente += 1);
        cliente.setNombre(nombre);
        cliente.setApellidoPaterno(apellido_paterno);
        cliente.setApellidoMaterno(apellido_materno);
        cliente.setTel(tel);
        cliente.setEdad(edad);
        cliente.setGenero(genero);
        cliente.setEmail(email);
        cliente.setPassword(password);

        cfacade.registrar(cliente);

        return "index";

    }
}
