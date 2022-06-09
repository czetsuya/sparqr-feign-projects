package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.services.pojos.PlatformUser;
import java.util.concurrent.CompletableFuture;

public interface PlatformSsoService {

  CompletableFuture<PlatformUser> createIfNotExists(PlatformUser platformUser);
}
