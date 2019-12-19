package woowahan.anifarm.tecolearning.study.domain;

import lombok.Getter;

@Getter
public enum StudyStatus {
    RECRUITING("recruiting"),
    ONGOING("ongoing"),
    FINISHED("finished");

    private String name;

    StudyStatus(String name) {
        this.name = name;
    }
}
