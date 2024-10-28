package emrx.monster.dto.monster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonsterPage {

    private Long id;
    private String name;
    private String type;
    private int dangerLevel;
    private String habitat;
    private String description;
    private String imageUrl;

}
