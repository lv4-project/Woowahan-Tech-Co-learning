package woowahan.anifarm.tecolearning.study.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyParticipant;
import woowahan.anifarm.tecolearning.study.domain.repository.StudyParticipantRepository;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudyParticipantServiceTest {

    @InjectMocks
    private StudyParticipantService studyParticipantService;

    @Mock
    private StudyParticipantRepository studyParticipantRepository;

    @Test
    @DisplayName("어떤 스터디에 참여하고 있는 사용자들의 정보를 모두 조회한다.")
    void findAllParticipantsOfStudy() {
        // Given
        Study study = Study.builder().id(1L).build();
        Set<StudyParticipant> givenStudyParticipants = getStudyParticipants(study);

        given(studyParticipantRepository.findAllByStudy(any(Study.class))).willReturn(givenStudyParticipants);

        // When
        Set<UserInfoDto> participants = studyParticipantService.findAllParticipantsOf(study);

        // Then
        assertThat(participants.size()).isEqualTo(givenStudyParticipants.size());
    }

    private Set<StudyParticipant> getStudyParticipants(Study study) {
        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(2L).build();
        StudyParticipant studyParticipant1 = StudyParticipant.builder().participant(user1).study(study).build();
        StudyParticipant studyParticipant2 = StudyParticipant.builder().participant(user2).study(study).build();

        return new HashSet<>(Arrays.asList(
                studyParticipant1, studyParticipant2
        ));
    }
}