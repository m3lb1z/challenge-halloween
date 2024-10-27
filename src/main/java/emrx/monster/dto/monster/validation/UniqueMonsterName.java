package emrx.monster.dto.monster.validation;

import emrx.monster.dto.monster.MonsterDTO;
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
        if(monsterRepository.existsByNameIgnoreCase(monsterDTO.getName())) {
            throw new ValidationException("Monster with name " + monsterDTO.getName() + " already exists");
        }
    }
}
