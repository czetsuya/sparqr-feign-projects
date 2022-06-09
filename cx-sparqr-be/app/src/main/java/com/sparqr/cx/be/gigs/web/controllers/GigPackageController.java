package com.sparqr.cx.be.gigs.web.controllers;

import static com.sparqr.cx.be.iam.EndpointConstants.PATH_GIG_PACKAGES;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_GIG_ID;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_ID;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_USER_ID;

import com.sparqr.cx.gig.api.dtos.commons.GigPackageDto;
import com.sparqr.cx.gig.client.proxies.GigPackageProxyAsync;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class GigPackageController {

  private final GigPackageProxyAsync gigPackageProxyAsync;

  @GetMapping(
      path = PATH_GIG_PACKAGES,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<List<GigPackageDto>> list(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId) {

    return gigPackageProxyAsync.list(userId, gigId);
  }

  @PostMapping(
      path = PATH_GIG_PACKAGES,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public CompletableFuture<GigPackageDto> create(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @Valid @RequestBody GigPackageDto gigPackageDto) {

    return gigPackageProxyAsync.create(userId, gigId, gigPackageDto);
  }

  @PutMapping(
      path = PATH_GIG_PACKAGES + "/{" + PVAR_ID + "}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<GigPackageDto> update(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @PathVariable(name = PVAR_ID) @Valid Long id,
      @Valid @RequestBody GigPackageDto gigPackageDto) {

    return gigPackageProxyAsync.update(userId, gigId, id, gigPackageDto);
  }

  @DeleteMapping(
      path = PATH_GIG_PACKAGES + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public CompletableFuture<Void> delete(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @PathVariable(name = PVAR_ID) @Valid Long id) {

    return gigPackageProxyAsync.delete(userId, gigId, id);
  }
}
