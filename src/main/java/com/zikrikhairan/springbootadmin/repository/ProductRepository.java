package com.zikrikhairan.springbootadmin.repository;

import com.zikrikhairan.springbootadmin.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {
    boolean existsByBarcode(String barcode);
}
