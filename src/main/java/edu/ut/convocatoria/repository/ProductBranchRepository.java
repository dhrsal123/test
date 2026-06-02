package edu.ut.convocatoria.repository;

import edu.ut.convocatoria.domain.entity.ProductBranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductBranchRepository extends JpaRepository<ProductBranchEntity, UUID> {

    Optional<ProductBranchEntity> findByBranchIdAndProductId(UUID branchId, UUID productId);

}
