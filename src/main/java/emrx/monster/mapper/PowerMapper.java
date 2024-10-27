package emrx.monster.mapper;

import emrx.monster.dto.PowerDTO;
import emrx.monster.model.Power;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PowerMapper {

    private final ModelMapper modelMapper;

    public PowerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PowerDTO toDTO(Power power) {
        return modelMapper.map(power, PowerDTO.class);
    }

    public Power toEntity(PowerDTO powerDTO) {
        return modelMapper.map(powerDTO, Power.class);
    }

    public List<PowerDTO> toDTOList(List<Power> powers) {
        return powers.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Power> toEntityList(List<PowerDTO> powerDTOs) {
        return powerDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
