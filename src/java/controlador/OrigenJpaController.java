/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import controlador.exceptions.RollbackFailureException;
import entidad.Origen;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidad.Vuelos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author sebas
 */
public class OrigenJpaController implements Serializable {

    public OrigenJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Origen origen) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (origen.getVuelosCollection() == null) {
            origen.setVuelosCollection(new ArrayList<Vuelos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Vuelos> attachedVuelosCollection = new ArrayList<Vuelos>();
            for (Vuelos vuelosCollectionVuelosToAttach : origen.getVuelosCollection()) {
                vuelosCollectionVuelosToAttach = em.getReference(vuelosCollectionVuelosToAttach.getClass(), vuelosCollectionVuelosToAttach.getIdvuelo());
                attachedVuelosCollection.add(vuelosCollectionVuelosToAttach);
            }
            origen.setVuelosCollection(attachedVuelosCollection);
            em.persist(origen);
            for (Vuelos vuelosCollectionVuelos : origen.getVuelosCollection()) {
                Origen oldOrigenIdorigenOfVuelosCollectionVuelos = vuelosCollectionVuelos.getOrigenIdorigen();
                vuelosCollectionVuelos.setOrigenIdorigen(origen);
                vuelosCollectionVuelos = em.merge(vuelosCollectionVuelos);
                if (oldOrigenIdorigenOfVuelosCollectionVuelos != null) {
                    oldOrigenIdorigenOfVuelosCollectionVuelos.getVuelosCollection().remove(vuelosCollectionVuelos);
                    oldOrigenIdorigenOfVuelosCollectionVuelos = em.merge(oldOrigenIdorigenOfVuelosCollectionVuelos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findOrigen(origen.getIdorigen()) != null) {
                throw new PreexistingEntityException("Origen " + origen + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Origen origen) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Origen persistentOrigen = em.find(Origen.class, origen.getIdorigen());
            Collection<Vuelos> vuelosCollectionOld = persistentOrigen.getVuelosCollection();
            Collection<Vuelos> vuelosCollectionNew = origen.getVuelosCollection();
            Collection<Vuelos> attachedVuelosCollectionNew = new ArrayList<Vuelos>();
            for (Vuelos vuelosCollectionNewVuelosToAttach : vuelosCollectionNew) {
                vuelosCollectionNewVuelosToAttach = em.getReference(vuelosCollectionNewVuelosToAttach.getClass(), vuelosCollectionNewVuelosToAttach.getIdvuelo());
                attachedVuelosCollectionNew.add(vuelosCollectionNewVuelosToAttach);
            }
            vuelosCollectionNew = attachedVuelosCollectionNew;
            origen.setVuelosCollection(vuelosCollectionNew);
            origen = em.merge(origen);
            for (Vuelos vuelosCollectionOldVuelos : vuelosCollectionOld) {
                if (!vuelosCollectionNew.contains(vuelosCollectionOldVuelos)) {
                    vuelosCollectionOldVuelos.setOrigenIdorigen(null);
                    vuelosCollectionOldVuelos = em.merge(vuelosCollectionOldVuelos);
                }
            }
            for (Vuelos vuelosCollectionNewVuelos : vuelosCollectionNew) {
                if (!vuelosCollectionOld.contains(vuelosCollectionNewVuelos)) {
                    Origen oldOrigenIdorigenOfVuelosCollectionNewVuelos = vuelosCollectionNewVuelos.getOrigenIdorigen();
                    vuelosCollectionNewVuelos.setOrigenIdorigen(origen);
                    vuelosCollectionNewVuelos = em.merge(vuelosCollectionNewVuelos);
                    if (oldOrigenIdorigenOfVuelosCollectionNewVuelos != null && !oldOrigenIdorigenOfVuelosCollectionNewVuelos.equals(origen)) {
                        oldOrigenIdorigenOfVuelosCollectionNewVuelos.getVuelosCollection().remove(vuelosCollectionNewVuelos);
                        oldOrigenIdorigenOfVuelosCollectionNewVuelos = em.merge(oldOrigenIdorigenOfVuelosCollectionNewVuelos);
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
                Integer id = origen.getIdorigen();
                if (findOrigen(id) == null) {
                    throw new NonexistentEntityException("The origen with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Origen origen;
            try {
                origen = em.getReference(Origen.class, id);
                origen.getIdorigen();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The origen with id " + id + " no longer exists.", enfe);
            }
            Collection<Vuelos> vuelosCollection = origen.getVuelosCollection();
            for (Vuelos vuelosCollectionVuelos : vuelosCollection) {
                vuelosCollectionVuelos.setOrigenIdorigen(null);
                vuelosCollectionVuelos = em.merge(vuelosCollectionVuelos);
            }
            em.remove(origen);
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

    public List<Origen> findOrigenEntities() {
        return findOrigenEntities(true, -1, -1);
    }

    public List<Origen> findOrigenEntities(int maxResults, int firstResult) {
        return findOrigenEntities(false, maxResults, firstResult);
    }

    private List<Origen> findOrigenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Origen.class));
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

    public Origen findOrigen(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Origen.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrigenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Origen> rt = cq.from(Origen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
