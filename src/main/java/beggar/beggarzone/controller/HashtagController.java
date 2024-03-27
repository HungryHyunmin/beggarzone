package beggar.beggarzone.controller;

import beggar.beggarzone.dto.BoardHashtagQueryDto;
import beggar.beggarzone.repository.BoardHashtagQueryRepository;
import beggar.beggarzone.service.BoardHashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HashtagController {
    private BoardHashtagService boardHashtagService;
    private final BoardHashtagQueryRepository boardHashtagQueryRepository;

    @GetMapping("/board/list/hashtag")
    public String list(Model model){
       List<BoardHashtagQueryDto> list = boardHashtagQueryRepository.findHashDto();
       for(BoardHashtagQueryDto dto :list){
           System.out.println("dto="+dto);
       }
       model.addAttribute("list" ,list);
        System.out.println(list);
        return "hashtag_list";
    }
}
