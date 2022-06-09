package com.sparqr.cx.iam.client.proxies;

import static com.sparqr.cx.iam.api.EndpointConstants.PVAR_ALIAS;

import com.sparqr.cx.iam.api.EndpointConstants;
import com.sparqr.cx.iam.api.dtos.inbound.MapUpdateOrCreateIfAbsentRequest;
import com.sparqr.cx.iam.api.dtos.inbound.UpdateAliasDto;
import com.sparqr.cx.iam.api.dtos.inbound.UpdateUserDetailDto;
import com.sparqr.cx.iam.api.dtos.outbound.PlatformUserDto;
import com.sparqr.cx.iam.api.dtos.outbound.UserDetailDto;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Currently CompletableFuture is not supported.
 */
@FeignClient(name = "platformUserProxy", url = "${app.cx.client.iam-services.url}")
public interface PlatformUserProxy {

  @PostMapping(
      path = EndpointConstants.PATH_USERS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  PlatformUserDto mapUpdateOrCreateIfAbsent(
      @Valid @RequestBody MapUpdateOrCreateIfAbsentRequest mapUpdateOrCreateIfAbsentRequest);

  @PutMapping(
      path = EndpointConstants.PATH_USER_PROFILE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  UserDetailDto updateOrCreateIfAbsent(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank String alias,
      @Valid @NotNull @RequestBody UpdateUserDetailDto updateUserDetailDto);

  @GetMapping(
      path = EndpointConstants.PATH_USER_PROFILE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  UserDetailDto getUserProfile(
      @PathVariable(name = PVAR_ALIAS) @Valid @NotBlank String alias);

  @PutMapping(
      path = EndpointConstants.PATH_USER_ALIAS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  Void updateAlias(@PathVariable(name = PVAR_ALIAS) @Valid @NotBlank String alias,
      @RequestBody @Valid @NotNull UpdateAliasDto updateAliasDto);
}