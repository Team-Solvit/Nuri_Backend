package nuri.nuri_server.domain.chat.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Sender {
    private String name;
    private String profile;
}
