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
import woowahan.anifarm.tecolearning.study.domain.exception.NotPresenterException;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyRepository;
import woowahan.anifarm.tecolearning.study.service.dto.StudyCreateDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyParticipantInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudySummaryDto;
import woowahan.anifarm.tecolearning.study.service.exception.StudyParticipantNotFoundException;
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
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.by;
import static woowahan.anifarm.tecolearning.study.domain.StudyParticipantStatus.PARTICIPANT;
import static woowahan.anifarm.tecolearning.study.domain.StudyStatus.RECRUITING;

@Slf4j
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
    private static final Long STUDY_ID = 1L;
    private static final Long USER_ID = 1L;

    @InjectMocks
    private StudyService injectStudyService;

    @Mock
    private StudyRepository studyRepository;

    @Mock
    private UserService userService;

    @Mock
    private StudyParticipantService studyParticipantService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().id(USER_ID)
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
        UserInfoDto userInfoDto = UserInfoDto.builder().id(USER_ID).build();
        Study expectedStudy = Study.builder().id(STUDY_ID).build();
        Study mockStudy = getMockStudy();

        given(userService.findById(USER_ID)).willReturn(user);
        given(studyRepository.save(any(Study.class))).willReturn(mockStudy);
        given(studyParticipantService.save(any(StudyParticipant.class))).willReturn(mock(StudyParticipantInfoDto.class));

        // when
        StudyInfoDto savedStudyInfoDto = injectStudyService.save(studyCreateDto, userInfoDto);

        // then
        assertThat(savedStudyInfoDto.getId()).isEqualTo(expectedStudy.getId());
    }

    private Study getMockStudy() {
        Study mockStudy = mock(Study.class);

        given(mockStudy.getId()).willReturn(STUDY_ID);
        given(mockStudy.getPresenter()).willReturn(user);
        given(mockStudy.getStatus()).willReturn(RECRUITING);

        return mockStudy;
    }

    @Test
    @DisplayName("Study id를 넘겨받아 스터디를 조회한다.")
    void findById() {
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
        given(studyRepository.findAllByStatus(RECRUITING, pageRequest)).willReturn(pageOfStudy);

        // When
        List<StudySummaryDto> pageOfSummaryDto =
                injectStudyService.findPageOfSummaryDto(RECRUITING.getName(), pageRequest);

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

    @Test
    @DisplayName("StudySummaryDto에 현재까지 참여 신청한 인원 수를 넣어 되돌려준다.")
    void checkCurrentNumberOfParticipants_InSummaryDto() {
        // Given
        int expectedNumberOfParticipants = 4;
        int offset = 0;
        int pageSize = 1;
        Sort sort = by(Sort.Direction.ASC, "createdDate");
        PageRequest pageRequest = PageRequest.of(offset, pageSize, sort);

        Page<Study> pageOfStudy = getPageOfStudy(pageSize);
        given(studyRepository.findAllByStatus(RECRUITING, pageRequest)).willReturn(pageOfStudy);
        given(studyParticipantService.countOfParticipant(any(Long.class))).willReturn(expectedNumberOfParticipants);

        // When
        List<StudySummaryDto> pageOfSummaryDto =
                injectStudyService.findPageOfSummaryDto(RECRUITING.getName(), pageRequest);

        // Then
        StudySummaryDto actual = pageOfSummaryDto.get(0);
        assertThat(actual.getNumberOfParticipants()).isEqualTo(expectedNumberOfParticipants);
    }

    // TODO: 2019-12-12 Study 수정 test 추가할 것

    @Test
    @DisplayName("발제자가 아닌 회원이 스터디에 참여한다.")
    void participateInStudy() {
        UserInfoDto userInfoDto = UserInfoDto.builder().id(USER_ID).build();

        given(studyRepository.findById(STUDY_ID)).willReturn(Optional.of(mock(Study.class)));
        given(userService.findById(userInfoDto.getId())).willReturn(mock(User.class));
        given(studyParticipantService.save(any(StudyParticipant.class))).willReturn(mock(StudyParticipantInfoDto.class));

        String studyParticipantStatus = injectStudyService.participateInStudy(STUDY_ID, userInfoDto);

        assertThat(studyParticipantStatus).isEqualTo(PARTICIPANT.getStatus());
    }

    @Test
    @DisplayName("스터디 참가자가 스터디에 참여 시도하는 경우 InvalidParticipatingRequestException 예외 발생")
    void participateStudy_ifUserIsPresenter() {
        // Given
        UserInfoDto userInfoDto = UserInfoDto.builder().id(USER_ID).build();
        Study mockStudy = mock(Study.class);

        given(studyRepository.findById(STUDY_ID)).willReturn(Optional.of(mockStudy));
        given(userService.findById(userInfoDto.getId())).willReturn(mock(User.class));
        given(studyParticipantService.save(any(StudyParticipant.class))).willThrow(StudyParticipantNotFoundException.class);

        // When, Then
        assertThrows(StudyParticipantNotFoundException.class,
                () -> injectStudyService.participateInStudy(STUDY_ID, userInfoDto));
    }

    @Test
    @DisplayName("발제자가 아닐 경우 스터디 폭파 요청하는 경우 NotPresenterException 에러")
    void deleteStudy_ifNotPresenter() {
        UserInfoDto userInfoDto = UserInfoDto.builder().id(USER_ID).build();
        Study mockStudy = mock(Study.class);

        given(studyRepository.findById(STUDY_ID)).willReturn(Optional.of(mockStudy));
        doThrow(NotPresenterException.class).when(mockStudy).checkNotPresenter(anyLong());

        assertThrows(NotPresenterException.class, () -> {
            injectStudyService.delete(STUDY_ID, userInfoDto);
        });
    }

    @Test
    @DisplayName("발제자일 경우 스터디 폭파 요청하는 경우 성공")
    void deleteStudy_ifPresenter() {
        UserInfoDto userInfoDto = UserInfoDto.builder().id(USER_ID).build();
        Study mockStudy = mock(Study.class);

        given(studyRepository.findById(STUDY_ID)).willReturn(Optional.of(mockStudy));
        doNothing().when(mockStudy).checkNotPresenter(anyLong());

        injectStudyService.delete(STUDY_ID, userInfoDto);

        verify(studyRepository).delete(any(Study.class));
    }

    @Test
    @DisplayName("발제자일 경우 스터디 폭파 요청하는 경우 참가자들 삭제")
    void deleteStudyAndParticipant_ifPresenter() {
        UserInfoDto userInfoDto = UserInfoDto.builder().id(USER_ID).build();
        Study mockStudy = mock(Study.class);

        given(studyRepository.findById(STUDY_ID)).willReturn(Optional.of(mockStudy));
        doNothing().when(mockStudy).checkNotPresenter(anyLong());
        doNothing().when(studyParticipantService).deleteByStudy(any(Study.class));

        injectStudyService.delete(STUDY_ID, userInfoDto);

        verify(studyRepository).delete(any(Study.class));
    }
}