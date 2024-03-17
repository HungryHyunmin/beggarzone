package beggar.beggarzone.api;

import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserApiController {

private final UserService userService;

    @PostMapping("/api/v1/users") //회원 등록 API
    public CreateUserResponse saveUser(@RequestBody @Valid CreateUserRequest request){
        SiteUser user = new SiteUser();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        SiteUser user1 = userService.create(user.getUsername(), user.getPassword(), user.getEmail());
        return new CreateUserResponse(user1.getId());
    }

    @PutMapping("/api/v1/users/{id}")
    public UpdateUserResponse updateUser(
            @PathVariable("id") Long id, @RequestBody @Valid UpdateUserRequest request){
        userService.update(id, request.getUsername(), request.getPassword(), request.getEmail());
        SiteUser findUser = userService.findOne(id);
        return new UpdateUserResponse(findUser.getId(),findUser.getUsername(), findUser.getPassword(), findUser.getEmail());
    }


     @Data
     @AllArgsConstructor
     static class  UpdateUserResponse{
        private Long id;
        private String username;
        private String password;
        private String email;
     }
     @Data
     static class UpdateUserRequest{
         private String username;
         private String password;
         private String email;
     }

    @Data
    static class CreateUserRequest{
        private String username;
        private String password;
        private String email;
    }

    @Data
    static  class CreateUserResponse{
        private Long id;
        public CreateUserResponse(Long id) {
            this.id = id;
        }
    }
}
