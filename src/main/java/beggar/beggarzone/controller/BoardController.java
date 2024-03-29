package beggar.beggarzone.controller;

import beggar.beggarzone.domain.Board;

import beggar.beggarzone.domain.BoardHashtag;
import beggar.beggarzone.domain.Hashtag;
import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.service.BoardHashtagService;
import beggar.beggarzone.service.BoardService;
import beggar.beggarzone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
    private final BoardHashtagService boardHashtagService;


    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw",defaultValue = "") String kw,
                       @RequestParam(value = "type",defaultValue = "") String type
                       ) {


        Page<Board> paging = this.boardService.getList(page, kw, type);

        model.addAttribute("paging",paging);
        model.addAttribute("kw",kw);
        model.addAttribute("type",type);


    return "board_list";
}



    @GetMapping(value = "/list/{tagName}") //카테고리
    public String categoryList(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                               @PathVariable(name = "tagName") String tagName){
        Page<Board> paging = this.boardHashtagService.findAllByHashtag(page,tagName);

        model.addAttribute("paging",paging);
        return "board_hashtag";
    }



@GetMapping(value="/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, ReplyForm replyForm) {
        Board board = this.boardService.getBoard(id);
        System.out.println(board.toString());
        model.addAttribute("board",board);

        List<BoardHashtag> hashtags = boardHashtagService.findHashtagListByBoard(board);
        model.addAttribute("hashtags",hashtags);

        return "board_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String boardCreate(Model model,BoardForm boardForm){


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
        System.out.println("-siteUser : " + siteUser);
        this.boardService.create(siteUser,boardForm);

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
        this.boardService.modify(board,boardForm);
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
