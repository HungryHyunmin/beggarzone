package beggar.beggarzone.dto.board.request;

import beggar.beggarzone.domain.Reply;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardReplyDto {

    private Long id;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
    private String replyUsername;

    public BoardReplyDto(Reply reply){
        id= reply.getId();
        content = reply.getContent();
        regDate = reply.getRegDate();
        replyUsername = reply.getAuthor().getUsername();
        modifyDate = reply.getModifyDate();

    }

}
