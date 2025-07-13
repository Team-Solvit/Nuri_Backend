package nuri.nuri_server.global.feign;


import feign.Response;
import feign.codec.ErrorDecoder;
import nuri.nuri_server.global.feign.exception.FeignClientInvalidRequestException;
import nuri.nuri_server.global.feign.exception.FeignClientResponseException;
import nuri.nuri_server.global.feign.exception.FeignClientUnauthorizedException;
import org.springframework.http.HttpStatus;

public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        String reason = response.reason();

        String baseMessage = String.format("[Feign 오류] 메서드: %s | 상태코드: %d | 사유: %s",
                methodKey, status, reason != null ? reason : "없음");

        if(response.status() == 401) {
            throw new FeignClientUnauthorizedException(baseMessage + " → 인증 실패", HttpStatus.UNAUTHORIZED);
        }
        else if(response.status() >= 400 && response.status() < 500) {
            throw new FeignClientInvalidRequestException(
                    baseMessage + " → 잘못된 요청",
                    HttpStatus.BAD_REQUEST
            );
        }
        else {
            throw new FeignClientResponseException(
                    baseMessage + " → 서버 오류",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
