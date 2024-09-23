package br.com.reserva.adapters.resources;

import br.com.reserva.adapters.controllers.AvaliaReservaController;
import br.com.reserva.adapters.controllers.BuscaReservasController;
import br.com.reserva.adapters.controllers.EfetuaReservaController;
import br.com.reserva.adapters.controllers.GerenciaReservaController;
import br.com.reserva.application.dto.AvaliacaoDTO;
import br.com.reserva.application.dto.ReservaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/reserva")
public class ReservaResource {

    private final EfetuaReservaController efetuaReserva;
    private final BuscaReservasController buscaReservas;
    private final AvaliaReservaController avaliaReserva;
    private final GerenciaReservaController gerenciaReserva;

    @Autowired
    public ReservaResource(EfetuaReservaController efetuaReserva,
                           BuscaReservasController buscaReservas,
                           AvaliaReservaController avaliaReserva,
                           GerenciaReservaController gerenciaReserva) {
        this.efetuaReserva = efetuaReserva;
        this.buscaReservas = buscaReservas;
        this.avaliaReserva = avaliaReserva;
        this.gerenciaReserva = gerenciaReserva;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservaDTO> efetuaReserva(@Validated @RequestBody ReservaDTO reservaDTO) {
        ReservaDTO newReservaDTO = efetuaReserva.execute(reservaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReservaDTO);
    }

    @GetMapping(value = "/busca-cliente/{clienteEmail}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ReservaDTO>> buscaPorCliente(@PathVariable String clienteEmail, Pageable pageable) {
        Page<ReservaDTO> reservas = buscaReservas.porCliente(clienteEmail, pageable);
        return ResponseEntity.ok().body(reservas);
    }

    @GetMapping(value = "/busca-restaurante/{restauranteId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ReservaDTO>> buscaPorRestaurante(@PathVariable Long restauranteId, Pageable pageable) {
        Page<ReservaDTO> reservas = buscaReservas.porRestaurante(restauranteId, pageable);
        return ResponseEntity.ok().body(reservas);
    }

    @GetMapping(value = "/busca-cliente-periodo/{clienteEmail}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ReservaDTO>> buscaPorClientePeriodo(@PathVariable String clienteEmail, @RequestParam LocalDateTime inicio,
                                                                   @RequestParam LocalDateTime fim, Pageable pageable) {
        Page<ReservaDTO> reservas = buscaReservas.porClientePeriodo(clienteEmail, inicio, fim, pageable);
        return ResponseEntity.ok().body(reservas);
    }

    @GetMapping(value = "/busca-restaurante-periodo/{restauranteId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ReservaDTO>> buscaPorRestaurantePeriodo(@PathVariable Long restauranteId, @RequestParam LocalDateTime inicio,
                                                                       @RequestParam LocalDateTime fim, Pageable pageable) {
        Page<ReservaDTO> reservas = buscaReservas.porRestaurantePeriodo(restauranteId, inicio, fim, pageable);
        return ResponseEntity.ok().body(reservas);
    }

    @PostMapping(value = "/{reservaId}/avalia", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> avaliaReserva(@PathVariable Long reservaId, @Validated @RequestBody AvaliacaoDTO avaliacaoDTO) {
        avaliaReserva.execute(reservaId, avaliacaoDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservaDTO> gerenciaReserva(@PathVariable Long id, @Validated @RequestBody ReservaDTO reservaDTO) {
        ReservaDTO newReservaDTO = gerenciaReserva.execute(id, reservaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReservaDTO);
    }

}
