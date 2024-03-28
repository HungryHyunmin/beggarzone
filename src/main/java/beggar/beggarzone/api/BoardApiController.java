package beggar.beggarzone.api;

import beggar.beggarzone.controller.BoardForm;
import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.Hashtag;
import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.dto.board.BoardRequestDto;
import beggar.beggarzone.dto.board.CreateBoardRequestDto;
import beggar.beggarzone.dto.board.CreateBoardResponseDto;
import beggar.beggarzone.service.BoardHashtagService;
import beggar.beggarzone.service.BoardService;
import beggar.beggarzone.service.HashtagService;
import beggar.beggarzone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
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
    public List<BoardRequestDto> getBoardList(){
        List<Board> boards = boardService.getAllList();
      List<BoardRequestDto> result= boards.stream()
                .map(b -> new BoardRequestDto(b))
                        .collect(toList());
            return result;
    }


    @PostMapping("/api/v1/{id}/board") //등록
    public CreateBoardResponseDto saveBoard(@PathVariable("id") Long id, @RequestBody @Valid BoardForm boardForm){

        SiteUser user = userService.findOne(id);
        Long boardId = boardService.create(user, boardForm);
        return new CreateBoardResponseDto(boardId);

    }
}
