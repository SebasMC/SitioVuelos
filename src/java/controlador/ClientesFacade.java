/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Clientes;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.weld.bean.builtin.ee.UserTransactionBean;

/**
 *
 * @author sebas0
 */
@Stateless
public class ClientesFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoVueloPU");
    @Resource
    private UserTransaction utx;

    private ClientesJpaController jpa = new ClientesJpaController(utx, emf);

    public ClientesFacade() {
    }

    public void registrar(Clientes cliente) throws Exception {
        jpa.create(cliente);
    }

}
