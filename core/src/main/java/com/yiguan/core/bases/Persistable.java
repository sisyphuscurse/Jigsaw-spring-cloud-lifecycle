package com.yiguan.core.bases;

import com.yiguan.core.persistence.Keyed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Optional;

public abstract class Persistable<B extends Persistable<B, E, K>, E extends Keyed<K>, K extends Serializable> {

  protected Optional<K> key;

  protected E internalState;

  @Autowired
  public void setRepository(CrudRepository<E, K> repository) {
    this.repository = repository;
  }

  protected CrudRepository<E, K> repository;

  @PostConstruct
  private void initialize() {
    if (null == internalState) {
      internalState = findOne(key.get());
    }
  }

  public final B save() {
    internalSave(this.internalState);
    postSave();
    return (B) this;
  }

  protected void postSave() {
  }

  protected E findOne(K k){
    return repository.findOne(k);
  }

  protected void internalSave(E entity) {
    repository.save(entity);
    key = Optional.of(key.orElse(internalState.getKey()));
  }


}