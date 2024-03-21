package beggar.beggarzone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data

@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private String email;

    public UserResponseDto(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserResponseDto(Long id){ //createuser
        this.id=id;
    }


}
