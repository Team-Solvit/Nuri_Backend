package nuri.nuri_server.global.feign;


import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.global.feign.exception.FeignClientInvalidRequestException;
import nuri.nuri_server.global.feign.exception.FeignClientResponseException;
import nuri.nuri_server.global.feign.exception.FeignClientUnauthorizedException;
import org.springframework.http.HttpStatus;

// 일괄 처리 필요
@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        String reason = response.reason();

        String baseMessage = String.format("[Feign 오류] 메서드: %s | 상태코드: %d | 사유: %s",
                methodKey, status, reason != null ? reason : "없음");

        if(response.status() == 401) {
            log.error(baseMessage + " → 인증 실패");
            throw new FeignClientUnauthorizedException("인증이 되지 않았습니다.", HttpStatus.UNAUTHORIZED);
        }
        else if(response.status() >= 400 && response.status() < 500) {
            log.error(baseMessage + " → 잘못된 요청");
            throw new FeignClientInvalidRequestException(
                    response.reason(),
                    HttpStatus.BAD_REQUEST
            );
        }
        else {
            log.error(baseMessage + " → 서버 오류");
            throw new FeignClientResponseException(
                    "FeignClient의 요청 에러코드 : " + response.status(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
