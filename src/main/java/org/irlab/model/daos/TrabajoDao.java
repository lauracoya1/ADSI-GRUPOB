package org.irlab.model.daos;

import org.irlab.model.entities.Trabajo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class TrabajoDao {
    private TrabajoDao() {}

    /**
     * Update the trabajo details
     *
     * @param em   the entity manager
     * @param trabajo the trabajo
     */
    public static void update(EntityManager em, Trabajo trabajo) {
        em.persist(trabajo);
    }

    /**
     * Find trabajo by ID
     *
     * @param em   the entity manager
     * @param ID the trabajo id
     * @return the trabajo or an empty optional if it does not exists
     * @throws NonUniqueResultException if there is more than one trabajo with the name
     */
    public static Optional<Trabajo> findById(EntityManager em, int id) {
        TypedQuery<Trabajo> q = em.createQuery("SELECT t FROM Trabajo t WHERE t.id = :id", Trabajo.class);
        q.setParameter("id", id);
        List<Trabajo> queryResult = q.getResultList();
        if (queryResult.size() > 1) {
            throw new NonUniqueResultException();
        } else if (queryResult.size() == 1) {
            return Optional.of(queryResult.get(0));
        } else {
            return Optional.empty();
        }
    }

    public static List<Trabajo> getAllTrabajos(EntityManager em) {
        TypedQuery<Trabajo> q = em.createQuery("SELECT t FROM Trabajo t", Trabajo.class);
       
        return q.getResultList();
    }

    public static List<Trabajo> getTrabajosFromVehicle(EntityManager em, String matricula){
        TypedQuery<Trabajo> q = em.createQuery("SELECT t FROM Trabajo t WHERE t.vehiculo.matricula = :matricula"
                , Trabajo.class);
        q.setParameter("matricula",matricula);
        return q.getResultList();
    }
}
