package beggar.beggarzone.dto.board.request;

import beggar.beggarzone.domain.Board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
public class BoardReponse {
    private Long id;
    private String title;
    private String content;
    private String name;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
    private List<BoardReplyDto> replyList;
    private List<BoardHashtagDto> boardHashtagList;


    public BoardReponse(Board board) {
        id = board.getId();
        title = board.getTitle();
        name = board.getAuthor().getUsername();
        content = board.getContent();
        regDate = board.getRegDate();
        modifyDate = board.getModifyDate();

        /*replyList=board.getReplyList(); 이코드를 사용할경우 reply 엔티티를 그대로 반환 하기때문에 좋지않음*/
        replyList = board.getReplyList().stream()
                .map(reply -> new BoardReplyDto(reply))
                .collect(toList()); //dto를 사용해 엔티티 그대로 반환x

        boardHashtagList = board.getBoardHashtags().stream()
                .map(hashtag -> new BoardHashtagDto(hashtag))
                .collect(toList());

    }
}