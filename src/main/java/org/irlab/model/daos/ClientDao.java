package org.irlab.model.daos;

import org.irlab.model.entities.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ClientDao {
    private ClientDao() {}

    /**
     * Update the client details
     *
     * @param em   the entity manager
     * @param cliente the client
     */
    public static void update(EntityManager em, Cliente cliente) {
        em.persist(cliente);
    }

    /**
     * Find cliente by DNI
     *
     * @param em   the entity manager
     * @param dni the client dni
     * @return the client or an empty optional if it does not exists
     * @throws NonUniqueResultException if there is more than one client with the name
     */
    public static Optional<Cliente> findByDNI(EntityManager em, String dni) {
        TypedQuery<Cliente> q = em.createQuery("SELECT c FROM Cliente c WHERE c.dni = :dni", Cliente.class);
        q.setParameter("dni", dni);
        List<Cliente> queryResult = q.getResultList();
        if (queryResult.size() > 1) {
            throw new NonUniqueResultException();
        } else if (queryResult.size() == 1) {
            return Optional.of(queryResult.get(0));
        } else {
            return Optional.empty();
        }
    }

    public static List<Cliente> getAllClients(EntityManager em) {
        TypedQuery<Cliente> q = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
       
        return q.getResultList();
    }
}
