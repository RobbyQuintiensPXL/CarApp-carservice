package be.carshop.carservice.controller;

import be.carshop.carservice.createresource.CreateProduct;
import be.carshop.carservice.dto.ProductDto;
import be.carshop.carservice.model.Product;
import be.carshop.carservice.service.ProductService;
import com.querydsl.core.types.Predicate;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/public/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> getAllProductsByQuery(@QuerydslPredicate(root = Product.class) Predicate predicate,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getAllProductsByQuery(predicate, page, size));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> createProduct(@RequestPart CreateProduct createProduct,
                                              @RequestPart("files") MultipartFile[] files) throws FileUploadException {
        productService.createProduct(createProduct, files);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
