package emrx.monster.controller;

import com.fasterxml.jackson.annotation.JsonView;
import emrx.monster.dto.AppearanceDTO;
import emrx.monster.dto.monster.MonsterDTO;
import emrx.monster.dto.monster.MonsterPage;
import emrx.monster.dto.monster.Views;
import emrx.monster.dto.monster.validation.OnCreate;
import emrx.monster.dto.monster.validation.OnUpdate;
import emrx.monster.mapper.AppearanceMapper;
import emrx.monster.mapper.MonsterMapper;
import emrx.monster.model.Appearance;
import emrx.monster.model.Monster;
import emrx.monster.service.MonsterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/monsters")
public class MonsterController {

    private final MonsterService monsterService;

    private final MonsterMapper monsterMapper;

    private final AppearanceMapper appearanceMapper;

    public MonsterController(MonsterService monsterService, MonsterMapper monsterMapper, AppearanceMapper appearanceMapper) {
        this.monsterService = monsterService;
        this.monsterMapper = monsterMapper;
        this.appearanceMapper = appearanceMapper;
    }

    @Operation(summary = "Obtener todos los monstruos", description = "Obtiene una lista paginada de todos los monstruos. Los parámetros de la request permiten paginar, definir el tamaño de la página y ordenar los resultados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "204", description = "No hay contenido")
    })
    @GetMapping
    public ResponseEntity<Page<MonsterPage>> getAllMonsters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<Monster> monsters = monsterService.getAllMonsters(page, size, sortBy);
        if (monsters.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Page<MonsterPage> monsterDTOs = monsterMapper.toPageDTO(monsters);
        return new ResponseEntity<>(monsterDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un monstruo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Monstruo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MonsterDTO> getMonster(@PathVariable Long id) {
        Monster monster = monsterService.getMonsterFullById(id);
        if (monster != null) {
            return new ResponseEntity<>(monsterMapper.toDTO(monster), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Crear un nuevo monstruo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Monstruo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    @JsonView(Views.Create.class)
    public ResponseEntity<MonsterDTO> createMonster(
            @Validated(OnCreate.class)
            @RequestBody @JsonView(Views.Create.class) MonsterDTO monsterDTO,
            UriComponentsBuilder uriComponentsBuilder) {
        Monster createdMonster = monsterService.createMonster(monsterDTO);
        URI location = uriComponentsBuilder.path("/monsters/{id}")
                .buildAndExpand(createdMonster.getId())
                .toUri();
        return ResponseEntity.created(location).body(monsterMapper.toDTO(createdMonster));
    }

    @Operation(summary = "Actualizar un monstruo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monstruo actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Monstruo no encontrado")
    })
    @PutMapping("/{id}")
    @JsonView(Views.Update.class)
    public ResponseEntity<MonsterDTO> updateMonster(
            @PathVariable Long id,
            @RequestBody @JsonView(Views.Update.class) MonsterDTO monsterDTO) {
        Monster updatedMonster = monsterService.updateMonster(id, monsterDTO);
        if (updatedMonster != null) {
            return new ResponseEntity<>(monsterMapper.toDTO(updatedMonster), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Eliminar un monstruo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Monstruo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Monstruo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonster(@PathVariable Long id) {
        monsterService.deleteMonster(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Obtener monstruos por tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping("/type/{type}")
    @JsonView(Views.Basic.class)
    public ResponseEntity<List<MonsterDTO>> getMonstersByType(@PathVariable String type) {
        List<Monster> monsters = monsterService.getMonstersByType(type);
        return new ResponseEntity<>(monsterMapper.toDTOList(monsters), HttpStatus.OK);
    }

    @Operation(summary = "Obtener monstruos peligrosos por nivel de peligro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping("/danger/{level}")
    @JsonView(Views.Basic.class)
    public ResponseEntity<List<MonsterDTO>> getDangerousMonsters(@PathVariable int level) {
        List<Monster> monsters = monsterService.getDangerousMonsters(level);
        return new ResponseEntity<>(monsterMapper.toDTOList(monsters), HttpStatus.OK);
    }

    // Methods Monster By Appearance

    @Operation(summary = "Obtener apariciones de un monstruo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Monstruo no encontrado")
    })
    @GetMapping("/{monsterId}/appearances")
    public ResponseEntity<List<AppearanceDTO>> getAppearancesByMonsterId(@PathVariable Long monsterId) {
        List<Appearance> appearances = monsterService.getAppearancesByMonsterId(monsterId);
        return new ResponseEntity<>(appearanceMapper.toDTOList(appearances), HttpStatus.OK);
    }

    @Operation(summary = "Obtener una aparición de un monstruo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Aparición no encontrada")
    })
    @GetMapping("/{monsterId}/appearances/{id}")
    public ResponseEntity<AppearanceDTO> getAppearanceById(@PathVariable Long monsterId, @PathVariable Long id) {
        Appearance appearance = monsterService.getAppearanceById(monsterId, id);
        if (appearance != null) {
            return new ResponseEntity<>(appearanceMapper.toDTO(appearance), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Crear una nueva aparición para un monstruo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aparición creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping("/{monsterId}/appearances")
    public ResponseEntity<AppearanceDTO> createAppearance(
            @PathVariable Long monsterId,
            @RequestBody @Valid AppearanceDTO appearanceDTO,
            UriComponentsBuilder uriComponentsBuilder) {
        Appearance createdAppearance = monsterService.createAppearance(monsterId, appearanceDTO);
        URI location = uriComponentsBuilder.path("/monsters/{monsterId}/appearances/{id}")
                .buildAndExpand(monsterId, createdAppearance.getId())
                .toUri();
        return ResponseEntity.created(location).body(appearanceMapper.toDTO(createdAppearance));
    }

    @Operation(summary = "Actualizar una aparición de un monstruo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aparición actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Aparición no encontrada")
    })
    @PutMapping("/{monsterId}/appearances/{id}")
    public ResponseEntity<AppearanceDTO> updateAppearance(@PathVariable Long monsterId, @PathVariable Long id, @RequestBody AppearanceDTO appearanceDTO) {
        Appearance updatedAppearance = monsterService.updateAppearance(monsterId, id, appearanceDTO);
        if (updatedAppearance != null) {
            return new ResponseEntity<>(appearanceMapper.toDTO(updatedAppearance), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Eliminar una aparición de un monstruo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aparición eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Aparición no encontrada")
    })
    @DeleteMapping("/{monsterId}/appearances/{id}")
    public ResponseEntity<Void> deleteAppearance(@PathVariable Long monsterId, @PathVariable Long id) {
        monsterService.deleteAppearance(monsterId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
