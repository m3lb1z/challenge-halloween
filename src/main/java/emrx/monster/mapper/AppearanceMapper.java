package emrx.monster.mapper;

import emrx.monster.dto.AppearanceDTO;
import emrx.monster.model.Appearance;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppearanceMapper {

    private final ModelMapper modelMapper;

    public AppearanceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AppearanceDTO toDTO(Appearance appearance) {
        return modelMapper.map(appearance, AppearanceDTO.class);
    }

    public Appearance toEntity(AppearanceDTO appearanceDTO) {
        return modelMapper.map(appearanceDTO, Appearance.class);
    }

    public List<AppearanceDTO> toDTOList(List<Appearance> appearances) {
        return appearances.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Appearance> toEntityList(List<AppearanceDTO> appearanceDTOs) {
        return appearanceDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
