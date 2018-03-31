package enre.entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface SinLuzRepositorio extends CrudRepository<CorteServicioMedia, Long> {

	  CorteServicioMedia findBySubestacionAlimentador(String subestacion_alimentador);

	  List<CorteServicioMedia> findBylocalidad(String localidad);
	}