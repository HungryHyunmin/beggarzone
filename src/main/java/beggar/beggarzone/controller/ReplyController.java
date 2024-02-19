package beggar.beggarzone.controller;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.service.BoardService;
import beggar.beggarzone.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
                              @RequestParam(value = "content") String content) {
        Board board = this.boardService.getBoard(id);
        this.replyService.create(board, content);
        return String.format("redirect:/board/detail/%s",id);
    }
}
