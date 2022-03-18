package org.irlab.model.services;

import java.util.List;

import com.google.common.base.Preconditions;

import org.irlab.common.AppEntityManagerFactory;

import jakarta.persistence.EntityManager;

import javax.annotation.Nonnull;

import org.irlab.model.entities.Cliente;
import org.irlab.model.daos.ClientDao;
import org.irlab.model.exceptions.ClientNotFoundException;

public class ClientServiceImpl implements ClientService {

    @Override
    public boolean exists(@Nonnull String dni) {
        Preconditions.checkNotNull(dni, "DNI cannot be null");

        if (dni.equals("")) {
            return false;
        }

        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            boolean exists = ClientDao
                .findByDNI(em, dni)
                .map(c -> {
                    return true;
                }).orElseThrow(() -> new ClientNotFoundException(String.format("with DNI %s", dni)));

            return exists;
        } catch (ClientNotFoundException e) {
            return false;
        } catch ( Exception e ) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> listAllClients() {
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            List<Cliente> clientList = ClientDao.getAllClients(em);
            em.getTransaction().begin();
            em.getTransaction().commit();

            return clientList;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

    }

    @Override
    public void insertClient(@Nonnull Cliente cliente) {
        Preconditions.checkNotNull(cliente, "Cliente cannot be null");

        if (exists(cliente.getDni())){
            System.out.println("Cliente already present");
            return;
        }
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            em.getTransaction().begin();
            ClientDao.update(em, cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
