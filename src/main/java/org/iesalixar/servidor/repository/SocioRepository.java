package org.iesalixar.servidor.repository;

import java.util.List;
import org.iesalixar.servidor.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio,Long> {

	public Socio findByNumCasa(String numCasa);
	public void deleteByNumCasa(String numCasa);
	public List<Socio> findByOrderByNumCasaAsc();

	
}
