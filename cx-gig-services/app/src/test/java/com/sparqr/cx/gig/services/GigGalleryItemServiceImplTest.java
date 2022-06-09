package com.sparqr.cx.gig.services;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparqr.cx.gig.mappers.Persistence2ModelMapper;
import com.sparqr.cx.gig.mappers.Service2PersistenceMapper;
import com.sparqr.cx.gig.persistence.entities.GigEntity;
import com.sparqr.cx.gig.persistence.entities.GigGalleryItemEntity;
import com.sparqr.cx.gig.persistence.repositories.GigGalleryItemRepository;
import com.sparqr.cx.gig.persistence.repositories.GigRepository;
import com.sparqr.cx.gig.services.pojos.GigGalleryItem;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class GigGalleryItemServiceImplTest {

  private GigGalleryItemService gigGalleryItemService;

  @Mock
  private GigRepository gigRepository;

  @Mock
  private GigGalleryItemRepository gigGalleryItemRepository;

  @Mock
  private Persistence2ModelMapper persistence2ModelMapper;

  @Mock
  private Service2PersistenceMapper service2PersistenceMapper;

  @BeforeEach
  void setUp() {

    gigGalleryItemService = new GigGalleryItemServiceImpl(gigRepository, gigGalleryItemRepository,
        persistence2ModelMapper, service2PersistenceMapper);
  }

  @Test
  @SneakyThrows
  void shouldThrowAnExceptionWhenIdAndGigIdIsNull() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> gigGalleryItemService.createOrUpdate(null, null, GigGalleryItem.builder().build()).get())
        .withMessage("gigId cannot be null");
  }

  @Test
  @SneakyThrows
  void shouldThrowAnExceptionWhenGalleryItemIsNull() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> gigGalleryItemService.createOrUpdate(1L, 1L, null))
        .withMessage("gigGalleryItem cannot be null");
  }

  @Test
  @SneakyThrows
  void shouldSaveWhenGalleryItemIdIsNull() {

    Long gigId = 1L;
    GigEntity gigEntity = GigEntity.builder().build();
    GigGalleryItem galleryItem = GigGalleryItem.builder().build();
    GigGalleryItemEntity gigGalleryItemEntity = GigGalleryItemEntity.builder().build();

    when(gigRepository.findById(gigId)).thenReturn(Optional.ofNullable(gigEntity));
    when(service2PersistenceMapper.toGigGalleryItemEntity(galleryItem)).thenReturn(gigGalleryItemEntity);
    gigGalleryItemEntity.setGig(gigEntity);
    when(gigGalleryItemRepository.save(gigGalleryItemEntity)).thenReturn(gigGalleryItemEntity);

    gigGalleryItemService.createOrUpdate(gigId, null, galleryItem).get();

    verify(service2PersistenceMapper, times(1)).toGigGalleryItemEntity(galleryItem);
    verify(gigGalleryItemRepository, times(1)).save(gigGalleryItemEntity);
    verify(persistence2ModelMapper, times(1)).toGigGalleryItem(gigGalleryItemEntity);
  }

  @Test
  @SneakyThrows
  void shouldUpdateWhenGalleryItemIdIsNotNull() {

    Long gigId = 1L;
    Long userId = 1L;
    GigGalleryItem galleryItem = GigGalleryItem.builder().build();
    GigGalleryItemEntity gigGalleryItemEntity = GigGalleryItemEntity.builder().build();

    when(gigGalleryItemRepository.findById(gigId)).thenReturn(Optional.of(gigGalleryItemEntity));
    when(gigGalleryItemRepository.save(gigGalleryItemEntity)).thenReturn(gigGalleryItemEntity);

    gigGalleryItemService.createOrUpdate(userId, gigId, galleryItem).get();

    verify(gigGalleryItemRepository, times(1)).findById(gigId);
    verify(service2PersistenceMapper, times(1)).toGigGalleryItemEntity(any(), any());
    verify(gigGalleryItemRepository, times(1)).save(gigGalleryItemEntity);
    verify(persistence2ModelMapper, times(1)).toGigGalleryItem(gigGalleryItemEntity);
  }

  @Test
  @SneakyThrows
  void shouldDeleteGivenAnId() {

    Long gigId = 1L;
    gigGalleryItemService.delete(gigId).get();

    verify(gigGalleryItemRepository, times(1)).deleteById(gigId);
  }

  @SneakyThrows
  @Test
  void shouldListOk() {

    Long gigId = 1L;
    List<GigGalleryItemEntity> gigGalleryItemEntities = Arrays.asList(
        GigGalleryItemEntity.builder().id(1L).build(),
        GigGalleryItemEntity.builder().id(2L).build()
    );
    List<GigGalleryItem> gigGalleryItems = Arrays.asList(
        GigGalleryItem.builder().id(1L).build(),
        GigGalleryItem.builder().id(2L).build()
    );

    when(gigGalleryItemRepository.findByGigId(gigId)).thenReturn(gigGalleryItemEntities);
    when(persistence2ModelMapper.toGigGalleryItem(gigGalleryItemEntities)).thenReturn(gigGalleryItems);

    List<GigGalleryItem> gigGalleryItemsResult = gigGalleryItemService.list(gigId).get();
    assertThat(gigGalleryItemsResult.size()).isEqualTo(2);
  }
}