package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.persistence.entities.PlatformUserProfileEntity;
import com.sparqr.cx.iam.services.pojos.PlatformUserProfile;
import java.util.concurrent.CompletableFuture;

public interface PlatformUserProfileService {

  CompletableFuture<PlatformUserProfileEntity> updateOrCreateIfAbsent(
      String alias,
      PlatformUserProfile platformUserProfile);

  CompletableFuture<PlatformUserProfile> getProfile(String userAlias);
}
