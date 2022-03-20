package org.irlab.model.daos;

import org.irlab.model.entities.Vehiculo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class VehiculoDao {
    private VehiculoDao() {}

    /**
     * Update the vehicle details
     *
     * @param em   the entity manager
     * @param vehicle the vehicle
     */
    public static void update(EntityManager em, Vehiculo vehiculo) {
        em.persist(vehiculo);
    }

    /**
     * Find vehiculo by number plate
     *
     * @param em   the entity manager
     * @param matricula the vehicle number plate
     * @return the vehicle or an empty optional if it does not exists
     * @throws NonUniqueResultException if there is more than one client with the name
     */
    public static Optional<Vehiculo> findByPlate(EntityManager em, String matricula) {
        TypedQuery<Vehiculo> q = em
            .createQuery(
                    "SELECT v FROM Vehiculo v WHERE v.matricula = :matricula"
                    , Vehiculo.class);

        q.setParameter("matricula", matricula);
        List<Vehiculo> queryResult = q.getResultList();

        if (queryResult.size() > 1) {
            throw new NonUniqueResultException();
        } else if (queryResult.size() == 1) {
            return Optional.of(queryResult.get(0));
        } else {
            return Optional.empty();
        }
    }

    public static List<Vehiculo> getAllVehicles(EntityManager em) {
        TypedQuery<Vehiculo> q = em.createQuery("SELECT v FROM Vehiculo v", Vehiculo.class);
       
        return q.getResultList();
    }

    public static List<Vehiculo> getVehiclesFromUser (EntityManager em, String dni){
        TypedQuery<Vehiculo> q = em.createQuery("SELECT v FROM Vehiculo v WHERE v.client_dni = :dni",
                Vehiculo.class);
        q.setParameter("client_dni",dni );
        return q.getResultList();
    }
}

