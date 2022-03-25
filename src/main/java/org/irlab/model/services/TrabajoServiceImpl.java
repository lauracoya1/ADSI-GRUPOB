package org.irlab.model.services;

import java.util.List;

import org.irlab.common.AppEntityManagerFactory;

import jakarta.persistence.EntityManager;

import org.irlab.model.entities.Trabajo;
import org.irlab.model.daos.TrabajoDao;

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
