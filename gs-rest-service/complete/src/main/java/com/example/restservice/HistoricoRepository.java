package com.example.restservice;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restservice.Historico;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long> {
  List<Historico> findByClienteId(long clienteId);
}