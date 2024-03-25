package beggar.beggarzone.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor //
@AllArgsConstructor //
@Getter
@Entity
public class BoardHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    private  Board board;
    @ManyToOne
    private  Hashtag hashtag;

    public BoardHashtag(Board board, Hashtag hashtag) {
        this.board = board;
        this.hashtag = hashtag;
    }
}