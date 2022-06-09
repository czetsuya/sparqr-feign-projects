package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.services.pojos.PlatformUserEducation;
import com.sparqr.cx.iam.services.pojos.PlatformUserLanguage;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PlatformUserEducationService {

  CompletableFuture<PlatformUserEducation> createOrUpdate(String userAlias, Long id,
      PlatformUserEducation platformUserEducation);

  CompletableFuture<Void> delete(Long id);

  CompletableFuture<List<PlatformUserEducation>> list(String userAlias);
}
