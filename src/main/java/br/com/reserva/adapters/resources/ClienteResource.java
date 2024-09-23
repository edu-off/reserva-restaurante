package br.com.reserva.adapters.resources;

import br.com.reserva.adapters.controllers.CadastraClienteController;
import br.com.reserva.application.dto.ClienteDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteResource {

    private final CadastraClienteController cadastraCliente;

    @Autowired
    public ClienteResource(CadastraClienteController cadastraCliente) {
        this.cadastraCliente = cadastraCliente;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> cadastraCliente(@Validated @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO newClienteDTO = cadastraCliente.execute(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClienteDTO);
    }

}
