package org.irlab.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Elevadores")
public class Elevador {

	@Id
	@Column(unique=true, nullable = false)
	private String codigo;

        public Elevador() {
	}
	
	public Elevador(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

        @Override
        public String toString() {
            return "Elevador: " + this.codigo;
        }
}
