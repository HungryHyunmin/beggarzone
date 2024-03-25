package beggar.beggarzone.dto.board;

import beggar.beggarzone.domain.Board;

import beggar.beggarzone.domain.Reply;
import beggar.beggarzone.domain.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
public class BoardRequestDto {
    private Long id;
    private String title;
    private String content;
    private String name;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
    private List<ReplyDto> replyList;




    public BoardRequestDto(Board board) {
        id =board.getId();
        title=board.getTitle();
        name =board.getAuthor().getUsername();
        content=board.getContent();
        regDate=board.getRegDate();
        modifyDate=board.getModifyDate();

        /*replyList=board.getReplyList(); 이코드를 사용할경우 reply 엔티티를 그대로 반환 하기때문에 좋지않음*/
        replyList =board.getReplyList().stream()
                .map(reply -> new ReplyDto(reply))
                .collect(toList()); //dto를 사용해 엔티티 그대로 반환x
    }

}
