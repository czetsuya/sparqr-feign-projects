package com.sparqr.cx.gig.client.proxies;

import static com.sparqr.cx.gig.api.EndpointConstants.PATH_GIG_SHORT;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_GIG_ID;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_ID;
import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_USER_ID;

import com.sparqr.cx.gig.api.EndpointConstants;
import com.sparqr.cx.gig.api.dtos.inbound.UpdateGigStatusDto;
import com.sparqr.cx.gig.api.dtos.inbound.UpsertGigDto;
import com.sparqr.cx.gig.api.dtos.outbound.GigDto;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "gigProxy", url = "${app.cx.client.gig-services.url}")
public interface GigProxy {

  @GetMapping(
      path = EndpointConstants.PATH_GIGS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  List<GigDto> list(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId);

  @PostMapping(
      path = EndpointConstants.PATH_GIGS,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  GigDto create(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @Valid @RequestBody UpsertGigDto gigDto);

  @PutMapping(
      path = EndpointConstants.PATH_GIGS + "/{" + PVAR_ID + "}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  GigDto update(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_ID) @Valid Long id,
      @Valid @RequestBody UpsertGigDto gigDto);

  @DeleteMapping(
      path = EndpointConstants.PATH_GIGS + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  Void delete(
      @PathVariable(name = PVAR_USER_ID) @Valid @NotNull Long userId,
      @PathVariable(name = PVAR_ID) @Valid Long id);

  @GetMapping(path = PATH_GIG_SHORT + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.OK)
  Boolean validateById(@PathVariable(name = PVAR_ID) @Valid @NotNull Long id);

  @GetMapping(path = PATH_GIG_SHORT + "/{" + PVAR_GIG_ID + "}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  GigDto findById(@PathVariable(name = PVAR_GIG_ID) @Valid @NotNull Long gigId);

  @PostMapping(path = PATH_GIG_SHORT + "/{" + PVAR_ID + "}")
  @ResponseStatus(value = HttpStatus.OK)
  Void updateStatus(@PathVariable(name = PVAR_ID) @Valid @NotNull Long id,
      @RequestBody @Valid @NotNull UpdateGigStatusDto updateGigStatusDto);
}
