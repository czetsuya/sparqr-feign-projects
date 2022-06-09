package com.sparqr.cx.iam.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import com.sparqr.cx.iam.persistence.repositories.PlatformUserRepository;
import com.sparqr.cx.iam.services.exceptions.UserNotFoundException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class PlatformUserSkillServiceImplTest {

  private PlatformUserSkillService platformUserSkillService;

  @Mock
  private PlatformUserRepository platformUserRepository;

  @BeforeEach
  private void setup() {
    platformUserSkillService = new PlatformUserSkillServiceImpl(platformUserRepository);
  }

  @Test
  public void ShouldThrowUserNotFoundException() {

    String alias = "czetsuya";
    Mockito.when(platformUserRepository.findByAlias(alias)).thenReturn(Optional.empty());

    Assertions.assertThatExceptionOfType(ExecutionException.class)
        .isThrownBy(() -> {
          platformUserSkillService.createOrUpdate(alias, Set.of("Java")).get();
        })
        .withCause(new UserNotFoundException(alias));
  }

  @Test
  @SneakyThrows
  public void ShouldCreateSkillsToUserWithEmptySkills() {

    String alias = "czetsuya";
    Set<String> skills = Set.of("Java", "Spring");

    PlatformUserEntity platformUserEntity = PlatformUserEntity.builder()
        .alias(alias)
        .build();

    Mockito.when(platformUserRepository.findByAlias(alias)).thenReturn(Optional.of(platformUserEntity));

    platformUserSkillService.createOrUpdate(alias, skills).get();

    platformUserEntity.setSkills(skills);

    verify(platformUserRepository, times(1)).save(platformUserEntity);
  }
}
