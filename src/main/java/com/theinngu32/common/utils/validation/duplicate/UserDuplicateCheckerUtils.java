package com.theinngu32.common.utils.validation.duplicate;

import com.theinngu32.common.entity.User;
import com.theinngu32.common.entity.constants.errorMessage.user.UserDuplicateErrorMessage;
import com.theinngu32.common.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserDuplicateCheckerUtils {

    private static final Map<String, Function<String, Optional<User>>> duplicateFunc = new HashMap<>();
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        duplicateFunc.put(
                UserDuplicateErrorMessage.EMAIL.getValue(),
                userRepository::findByEmail
        );
    }

    public static boolean isDuplicate(String key,String value){
        var func = duplicateFunc.get(key);
        return func != null && func.apply(value).isPresent();
    }
}
