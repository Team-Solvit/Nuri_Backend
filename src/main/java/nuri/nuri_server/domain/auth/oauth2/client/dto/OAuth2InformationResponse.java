package nuri.nuri_server.domain.auth.oauth2.client.dto;

import lombok.Builder;

@Builder
public record OAuth2InformationResponse(
        String id,
        String name,
        String profile
) {}
