package woowahan.anifarm.tecolearning.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.user.domain.exception.UserAuthenticationFailException;
import woowahan.anifarm.tecolearning.user.dto.UserUpdateDto;

import static org.assertj.core.api.Assertions.*;

class UserTest {
    private static final String VALID_EMAIL = "moomin@woowa.com";
    private static final String VALID_PASSWORD = "12345678";
    private static final String VALID_NICK_NAME = "moomin";

    private static final String NICK_NAME = "moo";
    private static final String INTRODUCTION = "Hi";

    @Test
    @DisplayName("유저 닉네임, 자기소개 글 수정")
    void userUpdate() {
        User user = User.builder().email(VALID_EMAIL).password(VALID_PASSWORD).nickName(VALID_NICK_NAME).build();

        User updateUser = new UserUpdateDto(NICK_NAME, INTRODUCTION).toEntity();
        user.update(updateUser);

        assertThat(user.getNickName()).isEqualTo(NICK_NAME);
        assertThat(user.getIntroduction()).isEqualTo(INTRODUCTION);
    }

    @Test
    @DisplayName("유저 비활성화")
    void deactivate() {
        User user = User.builder().email(VALID_EMAIL).password(VALID_PASSWORD).nickName(VALID_NICK_NAME).build();
        user.deactivate();

        assertThat(user.getStatus()).isEqualTo(AccountStatus.INACTIVE);
    }

    @Test
    @DisplayName("User id가 같으면, 인증에 성공(void)")
    void authenticate() {
        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(1L).build();

        assertThatCode(() -> user1.authenticate(user2)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("User id가 다를 경우, UserAuthenticationFailException 을 반환한다.")
    void authenticate_fail() {
        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(999L).build();

        assertThatThrownBy(() -> user1.authenticate(user2))
                .isInstanceOf(UserAuthenticationFailException.class);
    }

    @Test
    @DisplayName("User id가 같으면 true 반환")
    void doesNotAuthenticated_false() {
        User user = User.builder().id(1L).build();
        assertThat(user.authenticate(1L)).isTrue();
    }

    @Test
    @DisplayName("User id가 같지 않으면 false 반환")
    void doesNotAuthenticated() {
        User user = User.builder().id(1L).build();
        assertThat(user.authenticate(99999L)).isFalse();
    }

    @Test
    @DisplayName("같은 user일 경우 true 반환")
    void isSame() {
        User user = User.builder().id(1L).build();
        User user2 = User.builder().id(1L).build();

        assertThat(user.is(user2)).isTrue();
    }
}