package com.sparqr.cx.gig.web.controllers;

import static com.sparqr.cx.gig.api.EndpointConstants.PATH_GIG_SHORT;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_GIG_ID;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_ID;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_USER_ID;

import com.sparqr.cx.gig.api.EndpointConstants;
import com.sparqr.cx.gig.api.dtos.inbound.UpsertGigDto;
import com.sparqr.cx.gig.api.dtos.outbound.GigDto;
import com.sparqr.cx.gig.api.dtos.inbound.UpdateGigStatusDto;
import com.sparqr.cx.gig.mappers.Service2WebMapper;
import com.sparqr.cx.gig.mappers.Web2ServiceMapper;
import com.sparqr.cx.gig.services.GigService;
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
public class GigController {

  private final GigService gigService;
  private final Service2WebMapper service2WebMapper;
  private final Web2ServiceMapper web2ServiceMapper;

  /**
   * Returns a list of gigs.
   *
   * @param userId currently logged user
   * @return list of gigs for the given user
   */
  @GetMapping(path = EndpointConstants.PATH_GIGS, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<List<GigDto>> list(@PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId) {

    return gigService.list(userId).thenApply(service2WebMapper::toGigDto);
  }

  /**
   * Retrieves the gig information given an id.
   *
   * @param gigId id of the gig
   * @return detail of the gig
   */
  @GetMapping(path = PATH_GIG_SHORT + "/{" + PVAR_GIG_ID + "}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<GigDto> findById(@PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId) {

    return gigService.findById(gigId).thenApply(service2WebMapper::toGigDto);
  }

  /**
   * Creates gig for a given user.
   *
   * @param userId currently logged user
   * @param gigDto gig information
   * @return the created entity
   */
  @PostMapping(path = EndpointConstants.PATH_GIGS, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public CompletableFuture<GigDto> create(@PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @Valid @RequestBody UpsertGigDto gigDto) {

    return gigService.createOrUpdate(userId, null, web2ServiceMapper.toGig(gigDto))
        .thenApply(service2WebMapper::toGigDto);
  }

  /**
   * Updates a gig given an id.
   *
   * @param userId currently logged user
   * @param id     id of the gig to be updated
   * @param gigDto updated gig information
   * @return the updated entity
   */
  @PutMapping(path = EndpointConstants.PATH_GIGS + "/{" + PVAR_ID + "}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<GigDto> update(@PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_ID) @Valid Long id, @Valid @RequestBody UpsertGigDto gigDto) {

    return gigService.createOrUpdate(null, id, web2ServiceMapper.toGig(gigDto)).thenApply(service2WebMapper::toGigDto);
  }

  /**
   * Deletes a gig given an id.
   *
   * @param userId currently logged user
   * @param id     the id of the gig to be deleted
   * @return CompletableFuture
   */
  @DeleteMapping(path = EndpointConstants.PATH_GIGS + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public CompletableFuture<Void> delete(@PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_ID) @Valid Long id) {

    return gigService.delete(id);
  }


  /**
   * Check whether this gig is still valid so that orders can be placed.
   *
   * @param id id of the gig
   * @return true if this gig is valid
   */
  @GetMapping(path = PATH_GIG_SHORT + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<Boolean> validateById(@PathVariable(name = PVAR_ID) @Valid @NotNull Long id) {
    return gigService.validateById(id);
  }

  /**
   * Update the status of a gig with the given id.
   *
   * @param id id of the gig to be updated
   * @return Void
   */
  @PostMapping(path = PATH_GIG_SHORT + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<Void> updateStatus(@PathVariable(name = PVAR_ID) @Valid @NotNull Long id,
      @RequestBody @Valid @NotNull UpdateGigStatusDto updateGigStatusDto) {
    return gigService.updateStatus(id, updateGigStatusDto.getStatus());
  }
}
