package emrx.monster.dto.monster.validation;

import emrx.monster.dto.monster.MonsterDTO;
import emrx.monster.model.Power;
import emrx.monster.repository.PowerRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreatePowerIfNotExist implements MonsterValidation {

    private final PowerRepository powerRepository;

    public CreatePowerIfNotExist(PowerRepository powerRepository) {
        this.powerRepository = powerRepository;
    }

    @Override
    public void validate(MonsterDTO monsterDTO) {
        if(monsterDTO.getPowers() == null || monsterDTO.getPowers().isEmpty()) return;

        List<String> powers = monsterDTO.getPowers();

        for (String power : powers) {
            if (!powerRepository.existsByNameIgnoreCase(power)) {
                powerRepository.save(new Power(power));
            }
        }
    }
}

