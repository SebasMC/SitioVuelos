/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import controlador.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Origen;
import entidad.Destino;
import entidad.Compania;
import entidad.Reservaciones;
import entidad.Vuelos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jonh
 */
public class VuelosJpaController implements Serializable {

    public VuelosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vuelos vuelos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (vuelos.getReservacionesCollection() == null) {
            vuelos.setReservacionesCollection(new ArrayList<Reservaciones>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Origen origenIdorigen = vuelos.getOrigenIdorigen();
            if (origenIdorigen != null) {
                origenIdorigen = em.getReference(origenIdorigen.getClass(), origenIdorigen.getIdorigen());
                vuelos.setOrigenIdorigen(origenIdorigen);
            }
            Destino destinoIddestino = vuelos.getDestinoIddestino();
            if (destinoIddestino != null) {
                destinoIddestino = em.getReference(destinoIddestino.getClass(), destinoIddestino.getIddestino());
                vuelos.setDestinoIddestino(destinoIddestino);
            }
            Compania companiaIdcompania = vuelos.getCompaniaIdcompania();
            if (companiaIdcompania != null) {
                companiaIdcompania = em.getReference(companiaIdcompania.getClass(), companiaIdcompania.getIdcompania());
                vuelos.setCompaniaIdcompania(companiaIdcompania);
            }
            Collection<Reservaciones> attachedReservacionesCollection = new ArrayList<Reservaciones>();
            for (Reservaciones reservacionesCollectionReservacionesToAttach : vuelos.getReservacionesCollection()) {
                reservacionesCollectionReservacionesToAttach = em.getReference(reservacionesCollectionReservacionesToAttach.getClass(), reservacionesCollectionReservacionesToAttach.getReservacionesPK());
                attachedReservacionesCollection.add(reservacionesCollectionReservacionesToAttach);
            }
            vuelos.setReservacionesCollection(attachedReservacionesCollection);
            em.persist(vuelos);
            if (origenIdorigen != null) {
                origenIdorigen.getVuelosCollection().add(vuelos);
                origenIdorigen = em.merge(origenIdorigen);
            }
            if (destinoIddestino != null) {
                destinoIddestino.getVuelosCollection().add(vuelos);
                destinoIddestino = em.merge(destinoIddestino);
            }
            if (companiaIdcompania != null) {
                companiaIdcompania.getVuelosCollection().add(vuelos);
                companiaIdcompania = em.merge(companiaIdcompania);
            }
            for (Reservaciones reservacionesCollectionReservaciones : vuelos.getReservacionesCollection()) {
                Vuelos oldVuelosOfReservacionesCollectionReservaciones = reservacionesCollectionReservaciones.getVuelos();
                reservacionesCollectionReservaciones.setVuelos(vuelos);
                reservacionesCollectionReservaciones = em.merge(reservacionesCollectionReservaciones);
                if (oldVuelosOfReservacionesCollectionReservaciones != null) {
                    oldVuelosOfReservacionesCollectionReservaciones.getReservacionesCollection().remove(reservacionesCollectionReservaciones);
                    oldVuelosOfReservacionesCollectionReservaciones = em.merge(oldVuelosOfReservacionesCollectionReservaciones);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findVuelos(vuelos.getIdvuelo()) != null) {
                throw new PreexistingEntityException("Vuelos " + vuelos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vuelos vuelos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Vuelos persistentVuelos = em.find(Vuelos.class, vuelos.getIdvuelo());
            Origen origenIdorigenOld = persistentVuelos.getOrigenIdorigen();
            Origen origenIdorigenNew = vuelos.getOrigenIdorigen();
            Destino destinoIddestinoOld = persistentVuelos.getDestinoIddestino();
            Destino destinoIddestinoNew = vuelos.getDestinoIddestino();
            Compania companiaIdcompaniaOld = persistentVuelos.getCompaniaIdcompania();
            Compania companiaIdcompaniaNew = vuelos.getCompaniaIdcompania();
            Collection<Reservaciones> reservacionesCollectionOld = persistentVuelos.getReservacionesCollection();
            Collection<Reservaciones> reservacionesCollectionNew = vuelos.getReservacionesCollection();
            List<String> illegalOrphanMessages = null;
            for (Reservaciones reservacionesCollectionOldReservaciones : reservacionesCollectionOld) {
                if (!reservacionesCollectionNew.contains(reservacionesCollectionOldReservaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservaciones " + reservacionesCollectionOldReservaciones + " since its vuelos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (origenIdorigenNew != null) {
                origenIdorigenNew = em.getReference(origenIdorigenNew.getClass(), origenIdorigenNew.getIdorigen());
                vuelos.setOrigenIdorigen(origenIdorigenNew);
            }
            if (destinoIddestinoNew != null) {
                destinoIddestinoNew = em.getReference(destinoIddestinoNew.getClass(), destinoIddestinoNew.getIddestino());
                vuelos.setDestinoIddestino(destinoIddestinoNew);
            }
            if (companiaIdcompaniaNew != null) {
                companiaIdcompaniaNew = em.getReference(companiaIdcompaniaNew.getClass(), companiaIdcompaniaNew.getIdcompania());
                vuelos.setCompaniaIdcompania(companiaIdcompaniaNew);
            }
            Collection<Reservaciones> attachedReservacionesCollectionNew = new ArrayList<Reservaciones>();
            for (Reservaciones reservacionesCollectionNewReservacionesToAttach : reservacionesCollectionNew) {
                reservacionesCollectionNewReservacionesToAttach = em.getReference(reservacionesCollectionNewReservacionesToAttach.getClass(), reservacionesCollectionNewReservacionesToAttach.getReservacionesPK());
                attachedReservacionesCollectionNew.add(reservacionesCollectionNewReservacionesToAttach);
            }
            reservacionesCollectionNew = attachedReservacionesCollectionNew;
            vuelos.setReservacionesCollection(reservacionesCollectionNew);
            vuelos = em.merge(vuelos);
            if (origenIdorigenOld != null && !origenIdorigenOld.equals(origenIdorigenNew)) {
                origenIdorigenOld.getVuelosCollection().remove(vuelos);
                origenIdorigenOld = em.merge(origenIdorigenOld);
            }
            if (origenIdorigenNew != null && !origenIdorigenNew.equals(origenIdorigenOld)) {
                origenIdorigenNew.getVuelosCollection().add(vuelos);
                origenIdorigenNew = em.merge(origenIdorigenNew);
            }
            if (destinoIddestinoOld != null && !destinoIddestinoOld.equals(destinoIddestinoNew)) {
                destinoIddestinoOld.getVuelosCollection().remove(vuelos);
                destinoIddestinoOld = em.merge(destinoIddestinoOld);
            }
            if (destinoIddestinoNew != null && !destinoIddestinoNew.equals(destinoIddestinoOld)) {
                destinoIddestinoNew.getVuelosCollection().add(vuelos);
                destinoIddestinoNew = em.merge(destinoIddestinoNew);
            }
            if (companiaIdcompaniaOld != null && !companiaIdcompaniaOld.equals(companiaIdcompaniaNew)) {
                companiaIdcompaniaOld.getVuelosCollection().remove(vuelos);
                companiaIdcompaniaOld = em.merge(companiaIdcompaniaOld);
            }
            if (companiaIdcompaniaNew != null && !companiaIdcompaniaNew.equals(companiaIdcompaniaOld)) {
                companiaIdcompaniaNew.getVuelosCollection().add(vuelos);
                companiaIdcompaniaNew = em.merge(companiaIdcompaniaNew);
            }
            for (Reservaciones reservacionesCollectionNewReservaciones : reservacionesCollectionNew) {
                if (!reservacionesCollectionOld.contains(reservacionesCollectionNewReservaciones)) {
                    Vuelos oldVuelosOfReservacionesCollectionNewReservaciones = reservacionesCollectionNewReservaciones.getVuelos();
                    reservacionesCollectionNewReservaciones.setVuelos(vuelos);
                    reservacionesCollectionNewReservaciones = em.merge(reservacionesCollectionNewReservaciones);
                    if (oldVuelosOfReservacionesCollectionNewReservaciones != null && !oldVuelosOfReservacionesCollectionNewReservaciones.equals(vuelos)) {
                        oldVuelosOfReservacionesCollectionNewReservaciones.getReservacionesCollection().remove(reservacionesCollectionNewReservaciones);
                        oldVuelosOfReservacionesCollectionNewReservaciones = em.merge(oldVuelosOfReservacionesCollectionNewReservaciones);
                    }
                }
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
                Integer id = vuelos.getIdvuelo();
                if (findVuelos(id) == null) {
                    throw new NonexistentEntityException("The vuelos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Vuelos vuelos;
            try {
                vuelos = em.getReference(Vuelos.class, id);
                vuelos.getIdvuelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vuelos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Reservaciones> reservacionesCollectionOrphanCheck = vuelos.getReservacionesCollection();
            for (Reservaciones reservacionesCollectionOrphanCheckReservaciones : reservacionesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vuelos (" + vuelos + ") cannot be destroyed since the Reservaciones " + reservacionesCollectionOrphanCheckReservaciones + " in its reservacionesCollection field has a non-nullable vuelos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Origen origenIdorigen = vuelos.getOrigenIdorigen();
            if (origenIdorigen != null) {
                origenIdorigen.getVuelosCollection().remove(vuelos);
                origenIdorigen = em.merge(origenIdorigen);
            }
            Destino destinoIddestino = vuelos.getDestinoIddestino();
            if (destinoIddestino != null) {
                destinoIddestino.getVuelosCollection().remove(vuelos);
                destinoIddestino = em.merge(destinoIddestino);
            }
            Compania companiaIdcompania = vuelos.getCompaniaIdcompania();
            if (companiaIdcompania != null) {
                companiaIdcompania.getVuelosCollection().remove(vuelos);
                companiaIdcompania = em.merge(companiaIdcompania);
            }
            em.remove(vuelos);
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

    public List<Vuelos> findVuelosEntities() {
        return findVuelosEntities(true, -1, -1);
    }

    public List<Vuelos> findVuelosEntities(int maxResults, int firstResult) {
        return findVuelosEntities(false, maxResults, firstResult);
    }

    private List<Vuelos> findVuelosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vuelos.class));
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

    public Vuelos findVuelos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vuelos.class, id);
        } finally {
            em.close();
        }
    }

    public int getVuelosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vuelos> rt = cq.from(Vuelos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
