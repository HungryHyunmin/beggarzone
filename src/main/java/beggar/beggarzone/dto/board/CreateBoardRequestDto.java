package beggar.beggarzone.dto.board;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.BoardHashtag;
import beggar.beggarzone.domain.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBoardRequestDto {
    private String title;
    private String content;
    private LocalDateTime regDate;
    private SiteUser author;
    private List<BoardHashtag> boardHashtags = new ArrayList<>();



}


