package com.sparqr.cx.iam.fixtures;

import com.sparqr.cx.iam.api.dtos.inbound.MapUpdateOrCreateIfAbsentRequest;
import com.sparqr.cx.iam.persistence.entities.PlatformSsoEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;

public final class PlatformSsoFixtures {

  public static final String IDENTITY_PROVIDER = "Google";
  public static final String EMAIL = "czetsuya@gmail.com";
  public static final String EXTERNAL_REF = "G-001";
  public static final String FIRSTNAME = "Edward";
  public static final String LASTNAME = "Legaspi";

  public static MapUpdateOrCreateIfAbsentRequest getPlatformUserInboundDto() {

    return MapUpdateOrCreateIfAbsentRequest.builder()
        .identityProvider(IDENTITY_PROVIDER)
        .email(EMAIL)
        .externalRef(EXTERNAL_REF)
        .firstName(FIRSTNAME)
        .lastName(LASTNAME)
        .build();
  }

  public static PlatformUserEntity getPlatformUserEntity() {

    return PlatformUserEntity.builder()
        .email(EMAIL)
        .firstName(FIRSTNAME)
        .lastName(LASTNAME)
        .build();
  }

  public static PlatformSsoEntity getPlatformOpenId() {

    return PlatformSsoEntity.builder()
        .identityProvider(IDENTITY_PROVIDER)
        .externalRef(EXTERNAL_REF)
        .build();
  }

  public static PlatformSsoEntity getPlatformOpenIdWithUser() {

    PlatformSsoEntity platformOpenId = PlatformSsoEntity.builder()
        .identityProvider(IDENTITY_PROVIDER)
        .externalRef(EXTERNAL_REF)
        .build();

    PlatformUserEntity platformUser = getPlatformUserEntity();

    platformOpenId.setPlatformUser(platformUser);

    return platformOpenId;
  }
}
