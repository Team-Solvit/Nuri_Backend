package nuri.nuri_server.domain.auth.oauth2.infra.client.dto;

import lombok.Builder;

@Builder
public record OAuth2InformationResponse(
        String id,
        String name,
        String profile
) {}
