package beggar.beggarzone.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateUserResponseDto {
    private Long id;//createuser
      public  CreateUserResponseDto(Long id){
        this.id=id;
    }
}
