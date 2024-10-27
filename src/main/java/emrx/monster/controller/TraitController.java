package emrx.monster.controller;

import emrx.monster.dto.PowerDTO;
import emrx.monster.dto.WeaknessDTO;
import emrx.monster.service.TraitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/traits")
public class TraitController {

    private final TraitService traitService;

    public TraitController(TraitService traitService) {
        this.traitService = traitService;
    }

    // Endpoints para Power

    @Operation(summary = "Obtener todos los poderes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "204", description = "No hay contenido")
    })
    @GetMapping("/powers")
    public ResponseEntity<List<PowerDTO>> getAllPowers() {
        List<PowerDTO> powers = traitService.getAllPowers();
        if (powers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(powers, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un poder por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Poder no encontrado")
    })
    @GetMapping("/powers/{id}")
    public ResponseEntity<PowerDTO> getPowerById(@PathVariable Long id) {
        PowerDTO power = traitService.getPowerById(id);
        if (power != null) {
            return new ResponseEntity<>(power, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Crear un nuevo poder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Poder creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping("/powers")
    public ResponseEntity<PowerDTO> createPower(@RequestBody PowerDTO powerDTO, UriComponentsBuilder uriComponentsBuilder) {
        PowerDTO createdPower = traitService.createPower(powerDTO);
        URI location = uriComponentsBuilder.path("/traits/powers/{id}")
                .buildAndExpand(createdPower.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdPower);
    }

    @Operation(summary = "Actualizar un poder por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Poder actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Poder no encontrado")
    })
    @PutMapping("/powers/{id}")
    public ResponseEntity<PowerDTO> updatePower(@PathVariable Long id, @RequestBody PowerDTO powerDTO) {
        PowerDTO updatedPower = traitService.updatePower(id, powerDTO);
        if (updatedPower != null) {
            return new ResponseEntity<>(updatedPower, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Eliminar un poder por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Poder eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Poder no encontrado")
    })
    @DeleteMapping("/powers/{id}")
    public ResponseEntity<Void> deletePower(@PathVariable Long id) {
        traitService.deletePower(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoints para Weakness

    @Operation(summary = "Obtener todas las debilidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "204", description = "No hay contenido")
    })
    @GetMapping("/weaknesses")
    public ResponseEntity<List<WeaknessDTO>> getAllWeaknesses() {
        List<WeaknessDTO> weaknesses = traitService.getAllWeaknesses();
        if (weaknesses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(weaknesses, HttpStatus.OK);
    }

    @Operation(summary = "Obtener una debilidad por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Debilidad no encontrada")
    })
    @GetMapping("/weaknesses/{id}")
    public ResponseEntity<WeaknessDTO> getWeaknessById(@PathVariable Long id) {
        WeaknessDTO weakness = traitService.getWeaknessById(id);
        if (weakness != null) {
            return new ResponseEntity<>(weakness, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Crear una nueva debilidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Debilidad creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping("/weaknesses")
    public ResponseEntity<WeaknessDTO> createWeakness(@RequestBody WeaknessDTO weaknessDTO, UriComponentsBuilder uriComponentsBuilder) {
        WeaknessDTO createdWeakness = traitService.createWeakness(weaknessDTO);
        URI location = uriComponentsBuilder.path("/traits/weaknesses/{id}")
                .buildAndExpand(createdWeakness.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdWeakness);
    }

    @Operation(summary = "Actualizar una debilidad por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Debilidad actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Debilidad no encontrada")
    })
    @PutMapping("/weaknesses/{id}")
    public ResponseEntity<WeaknessDTO> updateWeakness(@PathVariable Long id, @RequestBody WeaknessDTO weaknessDTO) {
        WeaknessDTO updatedWeakness = traitService.updateWeakness(id, weaknessDTO);
        if (updatedWeakness != null) {
            return new ResponseEntity<>(updatedWeakness, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Eliminar una debilidad por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Debilidad eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Debilidad no encontrada")
    })
    @DeleteMapping("/weaknesses/{id}")
    public ResponseEntity<Void> deleteWeakness(@PathVariable Long id) {
        traitService.deleteWeakness(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
