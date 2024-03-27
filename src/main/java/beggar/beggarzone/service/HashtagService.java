package beggar.beggarzone.service;

import beggar.beggarzone.domain.Hashtag;
import beggar.beggarzone.repository.HashtagRepository;
import jakarta.validation.constraints.DecimalMax;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class HashtagService {

    private  final HashtagRepository hashtagRepository;
    public Optional<Hashtag> findByTagName(String tagName) {

        return hashtagRepository.findByTagName(tagName);
    }
    public Hashtag save(String tagName) {



        return hashtagRepository.save(
                Hashtag.builder()
                        .tagName(tagName)
                        .build());
    }

    public List<Hashtag> findAll() {
       return hashtagRepository.findAll();
    }
}
