package be.carshop.carservice.controller;

import be.carshop.carservice.dto.ProductDto;
import be.carshop.carservice.model.Product;
import be.carshop.carservice.service.ProductService;
import com.querydsl.core.types.Predicate;
import feign.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> getAllProductsByQuery(@QuerydslPredicate(root = Product.class) Predicate predicate,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(productService.getAllProductsByQuery(predicate, page, size));
    }
}
