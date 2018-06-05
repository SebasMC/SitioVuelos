/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Clientes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author sebas
 */
@Stateless
public class ClientesFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoVueloPU");

    private UserTransaction utx;

    private ClientesJpaController jpa = new ClientesJpaController(emf);

    public ClientesFacade() {

    }

    public void crearCl(Clientes cliente){
        try {
            jpa.create(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ClientesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public Clientes buscarUsuario(String email){
//        Clientes buscacliente;
//        buscacliente = jpa.findClientes(email);
//        
//        return buscacliente;
//    }

}
