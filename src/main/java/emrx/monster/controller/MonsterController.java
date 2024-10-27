package emrx.monster.controller;

import com.fasterxml.jackson.annotation.JsonView;
import emrx.monster.dto.MonsterDTO;
import emrx.monster.dto.Views;
import emrx.monster.mapper.AppearanceMapper;
import emrx.monster.mapper.MonsterMapper;
import emrx.monster.model.Monster;
import emrx.monster.service.MonsterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @JsonView(Views.Full.class)
    public ResponseEntity<MonsterDTO> getMonster(@PathVariable Long id) {
        Monster monster = monsterService.getMonsterFullById(id);
        if (monster != null) {
            return new ResponseEntity<>(monsterMapper.toDTO(monster), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @JsonView(Views.Create.class)
    public ResponseEntity<MonsterDTO> createMonster(@RequestBody @JsonView(Views.Create.class) MonsterDTO monsterDTO) {
        Monster createdMonster = monsterService.createMonster(monsterDTO);
        return new ResponseEntity<>(monsterMapper.toDTO(createdMonster), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @JsonView(Views.Full.class)
    public ResponseEntity<MonsterDTO> updateMonster(@PathVariable Long id, @RequestBody @JsonView(Views.Full.class) MonsterDTO monsterDTO) {
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
    public ResponseEntity<List<MonsterDTO>> getMonstersByType(@PathVariable String type) {
        List<Monster> monsters = monsterService.getMonstersByType(type);
        return new ResponseEntity<>(monsterMapper.toDTOList(monsters), HttpStatus.OK);
    }

    @GetMapping("/danger/{level}")
    public ResponseEntity<List<MonsterDTO>> getDangerousMonsters(@PathVariable int level) {
        List<Monster> monsters = monsterService.getDangerousMonsters(level);
        return new ResponseEntity<>(monsterMapper.toDTOList(monsters), HttpStatus.OK);
    }
}
