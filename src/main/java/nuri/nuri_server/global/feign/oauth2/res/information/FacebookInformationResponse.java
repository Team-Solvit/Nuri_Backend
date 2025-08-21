package nuri.nuri_server.global.feign.oauth2.res.information;

public record FacebookInformationResponse(
        String id,
        String name,
        Picture picture
) {
    public record Picture(
            PictureData data
    ) {
        public record PictureData(
                int height,
                boolean is_silhouette,
                String url,
                int width
        ) { }
    }
}
