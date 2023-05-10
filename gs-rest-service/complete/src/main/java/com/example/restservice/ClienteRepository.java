package com.example.restservice;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restservice.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  List<Cliente> findByNome(String nome);

  Cliente findById(long id);
}