package nuri.nuri_server.domain.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuri.nuri_server.global.entity.BaseEntity;

@Entity
@Table(name = "tbl_user_agreement")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAgreementEntity extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
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

    @Builder(builderMethodName = "userAgreeBuilder")
    public UserAgreementEntity(
            UserEntity user,
            Boolean agreedTermsOfService,
            Boolean agreedPrivacyCollection,
            Boolean agreedPrivacyThirdParty,
            Boolean agreedIdentityAgencyTerms,
            Boolean agreedIdentityPrivacyDelegate,
            Boolean agreedIdentityUniqueInfo,
            Boolean agreedIdentityProviderTerms
    ) {
        this.user = user;
        this.agreedTermsOfService = agreedTermsOfService;
        this.agreedPrivacyCollection = agreedPrivacyCollection;
        this.agreedPrivacyThirdParty = agreedPrivacyThirdParty;
        this.agreedIdentityAgencyTerms = agreedIdentityAgencyTerms;
        this.agreedIdentityPrivacyDelegate = agreedIdentityPrivacyDelegate;
        this.agreedIdentityUniqueInfo = agreedIdentityUniqueInfo;
        this.agreedIdentityProviderTerms = agreedIdentityProviderTerms;
    }
}
