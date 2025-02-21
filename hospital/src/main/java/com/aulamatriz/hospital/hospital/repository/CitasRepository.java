package com.aulamatriz.hospital.hospital.repository;

import com.aulamatriz.hospital.hospital.entities.CitasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CitasRepository extends JpaRepository<CitasEntity,Integer> {

}

