package woowahan.anifarm.tecolearning.study.domain;

import lombok.Getter;
import woowahan.anifarm.tecolearning.study.domain.exception.StudyStatusNotFoundException;

import java.util.stream.Stream;

@Getter
public enum StudyStatus {
    RECRUITING("recruiting"),
    ONGOING("ongoing"),
    FINISHED("finished");

    private String name;

    StudyStatus(String name) {
        this.name = name;
    }

    public static StudyStatus of(String name) {
        return Stream.of(values())
                .filter(studyStatus -> studyStatus.nameIs(name))
                .findAny()
                .orElseThrow(StudyStatusNotFoundException::new);
    }

    private boolean nameIs(String name) {
        return this.name.equals(name);
    }
}
