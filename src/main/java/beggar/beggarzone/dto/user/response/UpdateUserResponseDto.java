package beggar.beggarzone.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserResponseDto {
        private Long id;
        private String username;
        private String password;
        private String email;
}
