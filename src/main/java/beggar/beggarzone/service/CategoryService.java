package beggar.beggarzone.service;

import beggar.beggarzone.domain.Category;
import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.exception.DataNotFoundException;
import beggar.beggarzone.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategory(){
        return this.categoryRepository.findAll();
    }

    public Category getCategory(Integer id){
        Optional<Category> category = this.categoryRepository.findById(id);
        if(category.isPresent()){
            return  category.get();
        } else{
            throw new DataNotFoundException("category not found");
        }
    }
}
