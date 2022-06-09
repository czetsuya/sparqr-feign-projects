package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.services.pojos.PlatformUserLanguage;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PlatformUserLanguageService {

  CompletableFuture<PlatformUserLanguage> createOrUpdate(String userAlias, Long id,
      PlatformUserLanguage platformUserCertification);

  CompletableFuture<Void> delete(Long id);

  CompletableFuture<List<PlatformUserLanguage>> list(String userAlias);
}
