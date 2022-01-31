package com.vodafoneH2.vodh2.repository;

import com.vodafoneH2.vodh2.entity.Vehice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

// Annotation
@Repository

// Interface extending CrudRepository
public interface VehiceRepository extends CrudRepository<Vehice, Long> {
    List<Vehice> findAll();

    @Transactional
    @Modifying
    @Query("delete from Vehice v where v.slot=:id")
    void deleteBySlot(String id);
}
