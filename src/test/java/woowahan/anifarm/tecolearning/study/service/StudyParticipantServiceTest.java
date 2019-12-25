package woowahan.anifarm.tecolearning.study.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyParticipant;
import woowahan.anifarm.tecolearning.study.domain.exception.PresenterException;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyParticipantRepository;
import woowahan.anifarm.tecolearning.study.service.exception.StudyParticipantNotFoundException;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyParticipantServiceTest {
    private static final long STUDY_ID = 1L;
    private static final long USER_ID = 1L;
    private static final long ANOTHER_USER_ID = 2L;

    @InjectMocks
    private StudyParticipantService studyParticipantService;

    @Mock
    private StudyParticipantRepository studyParticipantRepository;

    @Mock
    private StudyParticipant studyParticipant;

    private UserInfoDto userInfoDto = UserInfoDto.builder()
            .id(1L)
            .build();

    @Test
    @DisplayName("참가자인 경우 탈퇴하기")
    void withdrawStudy_ifParticipant() {
        given(studyParticipantRepository.findByStudyIdAndParticipantId(STUDY_ID, USER_ID))
                .willReturn(Optional.of(studyParticipant));
        doNothing().when(studyParticipant).checkPresenter(USER_ID);

        studyParticipantService.withdrawStudy(STUDY_ID, userInfoDto);

        verify(studyParticipantRepository).deleteByStudyIdAndParticipantId(STUDY_ID, USER_ID);
    }

    @Test
    @DisplayName("발제자인 경우 탈퇴하기 PresenterException 에러")
    void withdrawStudy_ifPresenter() {
        given(studyParticipantRepository.findByStudyIdAndParticipantId(STUDY_ID, USER_ID))
                .willReturn(Optional.of(studyParticipant));
        doThrow(PresenterException.class).when(studyParticipant).checkPresenter(USER_ID);

        assertThrows(PresenterException.class, () -> {
            studyParticipantService.withdrawStudy(STUDY_ID, userInfoDto);
        });
    }

    @Test
    @DisplayName("참가자가 아닌 경우 스터디 탈퇴 요청 시 InvalidParticipatingRequestException 에러")
    void withdrawStudy_ifNonParticipant() {
        given(studyParticipantRepository.findByStudyIdAndParticipantId(STUDY_ID, USER_ID))
                .willThrow(StudyParticipantNotFoundException.class);

        assertThrows(StudyParticipantNotFoundException.class, () -> {
            studyParticipantService.withdrawStudy(STUDY_ID, userInfoDto);
        });
    }

    @Test
    @DisplayName("어떤 스터디에 참여하고 있는 사용자들의 정보를 모두 조회한다.")
    void findAllParticipantsOfStudy() {
        // Given
        Study study = Study.builder().id(STUDY_ID).build();
        Set<StudyParticipant> givenStudyParticipants = getStudyParticipants(study);

        given(studyParticipantRepository.findAllByStudy(any(Study.class))).willReturn(givenStudyParticipants);

        // When
        Set<UserInfoDto> participants = studyParticipantService.findAllParticipantsOf(study);

        // Then
        assertThat(participants.size()).isEqualTo(givenStudyParticipants.size());
    }

    private Set<StudyParticipant> getStudyParticipants(Study study) {
        User user1 = User.builder().id(USER_ID).build();
        User user2 = User.builder().id(ANOTHER_USER_ID).build();
        StudyParticipant studyParticipant1 = StudyParticipant.builder().participant(user1).study(study).build();
        StudyParticipant studyParticipant2 = StudyParticipant.builder().participant(user2).study(study).build();

        return new HashSet<>(Arrays.asList(
                studyParticipant1, studyParticipant2
        ));
    }
}