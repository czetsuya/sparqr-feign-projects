package com.sparqr.cx.iam.client.proxies;

import com.sparqr.cx.iam.api.dtos.inbound.MapUpdateOrCreateIfAbsentRequest;
import com.sparqr.cx.iam.api.dtos.inbound.UpdateAliasDto;
import com.sparqr.cx.iam.api.dtos.inbound.UpdateUserDetailDto;
import com.sparqr.cx.iam.api.dtos.outbound.PlatformUserDto;
import com.sparqr.cx.iam.api.dtos.outbound.UserDetailDto;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class PlatformUserProxyAsync {

  private final PlatformUserProxy platformUserProxy;

  @Async
  public CompletableFuture<PlatformUserDto> mapUpdateOrCreateIfAbsent(
      MapUpdateOrCreateIfAbsentRequest mapUpdateOrCreateIfAbsentRequest) {
    return CompletableFuture.completedFuture(platformUserProxy.mapUpdateOrCreateIfAbsent(
        mapUpdateOrCreateIfAbsentRequest));
  }

  @Async
  public CompletableFuture<UserDetailDto> updateOrCreateIfAbsent(String alias,
      UpdateUserDetailDto updateUserDetailDto) {
    return CompletableFuture.completedFuture(platformUserProxy.updateOrCreateIfAbsent(alias,
        updateUserDetailDto));
  }

  @Async
  public CompletableFuture<UserDetailDto> getUserProfile(
      @Valid @NotBlank String alias) {
    return CompletableFuture.completedFuture(platformUserProxy.getUserProfile(alias));
  }

  @Async
  public CompletableFuture<Void> updateAlias(
      @Valid @NotBlank String alias,
      @RequestBody @Valid @NotNull UpdateAliasDto updateAliasDto) {
    return CompletableFuture.completedFuture(platformUserProxy.updateAlias(alias, updateAliasDto));
  }
}
