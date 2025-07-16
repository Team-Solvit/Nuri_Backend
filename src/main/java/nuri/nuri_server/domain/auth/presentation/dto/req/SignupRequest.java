package nuri.nuri_server.domain.auth.presentation.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SignupRequest(
    @NotBlank(message = "유저명(name)은 필수 항목입니다.")
    String name,

    @NotBlank(message = "유저 아이디(id)는 필수 항목입니다.")
    String id,

    @NotBlank(message = "유저 이메일(email)은 필수 항목입니다.")
    String email,

    @NotBlank(message = "유저 비밀번호(password)는 필수 항목입니다.")
    String password,

    @NotNull(message = "유저 국적(country)은 필수 항목입니다.")
    UUID country,

    @AssertTrue(message = "서비스 이용약관에 동의해야 합니다.")
    @JsonProperty("agreed_terms_of_service")
    Boolean agreedTermsOfService,

    @AssertTrue(message = "개인정보 수집 이용 동의에 동의해야 합니다.")
    @JsonProperty("agreed_privacy_collection")
    Boolean agreedPrivacyCollection,

    @AssertTrue(message = "개인정보 제 3자 제공 동의에 동의해야 합니다.")
    @JsonProperty("agreed_privacy_third_party")
    Boolean agreedPrivacyThirdParty,

    @AssertTrue(message = "본인 확인 서비스 이용약관에 동의해야 합니다.")
    @JsonProperty("agreed_identity_agency_terms")
    Boolean agreedIdentityAgencyTerms,

    @AssertTrue(message = "개인정보 수집 이용 및 위탁 동의에 동의해야 합니다.")
    @JsonProperty("agreed_identity_privacy_delegate")
    Boolean agreedIdentityPrivacyDelegate,

    @AssertTrue(message = "고유식별정보 처리 동의 사항에 동의해야 합니다.")
    @JsonProperty("agreed_identity_unique_info")
    Boolean agreedIdentityUniqueInfo,

    @AssertTrue(message = "본인확인 서비스 이용약관에 동의해야 합니다.")
    @JsonProperty("agreed_identity_provider_terms")
    Boolean agreedIdentityProviderTerms
) {}