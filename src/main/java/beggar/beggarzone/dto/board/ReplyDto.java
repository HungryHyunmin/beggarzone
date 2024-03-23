package beggar.beggarzone.dto.board;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.Reply;
import beggar.beggarzone.domain.SiteUser;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ReplyDto {

    private Long id;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
    private String replyUsername;

    public ReplyDto(Reply reply){
        id= reply.getId();
        content = reply.getContent();
        regDate = reply.getRegDate();
        replyUsername = reply.getAuthor().getUsername();
        modifyDate = reply.getModifyDate();

    }

}
