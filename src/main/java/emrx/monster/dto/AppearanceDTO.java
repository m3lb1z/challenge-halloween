package emrx.monster.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppearanceDTO {

    private Long id;
    @NotBlank
    private String location;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @Min(1)
    private int witnesses;

}