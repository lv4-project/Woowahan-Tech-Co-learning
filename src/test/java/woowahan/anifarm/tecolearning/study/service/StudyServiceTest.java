package woowahan.anifarm.tecolearning.study.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyRepository;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;
import woowahan.anifarm.tecolearning.user.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Slf4j
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
    @InjectMocks
    private StudyService injectStudyService;

    @Mock
    private StudyRepository studyRepository;

    @Mock
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L)
                .email("email@email.com")
                .nickName("learner")
                .password("passwordpass")
                .introduction("intro")
                .build();
    }

    @Test
    @DisplayName("StudyCreateDto 를 넘겨받아 Study 정보를 저장한다.")
    void save() {
        // TODO: 2019-12-11 리펙토링
        // given
        StudyCreateDto studyCreateDto = StudyCreateDto.builder().build();
        UserInfoDto userInfoDto = UserInfoDto.builder().id(1L).build();
        Study expectedStudy = Study.builder().id(1L).build();
        Study mockStudy = getMockStudy();

        given(userService.findById(1L)).willReturn(user);
        given(studyRepository.save(any(Study.class))).willReturn(mockStudy);

        // when
        StudyInfoDto savedStudyInfoDto = injectStudyService.save(studyCreateDto, userInfoDto);

        // then
        assertThat(savedStudyInfoDto.getId()).isEqualTo(expectedStudy.getId());
    }

    private Study getMockStudy() {
        Study mockStudy = mock(Study.class);

        given(mockStudy.getId()).willReturn(1L);
        given(mockStudy.getPresenter()).willReturn(user);

        return mockStudy;
    }

    @Test
    @DisplayName("Study id를 넘겨받아 스터디를 조회한다.")
    void read() {
        // TODO: 2019-12-12 짤 것
    }

    // TODO: 2019-12-12 Study 수정 test
}