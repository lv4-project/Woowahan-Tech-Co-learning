package woowahan.anifarm.tecolearning.user.domain;

import lombok.*;
import woowahan.anifarm.tecolearning.user.domain.exception.UserAuthenticationFailException;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@ToString
public class User {
    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String PASSWORD_PATTERN = "[a-zA-Z0-9!@#$%^&*()_+\\-=]{8,30}";
    private static final String NICK_NAME_PATTERN = "[a-zA-Z0-9가-힣]{2,20}";

    private static final String EMAIL_ERROR_MESSAGE = "이메일 형식에 맞지 않습니다.";
    private static final String PASSWORD_ERROR_MESSAGE = "비밀번호는 8 ~ 30자";
    private static final String NICK_NAME_ERROR_MESSAGE = "닉네임은 2 ~ 20자 한글 숫자 영어";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Pattern(regexp = EMAIL_PATTERN, message = EMAIL_ERROR_MESSAGE)
    @Column(name = "email", length = 60, unique = true)
    private String email;

    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR_MESSAGE)
    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Pattern(regexp = NICK_NAME_PATTERN, message = NICK_NAME_ERROR_MESSAGE)
    @Column(name = "nick_name", nullable = false, length = 20)
    private String nickName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    @Column(name = "introduction", length = 300)
    private String introduction;

    @Builder
    public User(Long id, String email, String password, String nickName, String introduction) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.introduction = introduction;
    }

    public static User creatGitTempUser(String email) {
        return User.builder()
                .email(email)
                .password("tempPassword")
                .nickName("moomin")
                .introduction("")
                .build();
    }

    public void update(User updateUser) {
        this.nickName = updateUser.nickName;
        this.introduction = updateUser.introduction;
    }

    public void activate() {
        this.status = AccountStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = AccountStatus.INACTIVE;
    }

    public void authenticate(User presenter) {
        if (doesNotAuthenticated(presenter)) {
            throw new UserAuthenticationFailException();
        }
    }

    private boolean doesNotAuthenticated(User presenter) {
        return !this.id.equals(presenter.id);
    }

    public boolean doesNotAuthenticated(long id) {
        return !this.id.equals(id);
    }
}
