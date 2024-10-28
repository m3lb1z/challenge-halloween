package emrx.monster.mapper;

import emrx.monster.dto.AppearanceDTO;
import emrx.monster.dto.monster.MonsterDTO;
import emrx.monster.dto.monster.MonsterPage;
import emrx.monster.model.Monster;
import emrx.monster.model.Power;
import emrx.monster.model.Weakness;
import emrx.monster.repository.PowerRepository;
import emrx.monster.repository.WeaknessRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MonsterMapper {

    private final ModelMapper modelMapper;
    private final WeaknessRepository weaknessRepository;
    private final PowerRepository powerRepository;

    public MonsterMapper(ModelMapper modelMapper, WeaknessRepository weaknessRepository, PowerRepository powerRepository) {
        this.modelMapper = modelMapper;
        this.weaknessRepository = weaknessRepository;
        this.powerRepository = powerRepository;
    }

    public MonsterDTO toDTO(Monster monster) {
        MonsterDTO monsterDTO = modelMapper.map(monster, MonsterDTO.class);
        if (monster.getPowers() != null) {
            List<String> powerNames = monster.getPowers().stream()
                    .map(Power::getName)
                    .collect(Collectors.toList());
            monsterDTO.setPowers(powerNames);
        }

        if (monster.getWeaknesses() != null) {
            List<String> weaknessNames = monster.getWeaknesses().stream()
                    .map(Weakness::getName)
                    .collect(Collectors.toList());
            monsterDTO.setWeaknesses(weaknessNames);
        }
        if (monster.getAppearances() != null) {
            List<AppearanceDTO> appearances = monster.getAppearances().stream()
                    .map(appearance -> modelMapper.map(appearance, AppearanceDTO.class))
                    .toList();
            monsterDTO.setAppearances(appearances);
        }

        return monsterDTO;
    }
    public Monster toEntity(MonsterDTO monsterDTO) {
        Monster monster = modelMapper.map(monsterDTO, Monster.class);

        if(monsterDTO.getWeaknesses() != null) {
            Set<Weakness> weaknesses = new HashSet<>();
            for (String weakness : monsterDTO.getWeaknesses()) {
                Weakness existingWeakness = weaknessRepository.findByNameIgnoreCase(weakness).orElse(null);
                weaknesses.add(existingWeakness);
            }
            monster.setWeaknesses(weaknesses);
        }
        if(monsterDTO.getPowers() != null) {
            Set<Power> powers = new HashSet<>();
            for (String power : monsterDTO.getPowers()) {
                Power existingPower = powerRepository.findByNameIgnoreCase(power).orElse(null);
                powers.add(existingPower);
            }
            monster.setPowers(powers);
        }

        return monster;
    }

    public List<MonsterDTO> toDTOList(List<Monster> monsters) {
        return monsters.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Monster> toEntityList(List<MonsterDTO> monsterDTOs) {
        return monsterDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public Page<MonsterPage> toPageDTO(Page<Monster> monsters) {
        return monsters.map(monster -> modelMapper.map(monster, MonsterPage.class));
    }

}
