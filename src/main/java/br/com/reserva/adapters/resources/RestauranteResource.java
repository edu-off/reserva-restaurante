package br.com.reserva.adapters.resources;

import br.com.reserva.adapters.controllers.BuscaRestaurantesController;
import br.com.reserva.adapters.controllers.CadastraMesaController;
import br.com.reserva.adapters.controllers.CadastraRestauranteController;
import br.com.reserva.application.dto.MesaDTO;
import br.com.reserva.application.dto.RestauranteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurante")
public class RestauranteResource {

    private final CadastraRestauranteController cadastraRestaurante;
    private final BuscaRestaurantesController buscaRestaurante;
    private final CadastraMesaController cadastraMesaController;

    @Autowired
    public RestauranteResource(CadastraRestauranteController cadastraRestaurante,
                               BuscaRestaurantesController buscaRestaurante,
                               CadastraMesaController cadastraMesaController) {
        this.cadastraRestaurante = cadastraRestaurante;
        this.buscaRestaurante = buscaRestaurante;
        this.cadastraMesaController = cadastraMesaController;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestauranteDTO> cadastraRestaurante(@Validated @RequestBody RestauranteDTO restauranteDTO) {
        RestauranteDTO newRestauranteDTO = cadastraRestaurante.execute(restauranteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRestauranteDTO);
    }

    @GetMapping(value = "/busca-nome/{nome}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<RestauranteDTO>> buscaRestaurantePorNome(@PathVariable String nome, Pageable pageable) {
        Page<RestauranteDTO> restaurantes = buscaRestaurante.porNome(nome, pageable);
        return ResponseEntity.ok().body(restaurantes);
    }

    @GetMapping(value = "/busca-culinaria/{culinaria}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<RestauranteDTO>> buscaRestaurantePorCulinaria(@PathVariable String culinaria, Pageable pageable) {
        Page<RestauranteDTO> restaurantes = buscaRestaurante.porCulinaria(culinaria, pageable);
        return ResponseEntity.ok().body(restaurantes);
    }

    @PostMapping(value = "{restauranteId}/mesa", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MesaDTO> cadastraMesa(@PathVariable Long restauranteId, @Validated @RequestBody MesaDTO mesaDTO) {
        MesaDTO newMesaDTO = cadastraMesaController.execute(restauranteId, mesaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMesaDTO);
    }

}
