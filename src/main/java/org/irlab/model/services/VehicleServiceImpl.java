package org.irlab.model.services;

import java.util.List;

import com.google.common.base.Preconditions;

import org.irlab.common.AppEntityManagerFactory;

import jakarta.persistence.EntityManager;

import javax.annotation.Nonnull;

import org.irlab.model.entities.Vehiculo;
import org.irlab.model.daos.VehiculoDao;
import org.irlab.model.exceptions.VehiculoNotFoundException;

public class VehicleServiceImpl implements VehicleService {

    @Override
    public boolean exists(@Nonnull String plate) {
        Preconditions.checkNotNull(plate, "Number plate cannot be null");

        if (plate.equals("")) {
            return false;
        }

        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            boolean exists = VehiculoDao
                .findByPlate(em, plate)
                .map(v -> {
                    return true;
                }).orElseThrow(() -> 
                    new VehiculoNotFoundException(String.format("with numberplate %s", plate)));

            return exists;
        } catch (VehiculoNotFoundException e) {
            return false;
        } catch ( Exception e ) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Vehiculo> listAllVehiculos() {
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            List<Vehiculo> clientList = VehiculoDao.getAllVehicles(em);
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
    public List<Vehiculo> listVehiculosFromUser(String dni){
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try{
            List<Vehiculo> clientList = VehiculoDao.getVehiclesFromUser(em,dni);
            return clientList;
        }catch (Exception e){
            throw e;
        }finally {
            em.close();
        }
    }
    @Override
    public void insertVehiculo(@Nonnull Vehiculo vehiculo) {
        Preconditions.checkNotNull(vehiculo, "Vehiculo cannot be null");

        if (exists(vehiculo.getMatricula())){
            System.out.println("Vehiculo already present");
            return;
        }
        EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();

        try {
            em.getTransaction().begin();
            VehiculoDao.update(em, vehiculo);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
