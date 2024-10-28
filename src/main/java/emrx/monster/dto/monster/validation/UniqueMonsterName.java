package emrx.monster.dto.monster.validation;

import emrx.monster.dto.monster.MonsterDTO;
import emrx.monster.model.Monster;
import emrx.monster.repository.MonsterRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class UniqueMonsterName implements MonsterValidation {

    private final MonsterRepository monsterRepository;

    public UniqueMonsterName(MonsterRepository monsterRepository) {
        this.monsterRepository = monsterRepository;
    }

    @Override
    public void validate(MonsterDTO monsterDTO) {
        Monster monster = monsterRepository.findByNameIgnoreCase(monsterDTO.getName()).orElse(null);
        if(monster != null && !monster.getId().equals(monsterDTO.getId())) {
            throw new ValidationException("El monstruo con el nombre " + monsterDTO.getName() + " ya existe.");
        }
    }
}
