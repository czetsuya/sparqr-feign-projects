package com.sparqr.cx.gig.web.controllers;

import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_GIG_ID;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_ID;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_USER_ID;

import com.sparqr.cx.gig.api.EndpointConstants;
import com.sparqr.cx.gig.api.dtos.commons.GigGalleryItemDto;
import com.sparqr.cx.gig.mappers.Service2WebMapper;
import com.sparqr.cx.gig.mappers.Web2ServiceMapper;
import com.sparqr.cx.gig.services.GigGalleryItemService;
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

  private final GigGalleryItemService gigGalleryItemService;
  private final Service2WebMapper service2WebMapper;
  private final Web2ServiceMapper web2ServiceMapper;

  /**
   * Returns a list of gallery items.
   *
   * @param userId currently logged user
   * @param gigId  id of the gig
   * @return list of gig gallery items for a given gig
   */
  @GetMapping(
      path = EndpointConstants.PATH_GIG_GALLERY_ITEMS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<List<GigGalleryItemDto>> list(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId) {

    return gigGalleryItemService.list(gigId)
        .thenApply(service2WebMapper::toGigGalleryItemDto);
  }

  /**
   * Creates a gig gallery item for a given gig.
   *
   * @param userId            currently logged user
   * @param gigId             id of the gig
   * @param gigGalleryItemDto gig gallery item information
   * @return the created entity
   */
  @PostMapping(
      path = EndpointConstants.PATH_GIG_GALLERY_ITEMS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public CompletableFuture<GigGalleryItemDto> create(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @Valid @RequestBody GigGalleryItemDto gigGalleryItemDto) {

    return gigGalleryItemService.createOrUpdate(gigId, null,
            web2ServiceMapper.toGigGalleryItem(gigGalleryItemDto))
        .thenApply(service2WebMapper::toGigGalleryItemDto);
  }

  /**
   * Updates a gig gallery item given an id.
   *
   * @param userId            currently logged user
   * @param gigId             id of the gig
   * @param id                id of the gig gallery item to be updated
   * @param gigGalleryItemDto updated gig information
   * @return the updated entity
   */
  @PutMapping(
      path = EndpointConstants.PATH_GIG_GALLERY_ITEMS + "/{" + PVAR_ID + "}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<GigGalleryItemDto> update(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @PathVariable(name = PVAR_ID) @Valid Long id,
      @Valid @RequestBody GigGalleryItemDto gigGalleryItemDto) {

    return gigGalleryItemService.createOrUpdate(null, id,
            web2ServiceMapper.toGigGalleryItem(gigGalleryItemDto))
        .thenApply(service2WebMapper::toGigGalleryItemDto);
  }

  /**
   * Deletes a gig gallery item given an id.
   *
   * @param userId currently logged user
   * @param gigId  id of a gig
   * @param id     the id of the gig gallery item to be deleted
   * @return CompletableFuture
   */
  @DeleteMapping(
      path = EndpointConstants.PATH_GIG_GALLERY_ITEMS + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public CompletableFuture<Void> delete(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @PathVariable(name = PVAR_ID) @Valid Long id) {

    return gigGalleryItemService.delete(id);
  }
}
