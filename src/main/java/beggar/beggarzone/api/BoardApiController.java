package beggar.beggarzone.api;

import beggar.beggarzone.controller.BoardForm;
import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.BoardHashtag;
import beggar.beggarzone.domain.Hashtag;
import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.dto.board.request.BoardReponse;
import beggar.beggarzone.dto.board.response.CreateBoardResponseDto;
import beggar.beggarzone.dto.board.response.DeleteBoardResponse;
import beggar.beggarzone.dto.board.response.UpdateBoardResponse;
import beggar.beggarzone.exception.DataNotFoundException;
import beggar.beggarzone.service.BoardHashtagService;
import beggar.beggarzone.service.BoardService;
import beggar.beggarzone.service.HashtagService;
import beggar.beggarzone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final BoardHashtagService boardHashtagService;
    private final HashtagService hashtagService;
    private  final UserService userService;

    @GetMapping("/api/v1/board") // 페이징 조회
    public List<BoardReponse> getBoardList(@RequestParam(value = "page", defaultValue = "0") int page){
        Page<Board> boards = boardService.getListV2(page);
      List<BoardReponse> result= boards.stream()
                .map(b -> new BoardReponse(b))
                        .collect(toList());
            return result;
    }
 /*  @GetMapping("/api/v1/board/{tagName}")
   public List<BoardReponse> getHashBoardList(@PathVariable("tagName") String tagName ,
                                              @RequestParam(value = "page", defaultValue = "0") int page){

       Page<BoardHashtag> boards =boardHashtagService.findAllByHashtag(page,tagName);
       List<BoardReponse> result= boards.stream()
               .map(b -> new BoardReponse(b.getBoard()))
               .collect(toList());
       return result;
   }*/

    @GetMapping("/api/v1/board/{boardId}")// 단건
    public BoardReponse getBoard(@PathVariable("boardId") Integer boardId){
        Board board =boardService.getBoard(boardId);
        return new BoardReponse(board);
    }




    @PostMapping("/api/v1/{id}/board") //등록
    public CreateBoardResponseDto saveBoard(@PathVariable("id") Long id, @RequestBody @Valid BoardForm boardForm){
        SiteUser user = userService.findOne(id);
        Long boardId = boardService.create(user, boardForm);
        return new CreateBoardResponseDto(boardId);
    }
    @PutMapping("/api/v1/board/{boardId}")
    public UpdateBoardResponse updateBoard(@PathVariable("boardId") Integer boardId, @RequestBody @Valid BoardForm boardForm ){
        Board b = boardService.getBoard(boardId);
        Long boardId2 = b.getId();
        this.boardService.modify(b,boardForm);

        return new UpdateBoardResponse(boardId2);
    }
    @DeleteMapping("/api/v1/board/{boardId}")
    public DeleteBoardResponse deleteBoard(@PathVariable("boardId") Integer id){
        Board b = boardService.getBoard(id);
        this.boardService.delete(b);
        return new DeleteBoardResponse(b.getId());
    }
}
