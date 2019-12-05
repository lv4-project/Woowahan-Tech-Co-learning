package woowahan.anifarm.tecolearning.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.domain.UserRepository;
import woowahan.anifarm.tecolearning.user.dto.UserCreateDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService injectUserService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 생성 성공")
    void creat() {
        UserCreateDto userCreateDto = UserCreateDto.builder().build();

        when(userRepository.save(any())).thenReturn(User.builder().id(1L).build());
        assertThat(injectUserService.save(userCreateDto)).isEqualTo(1L);
    }
}