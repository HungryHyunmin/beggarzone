package beggar.beggarzone.api;

import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.dto.user.CreateUserResponseDto;

import beggar.beggarzone.dto.user.UpdateUserResponseDto;
import beggar.beggarzone.dto.user.UserRequestDto;
import beggar.beggarzone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {

private final UserService userService;

    @PostMapping("/api/v1/users") //회원 등록 API
    public CreateUserResponseDto saveUser(@RequestBody @Valid UserRequestDto request){
        SiteUser user = new SiteUser();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        SiteUser user1 = userService.create(user.getUsername(), user.getPassword(), user.getEmail());
        Long id= user1.getId();
        return new CreateUserResponseDto(user1.getId()); //생성자에 따라 UserRepsonseDto 오버라이딩
    }

    @PutMapping("/api/v1/users/{id}")
    public UpdateUserResponseDto updateUser(
            @PathVariable("id") Long id, @RequestBody @Valid UserRequestDto request){
        userService.update(id, request.getUsername(), request.getPassword(), request.getEmail());
        SiteUser findUser = userService.findOne(id);
        return new UpdateUserResponseDto(findUser.getId(),findUser.getUsername(), findUser.getPassword(), findUser.getEmail());
    }

    @GetMapping("/api/v1/users")
    public List<UserRequestDto> findUser(){
        List<SiteUser> userList = userService.findAll();
       List<UserRequestDto> collect = userList.stream()
                       .map(u-> new UserRequestDto(u.getId(),u.getUsername(),u.getPassword(),u.getEmail()))
                        .collect(Collectors.toList());
       return collect;
    }

}
