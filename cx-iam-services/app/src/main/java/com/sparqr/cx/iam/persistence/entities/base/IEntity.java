package com.sparqr.cx.iam.persistence.entities.base;

import java.io.Serializable;

public interface IEntity {

  Serializable getId();

  boolean isTransient();
}