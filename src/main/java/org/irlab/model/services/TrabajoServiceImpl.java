package org.irlab.model.services;

import java.util.List;

import com.google.common.base.Preconditions;
import org.irlab.common.AppEntityManagerFactory;

import jakarta.persistence.EntityManager;

import org.irlab.model.daos.UserDao;
import org.irlab.model.entities.Trabajo;
import org.irlab.model.daos.TrabajoDao;
import org.irlab.model.entities.User;

import javax.annotation.Nonnull;

public class TrabajoServiceImpl implements TrabajoService {

    @Override
    public List<Trabajo> listAllTrabajos() {
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            List<Trabajo> trabajosList = TrabajoDao.getAllTrabajos(em);
            em.getTransaction().begin();
            em.getTransaction().commit();

            return trabajosList;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

    }
    @Override
    public void insertTrabajo(@Nonnull Trabajo trabajo) {
        Preconditions.checkNotNull(trabajo, "job cannot be null");

        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            em.getTransaction().begin();
            TrabajoDao.update(em,trabajo);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Trabajo> listTrabajosFromVehicle(String matricula){
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            List<Trabajo> trabajosList = TrabajoDao.getTrabajosFromVehicle(em,matricula);

            return trabajosList;
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
