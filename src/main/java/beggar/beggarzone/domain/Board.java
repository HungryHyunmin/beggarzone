package beggar.beggarzone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.EAGER)
    private SiteUser author; // 다대일 단방향

    private LocalDateTime modifyDate;

    @ManyToMany(fetch = FetchType.LAZY)
    Set<SiteUser> voter;

  @OneToMany(mappedBy = "board" ,cascade = CascadeType.ALL, orphanRemoval = true )
   private  List<BoardHashtag> boardHashtags = new ArrayList<>();




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
