package org.irlab.model.entities;

import java.time.LocalDateTime;

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
        private LocalDateTime fechaAlta;

        public Cliente() {
            this.fechaAlta = LocalDateTime.now();
        }

        public Cliente(String dni) {
            this.fechaAlta = LocalDateTime.now();
            this.dni = dni;
        }

        public Cliente(String nombre, String dni, String telefono) {
            this.nombre = nombre;
            this.dni = dni;
            this.telefono = telefono;
            this.fechaAlta = LocalDateTime.now();
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

        public LocalDateTime getFechaAlta() {
            return fechaAlta;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("Cliente: ")
                .append(this.nombre)
                .append(" con DNI: ")
                .append(this.dni);

            return sb.toString();
        }
}
