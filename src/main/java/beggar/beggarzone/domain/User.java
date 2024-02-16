package beggar.beggarzone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id @GeneratedValue
    @Column(name="user_id")
    private Integer id;

    private String password;
    private String email;
    private String nickname;
    private String photo;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();
}
