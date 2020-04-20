package woowahan.anifarm.tecolearning.study.service.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyStatus;
import woowahan.anifarm.tecolearning.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

class StudySummaryDtoTest {

    @Test
    @DisplayName("스터디 설명이 40자가 넘지 않는 경우 경우 텍스트를 그대로 되돌려준다.")
    void plainDescription() {
        User presenter = User.builder().build();
        String description = createDummyDescription(StudySummaryDto.MAX_SUMMARY_DESCRIPTION_LENGTH - 1);
        Study study = Study.builder()
                .status(StudyStatus.ONGOING)
                .presenter(presenter)
                .description(description)
                .build();

        StudySummaryDto studySummaryDto = StudySummaryDto.from(study, 0);

        assertThat(studySummaryDto.getSummary()).isEqualTo(description);
    }

    @Test
    @DisplayName("스터디 설명이 40자가 넘어가는 경우 ...으로 텍스트를 요약하여 되돌려준다.")
    void summarizeDescription() {
        User presenter = User.builder().build();
        String description = createDummyDescription(StudySummaryDto.MAX_SUMMARY_DESCRIPTION_LENGTH + 1);
        Study study = Study.builder()
                .status(StudyStatus.ONGOING)
                .presenter(presenter)
                .description(description)
                .build();

        StudySummaryDto studySummaryDto = StudySummaryDto.from(study, 0);

        assertThat(studySummaryDto.getSummary().length())
                .isEqualTo(StudySummaryDto.MAX_SUMMARY_DESCRIPTION_LENGTH
                        + StudySummaryDto.SUMMARY_DESCRIPTION_SUFFIX.length());
        assertThat(studySummaryDto.getSummary().substring(0, StudySummaryDto.MAX_SUMMARY_DESCRIPTION_LENGTH))
                .isEqualTo(description.substring(0, StudySummaryDto.MAX_SUMMARY_DESCRIPTION_LENGTH));
        assertThat(studySummaryDto.getSummary().endsWith(StudySummaryDto.SUMMARY_DESCRIPTION_SUFFIX)).isTrue();
    }

    private String createDummyDescription(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append("a");
        }
        return stringBuilder.toString();
    }
}