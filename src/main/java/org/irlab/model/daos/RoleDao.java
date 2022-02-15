package org.irlab.model.daos;

import java.util.List;
import java.util.Optional;

import org.irlab.model.entities.Role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

public class RoleDao {
	private RoleDao () {}
	
	public static Optional<Role> findByName(EntityManager em, String roleName) {
	    TypedQuery<Role> q = em.createQuery("SELECT r FROM Role r WHERE r.roleName = :name", Role.class);
	    q.setParameter("name", roleName);
	    List<Role> queryResult = q.getResultList();
	    if (queryResult.size() > 1) {
	      throw new NonUniqueResultException();
	    } else if (queryResult.size() == 1) {
	      return Optional.of(queryResult.get(0));
	    } else {
	      return Optional.empty();
	    }
	}
}
