package beggar.beggarzone.service;

import beggar.beggarzone.domain.SiteUser;
import beggar.beggarzone.exception.DataNotFoundException;
import beggar.beggarzone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SiteUser create(String username, String email, String password){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
       /* BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 암호화 코드*/
        user.setPassword(passwordEncoder.encode(password)); // securityconfig에 객체 주입함(bean)
        this.userRepository.save(user);
        return user;
    }

    @Transactional
    public void update(Long id, String username, String password, String email) {
        Optional<SiteUser> user = userRepository.findById(id);
        if(user.isPresent()) {
            SiteUser user1 = user.get();
            user1.setUsername(username);
            user1.setPassword(passwordEncoder.encode(password));
            user1.setEmail(email);
        }
        else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    public SiteUser getUser(String username){
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
        if(siteUser.isPresent()){
            return  siteUser.get();
        } else{
            throw new DataNotFoundException("siteuser not found");
        }
    }

    public SiteUser findOne(Long id) {
        Optional<SiteUser> siteUser = userRepository.findById(id);
        if(siteUser.isPresent()){
            return  siteUser.get();
        } else{
            throw new DataNotFoundException("siteuser not found");
        }
    }

}
