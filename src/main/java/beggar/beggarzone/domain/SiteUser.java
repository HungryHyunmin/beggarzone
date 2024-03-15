package beggar.beggarzone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Long으로 하는 이유-> int와long은 원시타입이라 null값을 가지지 못한다.
    //하지만 Wrapper클래스인 Integer과 ,Long타입은 null을 가질 수 있다.
    //JPA에서 권장
    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Override
    public String toString() {
        return "SiteUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
