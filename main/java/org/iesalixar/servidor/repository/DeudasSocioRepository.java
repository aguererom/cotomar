package org.iesalixar.servidor.repository;

import java.util.List;

import org.iesalixar.servidor.model.DeudasSocio;
import org.iesalixar.servidor.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeudasSocioRepository extends JpaRepository<DeudasSocio,Long> {

	public List<DeudasSocio> findBySocio(Socio socio);
	public void deleteById(Long id);
	
}
