package beggar.beggarzone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reply_id")
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime regDate;

    @ManyToOne
    private Board board;
}
