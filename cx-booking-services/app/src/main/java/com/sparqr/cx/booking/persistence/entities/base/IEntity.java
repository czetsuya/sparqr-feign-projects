package com.sparqr.cx.booking.persistence.entities.base;

import java.io.Serializable;

public interface IEntity {

  Serializable getId();

  boolean isTransient();
}