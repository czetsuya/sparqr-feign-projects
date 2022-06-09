package com.sparqr.cx.gig.web.controllers;

import static com.sparqr.cx.gig.api.EndpointConstants.PATH_GIG_PACKAGES;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_GIG_ID;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_ID;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_USER_ID;

import com.sparqr.cx.gig.api.dtos.commons.GigPackageDto;
import com.sparqr.cx.gig.mappers.Service2WebMapper;
import com.sparqr.cx.gig.mappers.Web2ServiceMapper;
import com.sparqr.cx.gig.services.GigPackageService;
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

  private final GigPackageService gigPackageService;
  private final Service2WebMapper service2WebMapper;
  private final Web2ServiceMapper web2ServiceMapper;

  /**
   * Returns a list of packages.
   *
   * @param userId currently logged user
   * @param gigId  id of the gig
   * @return list of gig package for a given gig
   */
  @GetMapping(
      path = PATH_GIG_PACKAGES,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<List<GigPackageDto>> list(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId) {

    return gigPackageService.list(gigId)
        .thenApply(service2WebMapper::toGigPackageDto);
  }

  /**
   * Creates a gig package for a given gig.
   *
   * @param userId        currently logged user
   * @param gigId         id of the gig
   * @param gigPackageDto gig package information
   * @return the created entity
   */
  @PostMapping(
      path = PATH_GIG_PACKAGES,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public CompletableFuture<GigPackageDto> create(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @Valid @RequestBody GigPackageDto gigPackageDto) {

    return gigPackageService.createOrUpdate(gigId, null,
            web2ServiceMapper.toGigPackage(gigPackageDto))
        .thenApply(service2WebMapper::toGigPackageDto);
  }

  /**
   * Updates a gig package given an id.
   *
   * @param userId        currently logged user
   * @param gigId         id of the gig
   * @param id            id of the gig package to be updated
   * @param gigPackageDto updated gig information
   * @return the updated entity
   */
  @PutMapping(
      path = PATH_GIG_PACKAGES + "/{" + PVAR_ID + "}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<GigPackageDto> update(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @PathVariable(name = PVAR_ID) @Valid Long id,
      @Valid @RequestBody GigPackageDto gigPackageDto) {

    return gigPackageService.createOrUpdate(null, id,
            web2ServiceMapper.toGigPackage(gigPackageDto))
        .thenApply(service2WebMapper::toGigPackageDto);
  }

  /**
   * Deletes a gig package given an id.
   *
   * @param userId currently logged user
   * @param gigId  id of a gig
   * @param id     the id of the gig package to be deleted
   * @return CompletableFuture
   */
  @DeleteMapping(
      path = PATH_GIG_PACKAGES + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public CompletableFuture<Void> delete(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId,
      @PathVariable(name = PVAR_ID) @Valid Long id) {

    return gigPackageService.delete(id);
  }
}
