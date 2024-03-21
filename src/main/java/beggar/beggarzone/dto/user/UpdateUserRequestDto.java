package beggar.beggarzone.dto.user;

import lombok.Data;

@Data
public class UpdateUserRequestDto {

        private String username;
        private String password;
        private String email;
    }
