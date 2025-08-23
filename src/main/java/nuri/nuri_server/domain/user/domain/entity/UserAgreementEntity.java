package nuri.nuri_server.domain.user.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_user_agreement")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAgreementEntity {
    @Id
    @Column(name = "user_id")
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Column(name = "agreed_terms_of_service", nullable = false)
    private Boolean agreedTermsOfService;

    @Column(name = "agreed_privacy_collection", nullable = false)
    private Boolean agreedPrivacyCollection;

    @Column(name = "agreed_privacy_third_party", nullable = false)
    private Boolean agreedPrivacyThirdParty;

    @Column(name = "agreed_identity_agency_terms", nullable = false)
    private Boolean agreedIdentityAgencyTerms;

    @Column(name = "agreed_identity_privacy_delegate", nullable = false)
    private Boolean agreedIdentityPrivacyDelegate;

    @Column(name = "agreed_identity_unique_info", nullable = false)
    private Boolean agreedIdentityUniqueInfo;

    @Column(name = "agreed_identity_provider_terms", nullable = false)
    private Boolean agreedIdentityProviderTerms;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

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
