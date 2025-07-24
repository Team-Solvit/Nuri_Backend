package nuri.nuri_server.global.feign.oauth2.res.information;

public record GoogleInformationResponse(
        String sub,
        String name,
        String picture
) {}
