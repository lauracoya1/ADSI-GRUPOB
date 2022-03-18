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
	private int duracion;
	private int precio;

        public Tipo() {
	}
	
	public Tipo(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

        public int getPrecio() {
            return precio;
        }

        public int getDuracion() {
            return duracion;
        }

        public void setPrecio(int precio) {
            this.precio = precio;
        }

        public void setDuracion(int duracion) {
            this.duracion = duracion;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Tipo: ")
                .append(this.nombre)
                .append(" con duracion: ")
                .append(duracion)
                .append(" y precio ")
                .append(this.precio);

            return sb.toString();
        }
}
