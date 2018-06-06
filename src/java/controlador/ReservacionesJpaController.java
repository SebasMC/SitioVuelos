/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import controlador.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Clientes;
import entidad.Reservaciones;
import entidad.ReservacionesPK;
import entidad.Vuelos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jonh
 */
public class ReservacionesJpaController implements Serializable {

    public ReservacionesJpaController( EntityManagerFactory emf) {
        
        this.emf = emf;
    }
    private EntityTransaction utx = null;
    private EntityManagerFactory emf = null;

   

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reservaciones reservaciones) throws PreexistingEntityException, RollbackFailureException, Exception {
     
        EntityManager em = null;
        try {
            em = getEntityManager();
            utx = em.getTransaction();
            utx.begin();

            em.persist(reservaciones);

            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reservaciones reservaciones) throws NonexistentEntityException, RollbackFailureException, Exception {
        reservaciones.getReservacionesPK().setClientesIdcliente(reservaciones.getClientes().getIdcliente());
        reservaciones.getReservacionesPK().setVuelosIdvuelo(reservaciones.getVuelos().getIdvuelo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Reservaciones persistentReservaciones = em.find(Reservaciones.class, reservaciones.getReservacionesPK());
            Clientes clientesOld = persistentReservaciones.getClientes();
            Clientes clientesNew = reservaciones.getClientes();
            Vuelos vuelosOld = persistentReservaciones.getVuelos();
            Vuelos vuelosNew = reservaciones.getVuelos();
            if (clientesNew != null) {
                clientesNew = em.getReference(clientesNew.getClass(), clientesNew.getIdcliente());
                reservaciones.setClientes(clientesNew);
            }
            if (vuelosNew != null) {
                vuelosNew = em.getReference(vuelosNew.getClass(), vuelosNew.getIdvuelo());
                reservaciones.setVuelos(vuelosNew);
            }
            reservaciones = em.merge(reservaciones);
            if (clientesOld != null && !clientesOld.equals(clientesNew)) {
                clientesOld.getReservacionesCollection().remove(reservaciones);
                clientesOld = em.merge(clientesOld);
            }
            if (clientesNew != null && !clientesNew.equals(clientesOld)) {
                clientesNew.getReservacionesCollection().add(reservaciones);
                clientesNew = em.merge(clientesNew);
            }
            if (vuelosOld != null && !vuelosOld.equals(vuelosNew)) {
                vuelosOld.getReservacionesCollection().remove(reservaciones);
                vuelosOld = em.merge(vuelosOld);
            }
            if (vuelosNew != null && !vuelosNew.equals(vuelosOld)) {
                vuelosNew.getReservacionesCollection().add(reservaciones);
                vuelosNew = em.merge(vuelosNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ReservacionesPK id = reservaciones.getReservacionesPK();
                if (findReservaciones(id) == null) {
                    throw new NonexistentEntityException("The reservaciones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ReservacionesPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Reservaciones reservaciones;
            try {
                reservaciones = em.getReference(Reservaciones.class, id);
                reservaciones.getReservacionesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservaciones with id " + id + " no longer exists.", enfe);
            }
            Clientes clientes = reservaciones.getClientes();
            if (clientes != null) {
                clientes.getReservacionesCollection().remove(reservaciones);
                clientes = em.merge(clientes);
            }
            Vuelos vuelos = reservaciones.getVuelos();
            if (vuelos != null) {
                vuelos.getReservacionesCollection().remove(reservaciones);
                vuelos = em.merge(vuelos);
            }
            em.remove(reservaciones);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reservaciones> findReservacionesEntities() {
        return findReservacionesEntities(true, -1, -1);
    }

    public List<Reservaciones> findReservacionesEntities(int maxResults, int firstResult) {
        return findReservacionesEntities(false, maxResults, firstResult);
    }

    private List<Reservaciones> findReservacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reservaciones.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Reservaciones findReservaciones(ReservacionesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reservaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reservaciones> rt = cq.from(Reservaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
