package beggar.beggarzone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter@Setter
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reply_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime regDate;

    @ManyToOne //joincolum 사용안하면 디폴트 전략으로 외래키인 board_id 자동 지어
    private Board board;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany(fetch = FetchType.EAGER)
    //@다대다를 설정하면 새로운 테이블을 만들어(Reply_Voter) 관리한다
    Set<SiteUser> voter;
}
