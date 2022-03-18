package org.irlab.model.services;

import java.util.List;

import com.google.common.base.Preconditions;

import org.irlab.common.AppEntityManagerFactory;

import jakarta.persistence.EntityManager;

import javax.annotation.Nonnull;

import org.irlab.model.entities.Tipo;
import org.irlab.model.daos.TipoDao;
import org.irlab.model.exceptions.TipoNotFoundException;

public class TipoServiceImpl implements TipoService {

    @Override
    public boolean exists(@Nonnull String nombre) {
        Preconditions.checkNotNull(nombre, "Nombre cannot be null");

        if (nombre.equals("")) {
            return false;
        }

        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            boolean exists = TipoDao
                .findByNombre(em, nombre)
                .map(t -> {
                    return true;
                }).orElseThrow(() -> new TipoNotFoundException(String.format("with nombre %s", nombre)));

            return exists;
        } catch (TipoNotFoundException e) {
            return false;
        } catch ( Exception e ) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Tipo> listAllTipos() {
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            List<Tipo> tiposList = TipoDao.getAllTipos(em);
            em.getTransaction().begin();
            em.getTransaction().commit();

            return tiposList;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

    }

    @Override
    public void insertTipo(@Nonnull Tipo tipo) {
        Preconditions.checkNotNull(tipo, "Tipo cannot be null");

        if (exists(tipo.getNombre())){
            System.out.println("Tipo already present");
            return;
        }
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            em.getTransaction().begin();
            TipoDao.update(em, tipo);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
