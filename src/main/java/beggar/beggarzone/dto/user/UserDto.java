package beggar.beggarzone.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
}
