package beggar.beggarzone.api;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.Reply;
import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.dto.reply.request.RequestDto;
import beggar.beggarzone.dto.reply.response.CreateReplyResponseDto;
import beggar.beggarzone.dto.reply.response.DeleteReplyResponse;
import beggar.beggarzone.dto.reply.response.ReplyResponse;
import beggar.beggarzone.dto.reply.response.UpdateReplyResponseDto;
import beggar.beggarzone.service.BoardService;
import beggar.beggarzone.service.ReplyService;
import beggar.beggarzone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;
    private final BoardService boardService;
    private final UserService userService;

        @PostMapping("/api/v1/reply/{boardId}")
        public CreateReplyResponseDto saveReply(@PathVariable("boardId") Long boardId , @RequestBody RequestDto request){

            Board b =boardService.getBoard(Math.toIntExact(boardId));
            SiteUser user = userService.findOne(b.getAuthor().getId());
            Reply reply =replyService.create(b,request.getContent(),user);
            return new CreateReplyResponseDto(reply.getId());
        }
        @PutMapping("/api/v1/reply/{replyId}")
        public UpdateReplyResponseDto updateReply(@PathVariable("replyId") Long replyId, @RequestBody RequestDto request){
            Reply reply = replyService.getReply(Math.toIntExact(replyId));
            replyService.Modify(reply, request.getContent());
            return new UpdateReplyResponseDto(replyId);
        }

        @DeleteMapping("/api/v1/reply/{replyId}")
        public DeleteReplyResponse deleteReply(@PathVariable("replyId") Long replyId){
            Reply r = replyService.getReply(Math.toIntExact(replyId));
            this.replyService.delete(r);
            return new DeleteReplyResponse(replyId);
        }
        @GetMapping("/api/v1/reply/{replyId}")
        public ReplyResponse getReply(@PathVariable("replyId") Long replyId){
            Reply reply = replyService.getReply(Math.toIntExact(replyId));
            return new ReplyResponse(reply);
        }

    }

