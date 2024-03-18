package beggar.beggarzone.domain;

import beggar.beggarzone.CommonUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;

    @Column(length = 100) //varchar(100) 기본값 255
    private String title;

    @Column(columnDefinition = "TEXT") //TEXT 타입 ->
    private String content;

    private LocalDateTime regDate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Reply> replyList; // 다대일 양방향

    @ManyToOne
    private SiteUser author; // 다대일 단방향

    private LocalDateTime modifyDate;

    @ManyToMany(fetch = FetchType.LAZY)
    Set<SiteUser> voter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;




    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", regDate=" + regDate +
                ", replyList=" + replyList +
                '}';
    }
}
