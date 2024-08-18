package com.E_com.E_commerce.ServiceImplementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.E_com.E_commerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.E_com.E_commerce.Exception.ProductException;
import com.E_com.E_commerce.Repository.CategoryRepository;
import com.E_com.E_commerce.Repository.ProductRepository;
import com.E_com.E_commerce.Repository.UserRepository;
import com.E_com.E_commerce.Request.CreateProductRequest;
import com.E_com.E_commerce.Service.ProductService;
import com.E_com.E_commerce.model.Category;
import com.E_com.E_commerce.model.Product;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest request) {
        Category topLevel = categoryRepository.findByName(request.getTopLavelCategory());
        if (topLevel == null) {
            Category topLavelCategory = new Category();
            topLavelCategory.setName(request.getTopLavelCategory());
            topLavelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLavelCategory);
        }

        Category secondLevel = categoryRepository.findByNameAndParent(request.getSecondLavelCategory(),
                topLevel.getName());

        if (secondLevel == null) {
            Category secondLavelCategory = new Category();
            secondLavelCategory.setName(request.getSecondLavelCategory());
            secondLavelCategory.setParentcategory(topLevel);
            secondLavelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLavelCategory);
        }

        Category thirdLevel = categoryRepository.findByNameAndParent(request.getThirdLavelCategory(),
        secondLevel.getName());
        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(request.getThirdLavelCategory());
            thirdLevelCategory.setParentcategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setColor(request.getColor());
        product.setDescription(request.getDescription());
        product.setDiscountedPrice(request.getDiscountedPrice());
        product.setDiscountPersent(request.getDiscountedPercent());
        product.setImageUrl(request.getImageUrl());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setSizes(request.getSize());
        product.setQuantity(request.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAllProducts();
    }

    @Override
    public String deletProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product Deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        Product updateProduct = findProductById(productId);

        if(product.getQuantity()!=0){
            updateProduct.setQuantity(product.getQuantity());

        }
        return productRepository.save(updateProduct);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        throw new ProductException("Product Not Found With Id : " + id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        throw new UnsupportedOperationException("Unimplemented method 'findProductByCategory'");
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> size, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> products = productRepository.filterProduct(category,minPrice,maxPrice,minDiscount,sort);
        if(!colors.isEmpty()){
            products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }

        if(stock!=null){
            if(stock.equals("in_stock")){
                products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
            }
        }

        int startIndex = (int)pageable.getOffset();
        int endIndex = Math.min(startIndex+pageable.getPageSize(), products.size());

        List<Product> pageContent = products.subList(startIndex, endIndex);

        return new PageImpl<>(pageContent,pageable, products.size());
    }

}
