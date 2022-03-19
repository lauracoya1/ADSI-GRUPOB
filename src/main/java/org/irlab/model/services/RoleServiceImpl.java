package org.irlab.model.services;

import java.util.List;

import org.irlab.common.AppEntityManagerFactory;

import jakarta.persistence.EntityManager;

import org.irlab.model.entities.Role;
import org.irlab.model.daos.RoleDao;

public class RoleServiceImpl implements RoleService {


    @Override
    public List<Role> listAllRoles() {
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            List<Role> roleList = RoleDao.getAllRoles(em);
            em.getTransaction().begin();
            em.getTransaction().commit();

            return roleList;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

    }
}
