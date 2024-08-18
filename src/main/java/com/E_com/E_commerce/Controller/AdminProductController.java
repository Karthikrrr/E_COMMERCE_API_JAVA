package com.E_com.E_commerce.Controller;

import com.E_com.E_commerce.Exception.ProductException;
import com.E_com.E_commerce.Request.CreateProductRequest;
import com.E_com.E_commerce.Responce.ApiResponse;
import com.E_com.E_commerce.Service.ProductService;
import com.E_com.E_commerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request){
            Product product = productService.createProduct(request);
            return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException{

        productService.deletProduct(productId);
        ApiResponse responce = new ApiResponse();
        responce.setMessage("Product Deleted Successfully");
        responce.setStatus(true);
        return new ResponseEntity<>(responce, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProduct(){
        List<Product> products = productService.findAllProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product request, @PathVariable Long productId) throws ProductException{
        Product product = productService.updateProduct(productId, request);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] requests){

        for(CreateProductRequest productRequest: requests){
            productService.createProduct(productRequest);
        }
        ApiResponse response = new ApiResponse();
        response.setMessage("Product Created Successfully");
        response.setStatus(true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
