package emrx.monster.controller;

import com.fasterxml.jackson.annotation.JsonView;
import emrx.monster.dto.AppearanceDTO;
import emrx.monster.dto.monster.MonsterDTO;
import emrx.monster.dto.monster.Views;
import emrx.monster.dto.monster.validation.OnCreate;
import emrx.monster.mapper.AppearanceMapper;
import emrx.monster.mapper.MonsterMapper;
import emrx.monster.model.Appearance;
import emrx.monster.model.Monster;
import emrx.monster.service.MonsterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monsters")
public class MonsterController {

    private final MonsterService monsterService;

    private final MonsterMapper monsterMapper;

    private final AppearanceMapper appearanceMapper;

    public MonsterController(MonsterService monsterService, MonsterMapper monsterMapper, AppearanceMapper appearanceMapper) {
        this.monsterService = monsterService;
        this.monsterMapper = monsterMapper;
        this.appearanceMapper = appearanceMapper;
    }

    @GetMapping
    @JsonView(Views.Basic.class)
    public ResponseEntity<List<MonsterDTO>> getAllMonsters() {
        List<Monster> monsters = monsterService.getAllMonsters();
        return new ResponseEntity<>(monsterMapper.toDTOList(monsters), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonsterDTO> getMonster(@PathVariable Long id) {
        Monster monster = monsterService.getMonsterFullById(id);
        if (monster != null) {
            return new ResponseEntity<>(monsterMapper.toDTO(monster), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @JsonView(Views.Create.class)
    public ResponseEntity<MonsterDTO> createMonster(
            @Validated(OnCreate.class)
            @RequestBody @JsonView(Views.Create.class) MonsterDTO monsterDTO) {
        Monster createdMonster = monsterService.createMonster(monsterDTO);
        return new ResponseEntity<>(monsterMapper.toDTO(createdMonster), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonsterDTO> updateMonster(@PathVariable Long id, @RequestBody MonsterDTO monsterDTO) {
        Monster updatedMonster = monsterService.updateMonster(id, monsterDTO);
        if (updatedMonster != null) {
            return new ResponseEntity<>(monsterMapper.toDTO(updatedMonster), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonster(@PathVariable Long id) {
        monsterService.deleteMonster(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/type/{type}")
    @JsonView(Views.Basic.class)
    public ResponseEntity<List<MonsterDTO>> getMonstersByType(@PathVariable String type) {
        List<Monster> monsters = monsterService.getMonstersByType(type);
        return new ResponseEntity<>(monsterMapper.toDTOList(monsters), HttpStatus.OK);
    }

    @GetMapping("/danger/{level}")
    @JsonView(Views.Basic.class)
    public ResponseEntity<List<MonsterDTO>> getDangerousMonsters(@PathVariable int level) {
        List<Monster> monsters = monsterService.getDangerousMonsters(level);
        return new ResponseEntity<>(monsterMapper.toDTOList(monsters), HttpStatus.OK);
    }

    // Methods Monster By Appearance

    @GetMapping("/{monsterId}/appearances")
    public ResponseEntity<List<AppearanceDTO>> getAppearancesByMonsterId(@PathVariable Long monsterId) {
        List<Appearance> appearances = monsterService.getAppearancesByMonsterId(monsterId);
        return new ResponseEntity<>(appearanceMapper.toDTOList(appearances), HttpStatus.OK);
    }

    @GetMapping("/{monsterId}/appearances/{id}")
    public ResponseEntity<AppearanceDTO> getAppearanceById(@PathVariable Long monsterId, @PathVariable Long id) {
        Appearance appearance = monsterService.getAppearanceById(monsterId, id);
        if (appearance != null) {
            return new ResponseEntity<>(appearanceMapper.toDTO(appearance), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{monsterId}/appearances")
    public ResponseEntity<AppearanceDTO> createAppearance(@PathVariable Long monsterId, @RequestBody @Valid AppearanceDTO appearanceDTO) {
        Appearance createdAppearance = monsterService.createAppearance(monsterId, appearanceDTO);
        return new ResponseEntity<>(appearanceMapper.toDTO(createdAppearance), HttpStatus.CREATED);
    }

    @PutMapping("/{monsterId}/appearances/{id}")
    public ResponseEntity<AppearanceDTO> updateAppearance(@PathVariable Long monsterId, @PathVariable Long id, @RequestBody AppearanceDTO appearanceDTO) {
        Appearance updatedAppearance = monsterService.updateAppearance(monsterId, id, appearanceDTO);
        if (updatedAppearance != null) {
            return new ResponseEntity<>(appearanceMapper.toDTO(updatedAppearance), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{monsterId}/appearances/{id}")
    public ResponseEntity<Void> deleteAppearance(@PathVariable Long monsterId, @PathVariable Long id) {
        monsterService.deleteAppearance(monsterId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
