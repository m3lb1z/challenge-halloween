package emrx.monster.dto.monster.validation;

import emrx.monster.dto.monster.MonsterDTO;
import emrx.monster.model.Weakness;
import emrx.monster.repository.WeaknessRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateWeaknessIfNotExist implements MonsterValidation {

    private final WeaknessRepository weaknessRepository;

    public CreateWeaknessIfNotExist(WeaknessRepository weaknessRepository) {
        this.weaknessRepository = weaknessRepository;
    }

    @Override
    public void validate(MonsterDTO monsterDTO) {
        if(monsterDTO.getWeaknesses() == null || monsterDTO.getWeaknesses().isEmpty()) return;

        List<String> weaknesses = monsterDTO.getWeaknesses();
        for (String weakness : weaknesses) {
            if (!weaknessRepository.existsByNameIgnoreCase(weakness)) {
                weaknessRepository.save(new Weakness(weakness));
            }
        }
    }
}
