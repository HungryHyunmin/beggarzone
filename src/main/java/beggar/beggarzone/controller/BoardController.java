package beggar.beggarzone.controller;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.repository.BoardRepository;
import beggar.beggarzone.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board") //프리픽스
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Board> paging = this.boardService.getList(page);
        model.addAttribute("paging",paging);
    return "board_list";
}

@GetMapping(value="/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, ReplyForm replyForm) {
        Board board = this.boardService.getBoard(id);
        System.out.println(board.toString());
        model.addAttribute("board",board);
        return "board_detail";
    }

    @GetMapping("/create")
    public String boardCreate(BoardForm boardForm){
        return "board_form";
    }
    @PostMapping("/create")
    public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "board_form";
        }
        this.boardService.create(boardForm.getTitle(),boardForm.getContent());
        return "redirect:/board/list"; //저장후 목록으로 이동
    }
}
