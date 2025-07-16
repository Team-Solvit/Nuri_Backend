package nuri.nuri_server.global.feign;


import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.global.feign.exception.FeignClientInvalidRequestException;
import nuri.nuri_server.global.feign.exception.FeignClientResponseException;
import nuri.nuri_server.global.feign.exception.FeignClientUnauthorizedException;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        String reason = response.reason() != null ? response.reason() : "없음";


        String baseMessage = String.format(
                "FeignClient 오류 : 메서드 : %s | 상태코드 : %d | 사유 : %s",
                methodKey, status, reason
        );

        if(response.status() == 401) {
            log.error("{} → 인증 실패", baseMessage);
            return new FeignClientUnauthorizedException(
                    "인증이 되지 않았습니다.",
                    HttpStatus.UNAUTHORIZED
            );
        }
        else if(response.status() >= 400 && response.status() < 500) {
            log.error("{} → 잘못된 요청", baseMessage);
            return new FeignClientInvalidRequestException(
                    "잘못된 요청이 들어왔습니다",
                    HttpStatus.BAD_REQUEST
            );
        }
        else {
            log.error("{} → 서버 오류", baseMessage);
            return new FeignClientResponseException(
                    "외부 서버에서 오류가 발생했습니다",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
