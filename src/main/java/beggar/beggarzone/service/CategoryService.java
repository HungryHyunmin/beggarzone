package beggar.beggarzone.service;

import beggar.beggarzone.domain.Category;
import beggar.beggarzone.exception.DataNotFoundException;
import beggar.beggarzone.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategory(){
        return this.categoryRepository.findAll();
    }
}
