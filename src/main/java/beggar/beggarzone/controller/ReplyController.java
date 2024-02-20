package beggar.beggarzone.controller;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.service.BoardService;
import beggar.beggarzone.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/reply")
@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final BoardService boardService;
    private final ReplyService replyService;
    @PostMapping("/create/{id}")
    public String createReply(Model model, @PathVariable("id") Integer id,
                              @Valid ReplyForm replyForm, BindingResult bindingResult) {
        Board board = this.boardService.getBoard(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("board",board);
            return "board_detail";
        }
        this.replyService.create(board,replyForm.getContent());
        return String.format("redirect:/board/detail/%s",id);
    }
}
