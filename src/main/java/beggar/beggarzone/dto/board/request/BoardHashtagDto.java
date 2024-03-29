package beggar.beggarzone.dto.board.request;

import beggar.beggarzone.domain.BoardHashtag;
import lombok.Data;

@Data
public class BoardHashtagDto {

    private Long id;
    private String tagNames;
    public BoardHashtagDto(BoardHashtag hashtag){
        id = hashtag.getHashtag().getId();
        tagNames = hashtag.getHashtag().getTagName();

    }

}
