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
import entidad.Compania;
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
public class CompaniaJpaController implements Serializable {

    public CompaniaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compania compania) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (compania.getVuelosCollection() == null) {
            compania.setVuelosCollection(new ArrayList<Vuelos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Vuelos> attachedVuelosCollection = new ArrayList<Vuelos>();
            for (Vuelos vuelosCollectionVuelosToAttach : compania.getVuelosCollection()) {
                vuelosCollectionVuelosToAttach = em.getReference(vuelosCollectionVuelosToAttach.getClass(), vuelosCollectionVuelosToAttach.getIdvuelo());
                attachedVuelosCollection.add(vuelosCollectionVuelosToAttach);
            }
            compania.setVuelosCollection(attachedVuelosCollection);
            em.persist(compania);
            for (Vuelos vuelosCollectionVuelos : compania.getVuelosCollection()) {
                Compania oldCompaniaIdcompaniaOfVuelosCollectionVuelos = vuelosCollectionVuelos.getCompaniaIdcompania();
                vuelosCollectionVuelos.setCompaniaIdcompania(compania);
                vuelosCollectionVuelos = em.merge(vuelosCollectionVuelos);
                if (oldCompaniaIdcompaniaOfVuelosCollectionVuelos != null) {
                    oldCompaniaIdcompaniaOfVuelosCollectionVuelos.getVuelosCollection().remove(vuelosCollectionVuelos);
                    oldCompaniaIdcompaniaOfVuelosCollectionVuelos = em.merge(oldCompaniaIdcompaniaOfVuelosCollectionVuelos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCompania(compania.getIdcompania()) != null) {
                throw new PreexistingEntityException("Compania " + compania + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compania compania) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Compania persistentCompania = em.find(Compania.class, compania.getIdcompania());
            Collection<Vuelos> vuelosCollectionOld = persistentCompania.getVuelosCollection();
            Collection<Vuelos> vuelosCollectionNew = compania.getVuelosCollection();
            List<String> illegalOrphanMessages = null;
            for (Vuelos vuelosCollectionOldVuelos : vuelosCollectionOld) {
                if (!vuelosCollectionNew.contains(vuelosCollectionOldVuelos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vuelos " + vuelosCollectionOldVuelos + " since its companiaIdcompania field is not nullable.");
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
            compania.setVuelosCollection(vuelosCollectionNew);
            compania = em.merge(compania);
            for (Vuelos vuelosCollectionNewVuelos : vuelosCollectionNew) {
                if (!vuelosCollectionOld.contains(vuelosCollectionNewVuelos)) {
                    Compania oldCompaniaIdcompaniaOfVuelosCollectionNewVuelos = vuelosCollectionNewVuelos.getCompaniaIdcompania();
                    vuelosCollectionNewVuelos.setCompaniaIdcompania(compania);
                    vuelosCollectionNewVuelos = em.merge(vuelosCollectionNewVuelos);
                    if (oldCompaniaIdcompaniaOfVuelosCollectionNewVuelos != null && !oldCompaniaIdcompaniaOfVuelosCollectionNewVuelos.equals(compania)) {
                        oldCompaniaIdcompaniaOfVuelosCollectionNewVuelos.getVuelosCollection().remove(vuelosCollectionNewVuelos);
                        oldCompaniaIdcompaniaOfVuelosCollectionNewVuelos = em.merge(oldCompaniaIdcompaniaOfVuelosCollectionNewVuelos);
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
                Integer id = compania.getIdcompania();
                if (findCompania(id) == null) {
                    throw new NonexistentEntityException("The compania with id " + id + " no longer exists.");
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
            Compania compania;
            try {
                compania = em.getReference(Compania.class, id);
                compania.getIdcompania();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compania with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Vuelos> vuelosCollectionOrphanCheck = compania.getVuelosCollection();
            for (Vuelos vuelosCollectionOrphanCheckVuelos : vuelosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Compania (" + compania + ") cannot be destroyed since the Vuelos " + vuelosCollectionOrphanCheckVuelos + " in its vuelosCollection field has a non-nullable companiaIdcompania field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(compania);
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

    public List<Compania> findCompaniaEntities() {
        return findCompaniaEntities(true, -1, -1);
    }

    public List<Compania> findCompaniaEntities(int maxResults, int firstResult) {
        return findCompaniaEntities(false, maxResults, firstResult);
    }

    private List<Compania> findCompaniaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compania.class));
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

    public Compania findCompania(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compania.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompaniaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compania> rt = cq.from(Compania.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
