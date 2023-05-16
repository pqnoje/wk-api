package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.ClienteRepository;
import com.example.restservice.HistoricoRepository;
import com.example.restservice.Cliente;
import com.example.restservice.Historico;
import com.example.restservice.SaqueDTO;

@RestController
public class ClienteController {
	@Autowired
    private ClienteRepository clienteRepository;
	@Autowired
    private HistoricoRepository historicoRepository;

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/clientes/{clienteId}")
	public Cliente getCliente(@PathVariable Long clienteId) {
		Optional<Cliente> optionalEntity = clienteRepository.findById(clienteId);
		Cliente clienteEntity = optionalEntity.get();
		
		return clienteEntity;
	}

	@GetMapping("/clientes/historico/{clienteId}")
	public List<Historico> historico(@PathVariable Long clienteId) {
		List<Historico> historicoEntity = historicoRepository.findByClienteId(clienteId);
		
		return historicoEntity;
	}

	@PostMapping("/clientes")
  	public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
		try {
			Cliente clienteEntity = clienteRepository.save(cliente);
			this.salvarHistorico(clienteEntity, "deposito");
			return new ResponseEntity<>(clienteEntity, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
  	}

	@PostMapping("/clientes/saque/{clienteId}")
  	public Cliente saque(@RequestBody SaqueDTO saque, @PathVariable Long clienteId) {
		try {
			Optional<Cliente> optionalEntity = clienteRepository.findById(clienteId);
			Cliente clienteEntity = optionalEntity.get();
			if(clienteEntity.getSaldo().floatValue() >= saque.getQuantidade().floatValue()) {
				if(clienteEntity.getPlanoExclusive() || saque.getQuantidade().floatValue() < 100) {
					clienteEntity.setSaldo(clienteEntity.getSaldo().subtract(saque.getQuantidade()));
					this.salvarHistorico(clienteEntity, "saque");
				} else if (saque.getQuantidade().floatValue() > 100 && saque.getQuantidade().floatValue() < 300){
					BigDecimal quantidade = BigDecimal.valueOf(saque.getQuantidade().doubleValue() * 1.004);
					if(clienteEntity.getSaldo().floatValue() >= quantidade.floatValue()) {
						clienteEntity.setSaldo(clienteEntity.getSaldo().subtract(quantidade));
						this.salvarHistorico(clienteEntity, "saque");
					}
				} else if (saque.getQuantidade().floatValue() > 300) {
					BigDecimal quantidade = BigDecimal.valueOf(saque.getQuantidade().doubleValue() * 1.01);
					if(clienteEntity.getSaldo().floatValue() >= quantidade.floatValue()) {
						clienteEntity.setSaldo(clienteEntity.getSaldo().subtract(quantidade));
						this.salvarHistorico(clienteEntity, "saque");
					}
				}
				
				clienteRepository.save(clienteEntity);
			}
			
			return clienteEntity;
		} catch (Exception e) {
			return new Cliente();
		}
  	}

	@PostMapping("/clientes/deposito/{clienteId}")
  	public Cliente deposito(@RequestBody DepositoDTO deposito, @PathVariable Long clienteId) {
		try {
			Optional<Cliente> optionalEntity = clienteRepository.findById(clienteId);
			Cliente clienteEntity = optionalEntity.get();
			clienteEntity.setSaldo(clienteEntity.getSaldo().add(deposito.getQuantidade()));
			this.salvarHistorico(clienteEntity, "deposito");
			clienteRepository.save(clienteEntity);
			return clienteEntity;
		} catch (Exception e) {
			return new Cliente();
		}
  	}

	private void salvarHistorico(Cliente clienteEntity, String tipoTransacao){
        Historico historico = new Historico();
        historico.setClienteId(clienteEntity.getId());
        historico.setDataTransacao(new Date());
        historico.setQuantidade(clienteEntity.getSaldo());
        historico.setTipoTransacao(tipoTransacao);
        historicoRepository.save(historico);
    }
}
