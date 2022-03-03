package org.irlab.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Tipos")
public class Tipo {
	@Id
	@Column(unique=true, nullable = false)
	private String nombre;

        public Tipo() {
	}
	
	public Tipo(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
}
