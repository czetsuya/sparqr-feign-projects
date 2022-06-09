package com.sparqr.cx.gig.persistence.entities.base;

import java.io.Serializable;

public interface IEntity {

  Serializable getId();

  boolean isTransient();
}