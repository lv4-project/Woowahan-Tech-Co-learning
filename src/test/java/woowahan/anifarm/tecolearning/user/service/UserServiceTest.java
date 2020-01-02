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
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

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
        // Given
        UserCreateDto userCreateDto = UserCreateDto.builder().build();
        User expected = User.builder().id(1L).build();
        when(userRepository.save(any())).thenReturn(expected);

        // When
        UserInfoDto createdUser = injectUserService.save(userCreateDto);

        // Then
        assertThat(createdUser.getId()).isEqualTo(expected.getId());
    }
}