package beggar.beggarzone.api;

import beggar.beggarzone.controller.BoardForm;
import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.dto.board.request.BoardReponse;
import beggar.beggarzone.dto.board.response.CreateBoardResponseDto;
import beggar.beggarzone.dto.board.response.DeleteBoardResponse;
import beggar.beggarzone.dto.board.response.UpdateBoardResponse;
import beggar.beggarzone.service.BoardHashtagService;
import beggar.beggarzone.service.BoardService;
import beggar.beggarzone.service.HashtagService;
import beggar.beggarzone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final BoardHashtagService boardHashtagService;
    private final HashtagService hashtagService;
    private  final UserService userService;

    @GetMapping("/api/v1/board") // 조회
    public List<BoardReponse> getBoardList(){
        List<Board> boards = boardService.getAllList();
      List<BoardReponse> result= boards.stream()
                .map(b -> new BoardReponse(b))
                        .collect(toList());
            return result;
    }
    @GetMapping("/api/v1/board/{boardId}")
    public BoardReponse getBoard(@PathVariable("boardId") Integer boardId){
        Board board =boardService.getBoardv2(boardId);
        return new BoardReponse(board);
    }

    @PostMapping("/api/v1/{id}/board") //등록
    public CreateBoardResponseDto saveBoard(@PathVariable("id") Long id, @RequestBody @Valid BoardForm boardForm){
        SiteUser user = userService.findOne(id);
        Long boardId = boardService.create(user, boardForm);
        return new CreateBoardResponseDto(boardId);
    }
    @PutMapping("/api/v1/{id}/board/{boardId}")
    public UpdateBoardResponse updateBoard(@PathVariable("id") Integer id, @PathVariable("boardId") Integer boardId, @RequestBody @Valid BoardForm boardForm ){
        Board b = boardService.getBoard(boardId);
        Long boardId2 = b.getId();
        this.boardService.modify(b,boardForm);

        return new UpdateBoardResponse(boardId2);
    }
    @DeleteMapping("/api/v1/board/{boardId}")
    public DeleteBoardResponse deleteBoard(@PathVariable("boardId") Integer id){
        Board b = boardService.getBoardv2(id);
        this.boardService.delete(b);
        return new DeleteBoardResponse(b.getId());
    }
}
