package com.sparqr.cx.iam.services;

import com.sparqr.cx.iam.services.pojos.PlatformUser;
import com.sparqr.cx.iam.services.pojos.UserDetail;
import java.util.concurrent.CompletableFuture;

public interface PlatformUserService {

  CompletableFuture<UserDetail> update(String userAlias, final UserDetail userDetail);

  CompletableFuture<PlatformUser> update(String userAlias, final PlatformUser platformUser);

  CompletableFuture<PlatformUser> getUser(String userAlias);

  CompletableFuture<Void> updateAlias(String alias, String alias1);
}
