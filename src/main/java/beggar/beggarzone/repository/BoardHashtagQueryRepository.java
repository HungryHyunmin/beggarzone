package beggar.beggarzone.repository;

import beggar.beggarzone.domain.BoardHashtag;
import beggar.beggarzone.dto.BoardHashtagQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BoardHashtagQueryRepository  extends JpaRepository<BoardHashtag,Long>{

@Query("select new beggar.beggarzone.dto.BoardHashtagQueryDto(b.id, h.tagName) from BoardHashtag b left outer join b.hashtag h")
    List<BoardHashtagQueryDto> findHashDto();


}
