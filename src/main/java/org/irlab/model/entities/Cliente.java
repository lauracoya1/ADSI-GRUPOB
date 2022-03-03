package org.irlab.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Clientes")
public class Cliente {

	@Id
	private String dni;

	private String nombre;
	private String telefono;

        public Cliente() {
        }
	
        public Cliente(String dni) {
	    this.dni = dni;
        }
	
	public Cliente(String nombre, String dni, String telefono) {
		this.nombre = nombre;
		this.dni = dni;
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getTelefono() {
		return telefono;
	}

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
}
