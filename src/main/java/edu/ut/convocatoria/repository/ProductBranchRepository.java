package edu.ut.convocatoria.repository;

import edu.ut.convocatoria.domain.entity.MaxStockProductProjection;
import edu.ut.convocatoria.domain.entity.ProductBranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductBranchRepository extends JpaRepository<ProductBranchEntity, UUID> {

    @Query(value = """
            WITH BranchProducts AS (
                SELECT b.id as branchId,
                       p.id as productId,
                       p.name,
                       p.sku,
                       p.description,
                       p.price,
                       ROW_NUMBER() OVER (PARTITION BY b.id ORDER BY pb.stock DESC) as ranking
                FROM product_branch_entity pb
                         JOIN branch_entity b ON pb.branch_id = b.id
                         JOIN product_entity p ON pb.product_id = p.id
                WHERE b.franchise_id = :franchiseId
            )
            SELECT branchId,
                   productId,
                   name,
                   sku,
                   description,
                   price
            FROM BranchProducts
            WHERE ranking = 1
            """, nativeQuery = true)
    Optional<List<MaxStockProductProjection>> findMaxStockByFranchiseId(@Param("franchiseId") UUID franchiseId);

    Optional<ProductBranchEntity> findByBranchIdAndProductId(UUID branchId, UUID productId);

}
