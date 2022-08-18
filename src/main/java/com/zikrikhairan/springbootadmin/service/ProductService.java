package com.zikrikhairan.springbootadmin.service;

import com.zikrikhairan.springbootadmin.dto.ProductRequest;
import com.zikrikhairan.springbootadmin.entity.Product;
import com.zikrikhairan.springbootadmin.exception.ErrorException;
import com.zikrikhairan.springbootadmin.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("productService")
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Page<Product> getAllProducts(String orderBy, String orderDir, Integer page, Integer size){
        Sort sort = Sort.by(Sort.Direction.ASC, orderBy);
        if(orderDir.toUpperCase().equals(Sort.Direction.DESC.name())) {
            sort = Sort.by(Sort.Direction.DESC, orderBy);
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    public Product getProductById(String id) throws ErrorException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new ErrorException("ERROR001", "Data Not Found!");
        }
        return  productOptional.get();
    }

    public Product addProduct(ProductRequest request) throws ErrorException {
        if(productRepository.existsByBarcode(request.getBarcode())){
            throw new ErrorException("ERROR002", "Barcode is exists!");
        }
        Product newProduct = Product.builder()
                .name(request.getName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .barcode(request.getBarcode())
                .build();
        return productRepository.save(newProduct);
    }

    public Product updateProduct(String id, ProductRequest request) throws ErrorException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new ErrorException("ERROR001", "Data Not Found!");
        }
        Product oldProduct = product.get();
        oldProduct.setName(request.getName());
        oldProduct.setPrice(request.getPrice());
        oldProduct.setQuantity(request.getQuantity());
        oldProduct.setBarcode(request.getBarcode());
        return productRepository.save(oldProduct);
    }

    public Product deleteProduct(String id) throws ErrorException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new ErrorException("ERROR001", "Data Not Found!");
        }
        productRepository.delete(product.get());
        return product.get();
    }
}
