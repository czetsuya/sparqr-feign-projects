package com.sparqr.cx.gig.persistence.entities;

import com.sparqr.cx.gig.persistence.entities.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gig_gallery_item")
@EqualsAndHashCode(callSuper = true)
public class GigGalleryItemEntity extends BaseEntity {

  @NotNull
  @ManyToOne
  @JoinColumn(name = "gig_id", nullable = false)
  private GigEntity gig;

  @Column(name = "image_url", length = 100, nullable = false)
  private String imageUrl;

  @Column(name = "description", length = 100)
  private String description;

  @Column(name = "sort_order")
  private Integer sortOrder;
}
