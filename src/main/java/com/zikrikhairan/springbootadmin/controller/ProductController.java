package com.zikrikhairan.springbootadmin.controller;

import com.zikrikhairan.springbootadmin.dto.ProductRequest;
import com.zikrikhairan.springbootadmin.entity.Product;
import com.zikrikhairan.springbootadmin.exception.ErrorException;
import com.zikrikhairan.springbootadmin.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Product>> getAllProduct(@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
                                                       @RequestParam(value = "orderDir", defaultValue = "asc") String orderDir,
                                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        Page<Product> productPage = productService.getAllProducts(orderBy, orderDir, page, size);
        return ResponseEntity.ok(productPage);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        try{
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (ErrorException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductRequest request){
        try{
            Product product = productService.addProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (ErrorException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@PathVariable String id,
                                                 @RequestBody ProductRequest request){
        try{
            Product product = productService.updateProduct(id, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (ErrorException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> deleteProductById(@PathVariable String id){
        try{
            Product product = productService.deleteProduct(id);
            return ResponseEntity.ok(product);
        } catch (ErrorException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
