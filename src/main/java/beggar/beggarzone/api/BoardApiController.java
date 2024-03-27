package beggar.beggarzone.api;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.Hashtag;
import beggar.beggarzone.dto.board.BoardRequestDto;
import beggar.beggarzone.service.BoardHashtagService;
import beggar.beggarzone.service.BoardService;
import beggar.beggarzone.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final BoardHashtagService boardHashtagService;
    private final HashtagService hashtagService;

    @GetMapping("/api/v1/board")
    public List<BoardRequestDto> getBoardList(){
        List<Board> boards = boardService.getAllList();
      List<BoardRequestDto> result= boards.stream()
                .map(b -> new BoardRequestDto(b))
                        .collect(toList());
            return result;
    }
    /*public List<Hashtag> findHashtag(){
       hashtagService.findAll();

    }*/
}
