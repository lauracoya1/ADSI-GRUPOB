package org.irlab.model.services;

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
}
