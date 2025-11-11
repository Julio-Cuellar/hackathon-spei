package com.cuellar.crudservice.repository;

import com.cuellar.crudservice.domain.ObjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectRepository extends CrudRepository<ObjectEntity, Long> {
    List<ObjectEntity> findByUserUserUUID(String userUUID);
}