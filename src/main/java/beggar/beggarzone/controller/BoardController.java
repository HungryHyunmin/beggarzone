package beggar.beggarzone.controller;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.Category;
import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.repository.BoardRepository;
import beggar.beggarzone.service.BoardService;
import beggar.beggarzone.service.CategoryService;
import beggar.beggarzone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequestMapping("/board") //프리픽스
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final CategoryService categoryService;
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "kw",defaultValue = "") String kw) {
        Page<Board> paging = this.boardService.getList(page,kw);

        model.addAttribute("paging",paging);
        model.addAttribute("kw",kw);
    return "board_list";
}

    @GetMapping(value = "/list/{categoryId}") //카테고리
    public String categoryList(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                               @PathVariable(name = "categoryId") Integer categoryId){
        Page<Board> paging = this.boardService.getCategoryList(page,categoryId);
        model.addAttribute("paging",paging);
        return "board_category";
    }



@GetMapping(value="/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, ReplyForm replyForm) {
        Board board = this.boardService.getBoard(id);
        System.out.println(board.toString());
        model.addAttribute("board",board);
        return "board_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String boardCreate(Model model,BoardForm boardForm){

        List<Category> categoryList=this.categoryService.getCategory(); // 글작성시 카테고리 리스트 불러오기

        model.addAttribute("categoryList",categoryList);
        return "board_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "board_form";
        }
        System.out.println("보드폼 "+ boardForm.toString());
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Category category = this.categoryService.getCategory(boardForm.getCategory().getId());
        System.out.println("-siteUser : " + siteUser);
        this.boardService.create(boardForm.getTitle(),boardForm.getContent(),siteUser,category);
        return "redirect:/board/list"; //저장후 목록으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(BoardForm boardForm,@PathVariable("id") Integer id, Principal principal){
        Board board = this.boardService.getBoard(id);
        if(!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());

        return "board_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String boardModify(@Valid BoardForm boardForm, BindingResult bindingResult,Principal principal, @PathVariable("id") Integer id){
        if(bindingResult.hasErrors()){
            return "board_form";
        }
        Board board = this.boardService.getBoard(id);
        if(!board.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
        }
        this.boardService.modify(board,boardForm.getTitle(),boardForm.getContent());
        return String.format("redirect:/board/detail/%s",id);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String boardDelete(Principal principal,@PathVariable("id") Integer id){
        Board board = this.boardService.getBoard(id);
        if (!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.boardService.delete(board);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String boardVote(Principal principal, @PathVariable("id") Integer id) {
        Board board = this.boardService.getBoard(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.boardService.vote(board, siteUser);
        return String.format("redirect:/board/detail/%s", id);
    }

}
