package org.irlab.model.daos;

import org.irlab.model.entities.Tarea;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TareaDao {
    private TareaDao() {}

    /**
     * Update the tarea details
     *
     * @param em   the entity manager
     * @param tarea the tarea
     */
    public static void update(EntityManager em, Tarea tarea) {
        em.persist(tarea);
    }

    public static List<Tarea> getAllTareas(EntityManager em) {
        TypedQuery<Tarea> q = em.createQuery("SELECT t FROM Tarea t", Tarea.class);
       
        return q.getResultList();
    }
}
