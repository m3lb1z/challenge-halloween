package emrx.monster.service;

import emrx.monster.dto.MonsterDTO;
import emrx.monster.dto.validation.MonsterValidation;
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
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Monster> getAllMonsters() {
        return monsterRepository.findAll();
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
        validations.forEach(validation -> validation.validate(monsterDTO));
        Monster updatingMonster = monsterMapper.toEntity(monsterDTO);
        System.out.println(updatingMonster.toString());
        Monster existingMonster = monsterRepository.findById(id).orElse(null);
        if (existingMonster != null) {
            existingMonster.setName(updatingMonster.getName());
            existingMonster.setType(updatingMonster.getType());
            existingMonster.setDangerLevel(updatingMonster.getDangerLevel());
            existingMonster.setHabitat(updatingMonster.getHabitat());
            existingMonster.setDescription(updatingMonster.getDescription());
            existingMonster.setImageUrl(updatingMonster.getImageUrl());
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
        return monsterRepository.findByDangerLevelGreaterThan(level);
    }
}