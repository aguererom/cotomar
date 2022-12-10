package org.iesalixar.servidor.repository;

import java.util.List;

import org.iesalixar.servidor.model.Aforo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AforoRepository extends JpaRepository<Aforo,Long> {
	public List<Aforo> findByOrderByIdDesc();
}
