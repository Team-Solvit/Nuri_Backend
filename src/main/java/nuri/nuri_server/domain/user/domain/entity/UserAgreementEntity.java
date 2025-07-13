package nuri.nuri_server.domain.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_agreementEntity")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAgreementEntity {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "agreed_terms_of_service")
    private Boolean agreedTermsOfService;

    @Column(name = "agreed_privacy_collection")
    private Boolean agreedPrivacyCollection;

    @Column(name = "agreed_privacy_third_party")
    private Boolean agreedPrivacyThirdParty;

    @Column(name = "agreed_identity_agency_terms")
    private Boolean agreedIdentityAgencyTerms;

    @Column(name = "agreed_identity_privacy_delegate")
    private Boolean agreedIdentityPrivacyDelegate;

    @Column(name = "agreed_identity_unique_info")
    private Boolean agreedIdentityUniqueInfo;

    @Column(name = "agreed_identity_provider_terms")
    private Boolean agreedIdentityProviderTerms;
}
