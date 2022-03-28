package org.irlab.model.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import org.irlab.common.AppEntityManagerFactory;

import jakarta.persistence.EntityManager;

import javax.annotation.Nonnull;

import org.irlab.model.entities.Elevador;
import org.irlab.model.entities.Tarea;
import org.irlab.model.entities.User;
import org.irlab.model.daos.ElevadorDao;
import org.irlab.model.daos.TareaDao;
import org.irlab.model.daos.UserDao;
import org.irlab.model.utils.ScheduleSlot;

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
    public List<ScheduleSlot> findSlots(@Nonnull LocalDateTime date, Tarea tarea) {
        Preconditions.checkNotNull(date, "Given date cannot be null");
        Preconditions.checkNotNull(tarea, "Given tarea cannot be null");
        Preconditions.checkNotNull(tarea.getTipo(), "Tarea tipo cannot be null");

        
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        LocalDateTime workDayFinishTime = date.toLocalDate().atTime(19, 0, 0) ;
        LocalDateTime workDayStartTime = date.toLocalDate().atTime(8, 30, 0) ;
        LocalDateTime currentIteratorDate = date;
        List<ScheduleSlot> scheduleSlots = new ArrayList<ScheduleSlot>();

        try {
            while(scheduleSlots.size() < 3 ) {
                if (currentIteratorDate.isBefore(workDayStartTime)) {
                    currentIteratorDate = workDayStartTime;
                }
                if (currentIteratorDate.isAfter(workDayFinishTime)) {
                    currentIteratorDate.plusDays(1);
                    currentIteratorDate = currentIteratorDate.toLocalDate().atTime(8, 30, 0);
                }

                List<User> users = UserDao.getTechs(em);
                List<Elevador> elevadores = ElevadorDao.getAllElevadores(em);
                List<Tarea> taskOnPeriod = TareaDao.getTareasBetween(
                        em,
                        currentIteratorDate,
                        currentIteratorDate.plusMinutes(tarea.getTipo().getDuracion() - 1));

                if (taskOnPeriod.size() == 0) {
                } 
                for (Tarea t: taskOnPeriod) {
                    for (User u :t.getMecanicos()) {
                        int userIndex = users.indexOf(u);
                        if (userIndex >= 0) {
                            users.remove(userIndex);
                        }

                    }
                    int index = elevadores.indexOf(t.getElevador());
                    if (index >= 0) {
                        elevadores.remove(index);
                    }
                }
                
                for (User u: users) {
                    for (Elevador e: elevadores) {
                        if (scheduleSlots.size() < 3) {
                            ScheduleSlot sl 
                                = new ScheduleSlot( currentIteratorDate, u, e );

                            scheduleSlots.add(sl);
                        }

                    }
                }

                currentIteratorDate = currentIteratorDate
                    .plusMinutes(tarea.getTipo().getDuracion());


            }

            return scheduleSlots;
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
