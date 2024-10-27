package emrx.monster.controller;

import emrx.monster.dto.PowerDTO;
import emrx.monster.dto.WeaknessDTO;
import emrx.monster.service.TraitService;
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

    @GetMapping("/powers")
    public ResponseEntity<List<PowerDTO>> getAllPowers() {
        List<PowerDTO> powers = traitService.getAllPowers();
        if (powers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(powers, HttpStatus.OK);
    }

    @GetMapping("/powers/{id}")
    public ResponseEntity<PowerDTO> getPowerById(@PathVariable Long id) {
        PowerDTO power = traitService.getPowerById(id);
        if (power != null) {
            return new ResponseEntity<>(power, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/powers")
    public ResponseEntity<PowerDTO> createPower(@RequestBody PowerDTO powerDTO, UriComponentsBuilder uriComponentsBuilder) {
        PowerDTO createdPower = traitService.createPower(powerDTO);
        URI location = uriComponentsBuilder.path("/traits/powers/{id}")
                .buildAndExpand(createdPower.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdPower);
    }

    @PutMapping("/powers/{id}")
    public ResponseEntity<PowerDTO> updatePower(@PathVariable Long id, @RequestBody PowerDTO powerDTO) {
        PowerDTO updatedPower = traitService.updatePower(id, powerDTO);
        if (updatedPower != null) {
            return new ResponseEntity<>(updatedPower, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/powers/{id}")
    public ResponseEntity<Void> deletePower(@PathVariable Long id) {
        traitService.deletePower(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoints para Weakness

    @GetMapping("/weaknesses")
    public ResponseEntity<List<WeaknessDTO>> getAllWeaknesses() {
        List<WeaknessDTO> weaknesses = traitService.getAllWeaknesses();
        if (weaknesses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(weaknesses, HttpStatus.OK);
    }

    @GetMapping("/weaknesses/{id}")
    public ResponseEntity<WeaknessDTO> getWeaknessById(@PathVariable Long id) {
        WeaknessDTO weakness = traitService.getWeaknessById(id);
        if (weakness != null) {
            return new ResponseEntity<>(weakness, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/weaknesses")
    public ResponseEntity<WeaknessDTO> createWeakness(@RequestBody WeaknessDTO weaknessDTO, UriComponentsBuilder uriComponentsBuilder) {
        WeaknessDTO createdWeakness = traitService.createWeakness(weaknessDTO);
        URI location = uriComponentsBuilder.path("/traits/weaknesses/{id}")
                .buildAndExpand(createdWeakness.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdWeakness);
    }

    @PutMapping("/weaknesses/{id}")
    public ResponseEntity<WeaknessDTO> updateWeakness(@PathVariable Long id, @RequestBody WeaknessDTO weaknessDTO) {
        WeaknessDTO updatedWeakness = traitService.updateWeakness(id, weaknessDTO);
        if (updatedWeakness != null) {
            return new ResponseEntity<>(updatedWeakness, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/weaknesses/{id}")
    public ResponseEntity<Void> deleteWeakness(@PathVariable Long id) {
        traitService.deleteWeakness(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
