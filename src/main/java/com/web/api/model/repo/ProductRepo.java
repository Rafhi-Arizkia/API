package com.web.api.model.repo;

import com.web.api.model.entities.ProductEntities;
import com.web.api.model.entities.SupplierEntities;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntities, Long> {
    //    JpaQuery
    @Query("SELECT p.supplierProduct FROM ProductEntities p where p.productId =  :productId")
    Set<SupplierEntities> findProductEntitiesByProductId(@Param("productId") Long productId);

    //     query tersebut mengambil daftar supplierProduct dari ProductEntities dengan productId
    //     tertentu dan mengembalikan daftar SupplierEntities yang terkait dengan produk tersebut.

    @Query("SELECT p from ProductEntities p where p.productName = :name")
    public ProductEntities findProductByName(@PathParam("name") String name);

    @Query("SELECT p from ProductEntities p where p.productName LIKE :name")
    public List<ProductEntities> findProductByProductNameLike(@PathParam("name") String name);

    @Query("SELECT p from ProductEntities p where p.categoryProduct.categoryId = :categoryId")
    public List<ProductEntities> findProductByCategory(@PathParam("categoryId") Long categoryId);

    @Query("SELECT p from ProductEntities p where :supplierEntities MEMBER OF p.supplierProduct")
    public List<ProductEntities> findProductEntitiesBySupplierProduct(@PathParam("supplierEntities") SupplierEntities supplierEntities);

    //   Menampilkan semua data product
    @Override
    List<ProductEntities> findAll();
}
