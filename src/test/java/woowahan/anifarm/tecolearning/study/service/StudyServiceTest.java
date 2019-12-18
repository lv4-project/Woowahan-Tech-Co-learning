package woowahan.anifarm.tecolearning.study.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyParticipant;
import woowahan.anifarm.tecolearning.study.domain.StudyParticipantStatus;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyParticipantRepository;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyRepository;
import woowahan.anifarm.tecolearning.study.service.dto.StudyCreateDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudySummaryDto;
import woowahan.anifarm.tecolearning.study.service.exception.InvalidParticipatingRequestException;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;
import woowahan.anifarm.tecolearning.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.data.domain.Sort.by;

@Slf4j
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
    @InjectMocks
    private StudyService injectStudyService;

    @Mock
    private StudyRepository studyRepository;

    @Mock
    private StudyParticipantRepository studyParticipantRepository;

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
        given(studyParticipantRepository.save(any(StudyParticipant.class))).willReturn(mock(StudyParticipant.class));

        // when
        StudyInfoDto savedStudyInfoDto = injectStudyService.save(studyCreateDto, userInfoDto);

        // then
        verify(studyParticipantRepository).save(any(StudyParticipant.class));
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

    @Test
    @DisplayName("StudySummaryDto를 page단위로 조회한다.")
    void findSummaryDtoPage() {
        // Given
        int offset = 0;
        int pageSize = 10;
        Sort sort = by(Sort.Direction.ASC, "createdDate");
        PageRequest pageRequest = PageRequest.of(offset, pageSize, sort);

        Page<Study> pageOfStudy = getPageOfStudy(pageSize);
        given(studyRepository.findAll(pageRequest)).willReturn(pageOfStudy);

        // When
        List<StudySummaryDto> pageOfSummaryDto = injectStudyService.findPageOfSummaryDto(pageRequest);

        // Then
        assertThat(pageOfSummaryDto.size()).isEqualTo(pageSize);
    }

    private Page<Study> getPageOfStudy(int pageSize) {
        List<Study> studies = new ArrayList<>();

        for (int i = 0; i < pageSize; i++) {
            studies.add(getMockStudy());
        }
        return new PageImpl<>(studies);
    }

    // TODO: 2019-12-12 Study 수정 test


    @Test
    @DisplayName("발제자가 아닌 회원이 스터디에 참여한다.")
    void participateInStudy() {
        long studyId = 1L;
        UserInfoDto userInfoDto = UserInfoDto.builder().id(1L).build();
        given(studyRepository.findById(studyId)).willReturn(Optional.of(mock(Study.class)));
        given(userService.findById(userInfoDto.getId())).willReturn(mock(User.class));

        String studyParticipantStatus = injectStudyService.participateInStudy(studyId, userInfoDto);

        assertThat(studyParticipantStatus)
                .isEqualTo(StudyParticipantStatus.PARTICIPANT.name().toLowerCase());
    }

    @Test
    @DisplayName("발제자가 스터디에 참여 시도하는 경우 InvalidParticipatingRequestException 예외 발생")
    void participateStudy_ifUserIsPresenter() {
        // Given
        long studyId = 1L;
        UserInfoDto userInfoDto = UserInfoDto.builder().id(1L).build();
        Study mockStudy = mock(Study.class);

        given(studyRepository.findById(studyId)).willReturn(Optional.of(mockStudy));
        given(userService.findById(userInfoDto.getId())).willReturn(mock(User.class));
        given(mockStudy.isCreatedBy(any(User.class))).willReturn(true);

        // When, Then
        assertThrows(InvalidParticipatingRequestException.class,
                () -> injectStudyService.participateInStudy(studyId, userInfoDto));
    }
}