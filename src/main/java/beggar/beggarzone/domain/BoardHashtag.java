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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private  Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="HASHTAG_ID")
    private  Hashtag hashtag;

    public BoardHashtag(Board board, Hashtag hashtag) {
        this.board = board;
        this.hashtag = hashtag;
    }
}
