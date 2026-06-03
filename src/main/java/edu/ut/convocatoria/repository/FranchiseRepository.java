package edu.ut.convocatoria.repository;

import edu.ut.convocatoria.domain.entity.FranchiseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FranchiseRepository extends JpaRepository<FranchiseEntity, UUID> {
}
