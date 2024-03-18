package beggar.beggarzone.api;

import beggar.beggarzone.domain.Board;
import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.repository.BoardRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardRepository boardRepository;



}
