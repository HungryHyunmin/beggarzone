package beggar.beggarzone.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;


    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.LAZY)
    private List<BoardHashtag> boardHashtags = new ArrayList<>();

    @Builder
    public Hashtag(String tagName) {
        this.tagName = tagName;
    }
}
