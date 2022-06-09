package com.sparqr.cx.be.gigs.web.controllers;

import static com.sparqr.cx.be.iam.EndpointConstants.PATH_GIGS;
import static com.sparqr.cx.be.iam.EndpointConstants.PATH_GIG_SHORT;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_GIG_ID;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_ID;
import static com.sparqr.cx.be.iam.EndpointConstants.PVAR_USER_ID;

import com.sparqr.cx.be.gigs.services.GigService;
import com.sparqr.cx.gig.api.dtos.inbound.UpdateGigStatusDto;
import com.sparqr.cx.gig.api.dtos.inbound.UpsertGigDto;
import com.sparqr.cx.gig.api.dtos.outbound.GigDto;
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

  @GetMapping(path = PATH_GIGS, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<List<GigDto>> list(@PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId) {

    return gigService.list(userId);
  }

  @PostMapping(path = PATH_GIGS, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public CompletableFuture<GigDto> create(@PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @Valid @RequestBody UpsertGigDto gigDto) {

    return gigService.create(userId, gigDto);
  }

  @PutMapping(path = PATH_GIGS + "/{" + PVAR_ID + "}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<GigDto> update(@PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_ID) @Valid Long id, @Valid @RequestBody UpsertGigDto gigDto) {

    return gigService.update(userId, id, gigDto);
  }

  @DeleteMapping(path = PATH_GIGS + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public CompletableFuture<Void> delete(@PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_ID) @Valid Long id) {

    return gigService.delete(userId, id);
  }

  @GetMapping(path = PATH_GIG_SHORT + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<Boolean> validateById(@PathVariable(name = PVAR_ID) @Valid @NotNull Long id) {

    return gigService.validateById(id);
  }

  @PostMapping(path = PATH_GIG_SHORT + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<Void> updateStatus(@PathVariable(name = PVAR_ID) @Valid @NotNull Long id,
      @RequestBody @Valid @NotNull UpdateGigStatusDto updateGigStatusDto) {

    return gigService.updateStatus(id, updateGigStatusDto);
  }

  @GetMapping(path = PATH_GIG_SHORT + "/{" + PVAR_GIG_ID + "}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public CompletableFuture<GigDto> findById(@PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId) {

    return gigService.findById(gigId);
  }
}
