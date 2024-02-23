package beggar.beggarzone.controller;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.Reply;
import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.service.BoardService;
import beggar.beggarzone.service.ReplyService;
import beggar.beggarzone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;


@RequestMapping("/reply")
@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final BoardService boardService;
    private final ReplyService replyService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createReply(Model model, @PathVariable("id") Integer id,
                              @Valid ReplyForm replyForm, BindingResult bindingResult, Principal principal) {
        Board board = this.boardService.getBoard(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("board",board);
            return "board_detail";
        }
        Reply reply = this.replyService.create(board,replyForm.getContent(), siteUser);
        return String.format("redirect:/board/detail/%s#reply_%s", reply.getBoard().getId(),reply.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String replyModify(ReplyForm replyForm, @PathVariable("id") Integer id, Principal principal){
        Reply reply = this.replyService.getReply(id);
        if (!reply.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        replyForm.setContent(reply.getContent());
        return "reply_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String replyModify(@Valid ReplyForm replyForm, BindingResult bindingResult,
                              @PathVariable("id") Integer id,Principal principal){
        if(bindingResult.hasErrors()){
            return "reply_form";
        }

        Reply reply = this.replyService.getReply(id);
        if(!reply.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
        }
        this.replyService.Modify(reply,replyForm.getContent());
        return String.format("redirect:/board/detail/%s#reply_%s", reply.getBoard().getId(),reply.getId());

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String replyDelete(Principal principal, @PathVariable("id") Integer id) {
        Reply reply = this.replyService.getReply(id);
        if (!reply.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.replyService.delete(reply);
        return String.format("redirect:/board/detail/%s", reply.getBoard().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String replyVote(Principal principal, @PathVariable("id") Integer id) {
        Reply reply = this.replyService.getReply(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.replyService.vote(reply, siteUser);
        return String.format("redirect:/board/detail/%s#reply_%s", reply.getBoard().getId(),reply.getId());
    }
}
