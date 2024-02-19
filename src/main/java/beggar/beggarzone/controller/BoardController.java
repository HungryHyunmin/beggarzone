package beggar.beggarzone.controller;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.repository.BoardRepository;
import beggar.beggarzone.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/board") //프리픽스
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/list")
    public String list(Model model){
        List<Board> boardList = this.boardService.getList();
        model.addAttribute("boardList",boardList);
    return "board_list";
}

@GetMapping(value="/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Board board = this.boardService.getBoard(id);
        System.out.println(board.toString());
        model.addAttribute("board",board);
        return "board_detail";
    }
}
