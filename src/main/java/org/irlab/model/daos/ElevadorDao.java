package org.irlab.model.daos;

import org.irlab.model.entities.Elevador;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ElevadorDao {
    private ElevadorDao() {}

    /**
     * Update the elevador details
     *
     * @param em   the entity manager
     * @param elevador the elevador
     */
    public static void update(EntityManager em, Elevador elevador) {
        em.persist(elevador);
    }

    /**
     * Find Elevador by Codigo 
     *
     * @param em   the entity manager
     * @param codigo the elevator code 
     * @return the levador or an empty optional if it does not exists
     * @throws NonUniqueResultException if there is more than one elevador with the code
     */
    public static Optional<Elevador> findByCodigo(EntityManager em, String codigo) {
        TypedQuery<Elevador> q = em.createQuery("SELECT e FROM Elevador e WHERE e.codigo = :codigo", Elevador.class);
        q.setParameter("codigo", codigo);
        List<Elevador> queryResult = q.getResultList();
        if (queryResult.size() > 1) {
            throw new NonUniqueResultException();
        } else if (queryResult.size() == 1) {
            return Optional.of(queryResult.get(0));
        } else {
            return Optional.empty();
        }
    }

    public static List<Elevador> getAllElevadores(EntityManager em) {
        TypedQuery<Elevador> q = em.createQuery("SELECT e FROM Elevador e", Elevador.class);
       
        return q.getResultList();
    }
}
