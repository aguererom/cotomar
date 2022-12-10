package org.iesalixar.servidor.repository;

import org.iesalixar.servidor.model.Recinto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecintoRepository extends JpaRepository<Recinto,Long> {
	public Recinto findByNombre(String nombre);
	public Recinto findById(String id);
}
