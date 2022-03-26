package org.irlab.model.daos;

import org.irlab.model.entities.Tarea;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public static List<Tarea> getTareasByDate(EntityManager em, LocalDate date) {
        TypedQuery<Tarea> q = em.createQuery("SELECT t FROM Tarea t "
                .concat("WHERE t.dateTime = :date"), Tarea.class);

        q.setParameter("date", date);

        return q.getResultList();
    }

    public static List<Tarea> getTareasBetween( 
            EntityManager em,
            LocalDateTime startTime,
            LocalDateTime endTime
    ){

        TypedQuery<Tarea> q = em.createQuery("SELECT t FROM Tarea t "
                .concat("WHERE t.dateTime < :end"), Tarea.class);

        q.setParameter("end", endTime);

        List<Tarea> queryList = q.getResultList();
        List<Tarea> result = new ArrayList<Tarea>();

        if (queryList.size() > 0) {
            for (Tarea t : queryList) {
                if (t.getDateTime().plusMinutes(t.getTipo().getDuracion()).isAfter(startTime)) {
                    result.add(t);
                }
            }

            return result;
        }

        return queryList;

    }
}
