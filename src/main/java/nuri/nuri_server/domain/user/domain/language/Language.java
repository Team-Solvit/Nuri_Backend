package nuri.nuri_server.domain.user.domain.language;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {
    KOREAN("한국어"),
    ENGLISH("영어");
    private final String name;
}
