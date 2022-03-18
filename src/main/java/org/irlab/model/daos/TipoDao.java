package org.irlab.model.daos;

import org.irlab.model.entities.Tipo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class TipoDao {
    private TipoDao() {}

    /**
     * Update the tipo details
     *
     * @param em   the entity manager
     * @param tipo the tipo
     */
    public static void update(EntityManager em, Tipo tipo) {
        em.persist(tipo);
    }

    /**
     * Find tipo by nombre
     *
     * @param em   the entity manager
     * @param nombre the tipo nombre
     * @return the client or an empty optional if it does not exists
     * @throws NonUniqueResultException if there is more than one tipo with the name
     */
    public static Optional<Tipo> findByNombre(EntityManager em, String nombre) {
        TypedQuery<Tipo> q = em.createQuery("SELECT t FROM Tipo t WHERE t.nombre = :nombre", Tipo.class);
        q.setParameter("nombre", nombre);
        List<Tipo> queryResult = q.getResultList();
        if (queryResult.size() > 1) {
            throw new NonUniqueResultException();
        } else if (queryResult.size() == 1) {
            return Optional.of(queryResult.get(0));
        } else {
            return Optional.empty();
        }
    }

    public static List<Tipo> getAllTipos(EntityManager em) {
        TypedQuery<Tipo> q = em.createQuery("SELECT t FROM Tipo t", Tipo.class);
       
        return q.getResultList();
    }
}
