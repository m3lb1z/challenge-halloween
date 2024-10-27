package emrx.monster.mapper;

import emrx.monster.dto.WeaknessDTO;
import emrx.monster.model.Weakness;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeaknessMapper {

    private final ModelMapper modelMapper;

    public WeaknessMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public WeaknessDTO toDTO(Weakness weakness) {
        return modelMapper.map(weakness, WeaknessDTO.class);
    }

    public Weakness toEntity(WeaknessDTO weaknessDTO) {
        return modelMapper.map(weaknessDTO, Weakness.class);
    }

    public List<WeaknessDTO> toDTOList(List<Weakness> weaknesses) {
        return weaknesses.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Weakness> toEntityList(List<WeaknessDTO> weaknessDTOs) {
        return weaknessDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
