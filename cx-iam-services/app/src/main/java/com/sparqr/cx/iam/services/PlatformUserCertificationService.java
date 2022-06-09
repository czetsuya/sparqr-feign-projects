package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.services.pojos.PlatformUserCertification;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PlatformUserCertificationService {

  CompletableFuture<PlatformUserCertification> createOrUpdate(String userAlias, Long id,
      PlatformUserCertification platformUserCertification);

  CompletableFuture<Void> delete(Long id);

  CompletableFuture<List<PlatformUserCertification>> list(String userAlias);
}
