package org.irlab.model.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import org.irlab.common.AppEntityManagerFactory;

import jakarta.persistence.EntityManager;

import javax.annotation.Nonnull;

import org.irlab.model.entities.Tarea;
import org.irlab.model.daos.TareaDao;

public class TareaServiceImpl implements TareaService {

    @Override
    public List<Tarea> listAllTareas() {
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            List<Tarea> tareaList = TareaDao.getAllTareas(em);
            em.getTransaction().begin();
            em.getTransaction().commit();

            return tareaList;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

    }

    @Override
    public void insertTarea(@Nonnull Tarea tarea) {
        Preconditions.checkNotNull(tarea, "Tarea cannot be null");

        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            em.getTransaction().begin();
            TareaDao.update(em, tarea);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override 
    public List<LocalDateTime> findSlots(@Nonnull LocalDate date, Tarea tarea) {
        Preconditions.checkNotNull(date, "Given date cannot be null");
        Preconditions.checkNotNull(tarea, "Given tarea cannot be null");
        Preconditions.checkNotNull(tarea.getTipo(), "Tarea tipo cannot be null");

        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        LocalDateTime workDayFinishTime = date.atTime(19, 0, 0) ;
        LocalDateTime currentIteratorDate = date.atTime(8, 30, 0);
        List<LocalDateTime> slots = new ArrayList<LocalDateTime>();

        try {
            while(currentIteratorDate.isBefore(workDayFinishTime) && slots.size() < 3 ) {
                List<Tarea> taskOnPeriod = TareaDao.getTareasBetween(
                        em,
                        currentIteratorDate,
                        currentIteratorDate.plusMinutes(tarea.getTipo().getDuracion() - 1));

                if (taskOnPeriod.size() == 0) {
                    slots.add(currentIteratorDate);
                }

                currentIteratorDate = currentIteratorDate
                    .plusMinutes(tarea.getTipo().getDuracion());

            }

            return slots;
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
