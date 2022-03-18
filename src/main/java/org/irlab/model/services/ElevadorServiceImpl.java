package org.irlab.model.services;

import java.util.List;

import com.google.common.base.Preconditions;

import org.irlab.common.AppEntityManagerFactory;

import jakarta.persistence.EntityManager;

import javax.annotation.Nonnull;

import org.irlab.model.entities.Elevador;
import org.irlab.model.daos.ElevadorDao;
import org.irlab.model.exceptions.ElevadorNotFoundException;

public class ElevadorServiceImpl implements ElevadorService {

    @Override
    public boolean exists(@Nonnull String codigo) {
        Preconditions.checkNotNull(codigo, "Codigo cannot be null");

        if (codigo.equals("")) {
            return false;
        }

        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            boolean exists = ElevadorDao
                .findByCodigo(em, codigo)
                .map(c -> {
                    return true;
                }).orElseThrow(() -> new ElevadorNotFoundException(String.format("with codigo %s", codigo)));

            return exists;
        } catch (ElevadorNotFoundException e) {
            return false;
        } catch ( Exception e ) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Elevador> listAllElevadores() {
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            List<Elevador> elevadoresList = ElevadorDao.getAllElevadores(em);
            em.getTransaction().begin();
            em.getTransaction().commit();

            return elevadoresList;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

    }

    @Override
    public void insertElevador(@Nonnull Elevador elevador) {
        Preconditions.checkNotNull(elevador, "Elevador cannot be null");

        if (exists(elevador.getCodigo())){
            System.out.println("Elevador already present");
            return;
        }
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            em.getTransaction().begin();
            ElevadorDao.update(em, elevador);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
