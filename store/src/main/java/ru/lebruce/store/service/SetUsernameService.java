package ru.lebruce.store.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.dto.SetUsernameRequest;
import ru.lebruce.store.domain.model.SetUsernameToken;
import ru.lebruce.store.domain.model.User;

@Service
@RequiredArgsConstructor
public class SetUsernameService {
    private final UserService userService;
    private final EmailService emailService;
    private final SetUsernameTokenService setUsernameTokenService;


    @Transactional
    public void setUsername(String token) {
        SetUsernameToken setUsernameToken = setUsernameTokenService.getToken(token);
        User user = setUsernameToken.getUser();
        user.setUsername(setUsernameToken.getUsername());
        userService.saveUser(user);
        setUsernameTokenService.delete(setUsernameToken);
    }

    public boolean existsByUser(User user) {
        return setUsernameTokenService.existsByUser(user);
    }

    @Transactional
    @Async("taskExecutor")
    public void setUsernameRequest(User user, SetUsernameRequest setUsernameRequest) throws MessagingException {
        var token = setUsernameTokenService.generateToken(user, setUsernameRequest.getUsername());
        var emailContext = emailService.setUsernmaeEmailContext(user, token.getToken(), setUsernameRequest.getUsername());
        emailService.sendEmail(emailContext);
    }

}
