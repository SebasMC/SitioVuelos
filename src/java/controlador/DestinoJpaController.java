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
import entidad.Destino;
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
 * @author Jonh
 */
public class DestinoJpaController implements Serializable {

    public DestinoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Destino destino) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (destino.getVuelosCollection() == null) {
            destino.setVuelosCollection(new ArrayList<Vuelos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Vuelos> attachedVuelosCollection = new ArrayList<Vuelos>();
            for (Vuelos vuelosCollectionVuelosToAttach : destino.getVuelosCollection()) {
                vuelosCollectionVuelosToAttach = em.getReference(vuelosCollectionVuelosToAttach.getClass(), vuelosCollectionVuelosToAttach.getIdvuelo());
                attachedVuelosCollection.add(vuelosCollectionVuelosToAttach);
            }
            destino.setVuelosCollection(attachedVuelosCollection);
            em.persist(destino);
            for (Vuelos vuelosCollectionVuelos : destino.getVuelosCollection()) {
                Destino oldDestinoIddestinoOfVuelosCollectionVuelos = vuelosCollectionVuelos.getDestinoIddestino();
                vuelosCollectionVuelos.setDestinoIddestino(destino);
                vuelosCollectionVuelos = em.merge(vuelosCollectionVuelos);
                if (oldDestinoIddestinoOfVuelosCollectionVuelos != null) {
                    oldDestinoIddestinoOfVuelosCollectionVuelos.getVuelosCollection().remove(vuelosCollectionVuelos);
                    oldDestinoIddestinoOfVuelosCollectionVuelos = em.merge(oldDestinoIddestinoOfVuelosCollectionVuelos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findDestino(destino.getIddestino()) != null) {
                throw new PreexistingEntityException("Destino " + destino + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Destino destino) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Destino persistentDestino = em.find(Destino.class, destino.getIddestino());
            Collection<Vuelos> vuelosCollectionOld = persistentDestino.getVuelosCollection();
            Collection<Vuelos> vuelosCollectionNew = destino.getVuelosCollection();
            List<String> illegalOrphanMessages = null;
            for (Vuelos vuelosCollectionOldVuelos : vuelosCollectionOld) {
                if (!vuelosCollectionNew.contains(vuelosCollectionOldVuelos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vuelos " + vuelosCollectionOldVuelos + " since its destinoIddestino field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Vuelos> attachedVuelosCollectionNew = new ArrayList<Vuelos>();
            for (Vuelos vuelosCollectionNewVuelosToAttach : vuelosCollectionNew) {
                vuelosCollectionNewVuelosToAttach = em.getReference(vuelosCollectionNewVuelosToAttach.getClass(), vuelosCollectionNewVuelosToAttach.getIdvuelo());
                attachedVuelosCollectionNew.add(vuelosCollectionNewVuelosToAttach);
            }
            vuelosCollectionNew = attachedVuelosCollectionNew;
            destino.setVuelosCollection(vuelosCollectionNew);
            destino = em.merge(destino);
            for (Vuelos vuelosCollectionNewVuelos : vuelosCollectionNew) {
                if (!vuelosCollectionOld.contains(vuelosCollectionNewVuelos)) {
                    Destino oldDestinoIddestinoOfVuelosCollectionNewVuelos = vuelosCollectionNewVuelos.getDestinoIddestino();
                    vuelosCollectionNewVuelos.setDestinoIddestino(destino);
                    vuelosCollectionNewVuelos = em.merge(vuelosCollectionNewVuelos);
                    if (oldDestinoIddestinoOfVuelosCollectionNewVuelos != null && !oldDestinoIddestinoOfVuelosCollectionNewVuelos.equals(destino)) {
                        oldDestinoIddestinoOfVuelosCollectionNewVuelos.getVuelosCollection().remove(vuelosCollectionNewVuelos);
                        oldDestinoIddestinoOfVuelosCollectionNewVuelos = em.merge(oldDestinoIddestinoOfVuelosCollectionNewVuelos);
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
                Integer id = destino.getIddestino();
                if (findDestino(id) == null) {
                    throw new NonexistentEntityException("The destino with id " + id + " no longer exists.");
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
            Destino destino;
            try {
                destino = em.getReference(Destino.class, id);
                destino.getIddestino();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The destino with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Vuelos> vuelosCollectionOrphanCheck = destino.getVuelosCollection();
            for (Vuelos vuelosCollectionOrphanCheckVuelos : vuelosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Destino (" + destino + ") cannot be destroyed since the Vuelos " + vuelosCollectionOrphanCheckVuelos + " in its vuelosCollection field has a non-nullable destinoIddestino field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(destino);
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

    public List<Destino> findDestinoEntities() {
        return findDestinoEntities(true, -1, -1);
    }

    public List<Destino> findDestinoEntities(int maxResults, int firstResult) {
        return findDestinoEntities(false, maxResults, firstResult);
    }

    private List<Destino> findDestinoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Destino.class));
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

    public Destino findDestino(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Destino.class, id);
        } finally {
            em.close();
        }
    }

    public int getDestinoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Destino> rt = cq.from(Destino.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
