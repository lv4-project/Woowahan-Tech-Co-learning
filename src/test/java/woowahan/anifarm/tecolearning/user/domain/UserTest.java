package woowahan.anifarm.tecolearning.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.user.dto.UserUpdateDto;

import static org.assertj.core.api.Assertions.assertThat;

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

}