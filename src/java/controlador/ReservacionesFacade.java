/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidad.Clientes;
import entidad.Reservaciones;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jonh
 */
@Stateless
public class ReservacionesFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoVueloPU");

    private UserTransaction utx;

    private ReservacionesJpaController jpa = new ReservacionesJpaController(emf);

    public ReservacionesFacade() {

    }

    public void crearReservacion(Reservaciones reservacion) {
        try {
            jpa.create(reservacion);
        } catch (Exception ex) {
            Logger.getLogger(ClientesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
