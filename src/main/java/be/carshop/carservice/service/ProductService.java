package be.carshop.carservice.service;

import be.carshop.carservice.dto.ProductDto;
import be.carshop.carservice.exception.BusinessException;
import be.carshop.carservice.repository.ProductRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductDto> getAllProducts(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        List<ProductDto> productDtoList = productRepository.findAll(paging).stream()
                .map(ProductDto::new).collect(Collectors.toList());

        if (productDtoList.isEmpty()) {
            throw new BusinessException("No products found");
        }

        return new PageImpl<>(productDtoList, paging, productDtoList.size());
    }

    public Page<ProductDto> getAllProductsByQuery(Predicate predicate, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        BooleanBuilder builder = new BooleanBuilder();
        List<ProductDto> productDtoList = productRepository.findAll(builder.and(predicate), paging).stream()
                .map(ProductDto::new).collect(Collectors.toList());

        if (productDtoList.isEmpty()) {
            throw new BusinessException("No products found");
        }

        return new PageImpl<>(productDtoList, paging, productDtoList.size());
    }
}
