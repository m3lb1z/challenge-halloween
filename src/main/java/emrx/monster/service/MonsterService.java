package emrx.monster.service;

import emrx.monster.dto.AppearanceDTO;
import emrx.monster.dto.monster.MonsterDTO;
import emrx.monster.dto.monster.validation.MonsterValidation;
import emrx.monster.infra.exception.ValidationIntegrity;
import emrx.monster.mapper.AppearanceMapper;
import emrx.monster.mapper.MonsterMapper;
import emrx.monster.model.Appearance;
import emrx.monster.model.Monster;
import emrx.monster.model.Power;
import emrx.monster.model.Weakness;
import emrx.monster.repository.AppearanceRepository;
import emrx.monster.repository.MonsterRepository;
import emrx.monster.repository.PowerRepository;
import emrx.monster.repository.WeaknessRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MonsterService {
    private final MonsterRepository monsterRepository;
    private final WeaknessRepository weaknessRepository;
    private final PowerRepository powerRepository;
    private final AppearanceRepository appearanceRepository;

    private final MonsterMapper monsterMapper;
    private final AppearanceMapper appearanceMapper;

    private final List<MonsterValidation> validations;

    public MonsterService(MonsterRepository monsterRepository, WeaknessRepository weaknessRepository, PowerRepository powerRepository, AppearanceRepository appearanceRepository, MonsterMapper monsterMapper, AppearanceMapper appearanceMapper, List<MonsterValidation> validations) {
        this.monsterRepository = monsterRepository;
        this.weaknessRepository = weaknessRepository;
        this.powerRepository = powerRepository;
        this.appearanceRepository = appearanceRepository;
        this.monsterMapper = monsterMapper;
        this.appearanceMapper = appearanceMapper;
        this.validations = validations;
    }

    public Page<Monster> getAllMonsters(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return monsterRepository.findAll(pageable);
    }

    public Monster getMonsterById(Long id) {
        return monsterRepository.findById(id).orElse(null);
    }

    public Monster getMonsterFullById(Long id) {
        Monster monster = monsterRepository.findById(id).orElse(null);
        if (monster != null) {
            Set<Weakness> weaknesses = weaknessRepository.findAllByMonsterId(id);
            Set<Power> powers = powerRepository.findAllByMonsterId(id);
            List<Appearance> appearances = appearanceRepository.findAllByMonsterId(id);
            monster.setWeaknesses(weaknesses);
            monster.setPowers(powers);
            monster.setAppearances(appearances);

            return monster;
        }
        return null;
    }

    public Monster createMonster(MonsterDTO monsterDTO) {
        validations.forEach(validation -> validation.validate(monsterDTO));
        Monster monster = monsterMapper.toEntity(monsterDTO);
        return monsterRepository.save(monster);
    }

    public Monster updateMonster(Long id, MonsterDTO monsterDTO) {
        monsterDTO.setId(id);
        validations.forEach(validation -> validation.validate(monsterDTO));
        Monster updatingMonster = monsterMapper.toEntity(monsterDTO);
        Monster existingMonster = monsterRepository.findById(id).orElse(null);
        if (existingMonster != null) {
            if (updatingMonster.getName() != null && !updatingMonster.getName().isBlank()) {
                existingMonster.setName(updatingMonster.getName());
            }
            if (updatingMonster.getType() != null && !updatingMonster.getType().isBlank()) {
                existingMonster.setType(updatingMonster.getType());
            }
            if(updatingMonster.getDangerLevel() != 0) {
                if (updatingMonster.getDangerLevel() > 0 && updatingMonster.getDangerLevel() <= 10) {
                    existingMonster.setDangerLevel(updatingMonster.getDangerLevel());
                } else {
                    throw new ValidationIntegrity("El nivel de peligro debe ser entre 1 y 10");
                }
            }
            if (updatingMonster.getHabitat() != null && !updatingMonster.getHabitat().isBlank()) {
                existingMonster.setHabitat(updatingMonster.getHabitat());
            }
            if (updatingMonster.getDescription() != null && !updatingMonster.getDescription().isBlank()) {
                existingMonster.setDescription(updatingMonster.getDescription());
            }
            if (updatingMonster.getImageUrl() != null && !updatingMonster.getImageUrl().isBlank()) {
                existingMonster.setImageUrl(updatingMonster.getImageUrl());
            }
            if(updatingMonster.getWeaknesses() != null) {
                existingMonster.setWeaknesses(updatingMonster.getWeaknesses());
            }
            if(updatingMonster.getPowers() != null) {
                existingMonster.setPowers(updatingMonster.getPowers());
            }

            return monsterRepository.save(existingMonster);
        }
        return null;
    }

    public void deleteMonster(Long id) {
        monsterRepository.deleteById(id);
    }

    public List<Monster> getMonstersByType(String type) {
        return monsterRepository.findByType(type);
    }

    public List<Monster> getDangerousMonsters(int level) {
        return monsterRepository.findByDangerLevelGreaterThanEqual(level);
    }

    // Methods Monster By Appearance

    public List<Appearance> getAppearancesByMonsterId(Long monsterId) {
        return appearanceRepository.findAllByMonsterId(monsterId);
    }

    public Appearance getAppearanceById(Long monsterId, Long id) {
        return appearanceRepository.findByIdAndMonsterId(id, monsterId).orElse(null);
    }

    public Appearance createAppearance(Long monsterId, AppearanceDTO appearanceDTO) {
        Monster monster = monsterRepository.findById(monsterId).orElse(null);
        Appearance appearance = appearanceMapper.toEntity(appearanceDTO);
        appearance.setMonster(monster);
        return appearanceRepository.save(appearance);
    }

    public Appearance updateAppearance(Long monsterId, Long id, AppearanceDTO appearanceDTO) {
        Appearance existingAppearance = appearanceRepository.findByIdAndMonsterId(id, monsterId).orElse(null);
        if (existingAppearance != null) {
            Appearance updatingAppearance = appearanceMapper.toEntity(appearanceDTO);
            if (updatingAppearance.getLocation() != null && !updatingAppearance.getLocation().isBlank()) {
                existingAppearance.setLocation(updatingAppearance.getLocation());
            }
            if (updatingAppearance.getDate() != null) {
                existingAppearance.setDate(updatingAppearance.getDate());
            }
            if (updatingAppearance.getWitnesses() > 0 && updatingAppearance.getWitnesses() <= 10) {
                existingAppearance.setWitnesses(updatingAppearance.getWitnesses());
            }

            return appearanceRepository.save(existingAppearance);
        }
        return null;
    }

    public void deleteAppearance(Long monsterId, Long id) {
        appearanceRepository.deleteByIdAndMonsterId(id, monsterId);
    }
}
