package emrx.monster.service;

import emrx.monster.dto.PowerDTO;
import emrx.monster.dto.WeaknessDTO;
import emrx.monster.mapper.PowerMapper;
import emrx.monster.mapper.WeaknessMapper;
import emrx.monster.model.Power;
import emrx.monster.model.Weakness;
import emrx.monster.repository.PowerRepository;
import emrx.monster.repository.WeaknessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraitService {

    private final PowerRepository powerRepository;
    private final WeaknessRepository weaknessRepository;
    private final PowerMapper powerMapper;
    private final WeaknessMapper weaknessMapper;

    @Autowired
    public TraitService(PowerRepository powerRepository, WeaknessRepository weaknessRepository, PowerMapper powerMapper, WeaknessMapper weaknessMapper) {
        this.powerRepository = powerRepository;
        this.weaknessRepository = weaknessRepository;
        this.powerMapper = powerMapper;
        this.weaknessMapper = weaknessMapper;
    }

    // Métodos para Power

    public List<PowerDTO> getAllPowers() {
        return powerMapper.toDTOList(powerRepository.findAll());
    }

    public PowerDTO getPowerById(Long id) {
        return powerMapper.toDTO(powerRepository.findById(id).orElse(null));
    }

    public PowerDTO createPower(PowerDTO powerDTO) {
        Power power = powerMapper.toEntity(powerDTO);
        return powerMapper.toDTO(powerRepository.save(power));
    }

    public PowerDTO updatePower(Long id, PowerDTO powerDTO) {
        Power power = powerRepository.findById(id).orElse(null);
        if (power != null) {
            power.setName(powerDTO.getName());
            return powerMapper.toDTO(powerRepository.save(power));
        }
        return null;
    }

    public void deletePower(Long id) {
        powerRepository.deleteById(id);
    }

    // Métodos para Weakness

    public List<WeaknessDTO> getAllWeaknesses() {
        return weaknessMapper.toDTOList(weaknessRepository.findAll());
    }

    public WeaknessDTO getWeaknessById(Long id) {
        return weaknessMapper.toDTO(weaknessRepository.findById(id).orElse(null));
    }

    public WeaknessDTO createWeakness(WeaknessDTO weaknessDTO) {
        Weakness weakness = weaknessMapper.toEntity(weaknessDTO);
        return weaknessMapper.toDTO(weaknessRepository.save(weakness));
    }

    public WeaknessDTO updateWeakness(Long id, WeaknessDTO weaknessDTO) {
        Weakness weakness = weaknessRepository.findById(id).orElse(null);
        if (weakness != null) {
            weakness.setName(weaknessDTO.getName());
            return weaknessMapper.toDTO(weaknessRepository.save(weakness));
        }
        return null;
    }

    public void deleteWeakness(Long id) {
        weaknessRepository.deleteById(id);
    }
}
