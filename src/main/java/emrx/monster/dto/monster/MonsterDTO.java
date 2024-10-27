package emrx.monster.dto.monster;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import emrx.monster.dto.AppearanceDTO;
import emrx.monster.dto.monster.validation.OnCreate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonsterDTO {

    @JsonView(Views.Basic.class)
    private Long id;

    @JsonView(Views.Basic.class)
    @NotEmpty(groups = OnCreate.class)
    private String name;

    @JsonView(Views.Basic.class)
    @NotEmpty(groups = OnCreate.class)
    private String type;

    @JsonView(Views.Basic.class)
    @Range(groups = OnCreate.class, min = 1, max = 10)
    private int dangerLevel;

    @JsonView(Views.Create.class)
    @NotNull(groups = OnCreate.class)
    private List<String> weaknesses;

    @JsonView(Views.Create.class)
    private List<String> powers;

    @JsonView
    private List<AppearanceDTO> appearances;

    @JsonView(Views.Basic.class)
    @NotEmpty(groups = OnCreate.class)
    private String habitat;

    @JsonView(Views.Basic.class)
    @NotEmpty(groups = OnCreate.class)
    private String description;

    @JsonView(Views.Basic.class)
    @NotEmpty(groups = OnCreate.class)
    private String imageUrl;

    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime updatedAt;
}