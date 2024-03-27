package beggar.beggarzone.dto;

import beggar.beggarzone.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardHashtagQueryDto {
    private Long id;
    private String tagName;


}
