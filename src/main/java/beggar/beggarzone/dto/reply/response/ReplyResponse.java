package beggar.beggarzone.dto.reply.response;

import beggar.beggarzone.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyResponse {
    private Long id;
    private String content;
    private String name;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;

    public ReplyResponse(Reply reply){
        id= reply.getId();
        content= reply.getContent();
        name= reply.getAuthor().getUsername();
        regDate= reply.getRegDate();
        modifyDate= reply.getModifyDate();
    }
}
