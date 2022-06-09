package com.sparqr.cx.be.gigs.web.controllers;

import static com.sparqr.cx.be.iam.EndpointConstants.PATH_GIG_GALLERY_ITEMS;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_GIG_ID;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_ID;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_USER_ID;

import com.sparqr.cx.gig.api.dtos.commons.GigGalleryItemDto;
import com.sparqr.cx.gig.client.proxies.GigGalleryItemProxyAsync;
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
public class GigGalleryItemController {

  private final GigGalleryItemProxyAsync gigGalleryItemProxyAsync;

  @GetMapping(
      path = PATH_GIG_GALLERY_ITEMS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<List<GigGalleryItemDto>> list(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId) {

    return gigGalleryItemProxyAsync.list(userId, gigId);
  }

  @PostMapping(
      path = PATH_GIG_GALLERY_ITEMS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public CompletableFuture<GigGalleryItemDto> create(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @Valid @RequestBody GigGalleryItemDto gigGalleryItemDto) {

    return gigGalleryItemProxyAsync.create(userId, gigId, gigGalleryItemDto);
  }

  @PutMapping(
      path = PATH_GIG_GALLERY_ITEMS + "/{" + PVAR_ID + "}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<GigGalleryItemDto> update(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @PathVariable(name = PVAR_ID) @Valid Long id,
      @Valid @RequestBody GigGalleryItemDto gigGalleryItemDto) {

    return gigGalleryItemProxyAsync.update(userId, gigId, id, gigGalleryItemDto);
  }

  @DeleteMapping(
      path = PATH_GIG_GALLERY_ITEMS + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public CompletableFuture<Void> delete(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @PathVariable(name = PVAR_ID) @Valid Long id) {

    return gigGalleryItemProxyAsync.delete(userId, gigId, id);
  }
}
