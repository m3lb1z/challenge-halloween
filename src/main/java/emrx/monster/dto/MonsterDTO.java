package emrx.monster.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String name;
    @JsonView(Views.Basic.class)
    private String type;
    @JsonView(Views.Basic.class)
    private int dangerLevel;
    @JsonView({Views.Full.class, Views.Create.class})
    private List<String> weaknesses;
    @JsonView({Views.Full.class, Views.Create.class})
    private List<String> powers;
    @JsonView(Views.Full.class)
    private List<AppearanceDTO> appearances;
    @JsonView(Views.Basic.class)
    private String habitat;
    @JsonView(Views.Basic.class)
    private String description;
    @JsonView(Views.Basic.class)
    private String imageUrl;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;
}