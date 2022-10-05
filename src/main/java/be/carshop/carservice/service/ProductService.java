package be.carshop.carservice.service;

import be.carshop.carservice.createresource.CreateProduct;
import be.carshop.carservice.dto.ProductDto;
import be.carshop.carservice.exception.BusinessException;
import be.carshop.carservice.model.*;
import be.carshop.carservice.repository.BrandRepository;
import be.carshop.carservice.repository.ModelRepository;
import be.carshop.carservice.repository.ProductRepository;
import be.carshop.carservice.repository.VersionRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final VersionRepository versionRepository;
    private final ModelRepository modelRepository;
    private final FileStorageService storageService;

    public ProductService(ProductRepository productRepository, BrandRepository brandRepository,
                          VersionRepository versionRepository, ModelRepository modelRepository,
                          FileStorageService storageService) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.versionRepository = versionRepository;
        this.modelRepository = modelRepository;
        this.storageService = storageService;
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

    public void createProduct(CreateProduct createProduct, MultipartFile[] files) throws FileUploadException {
        Brand brand = brandRepository.findByBrandName(createProduct.getBrand()).orElseThrow(() ->
                new BusinessException("Brand not found"));
        Model model = modelRepository.findByModelNameAndBrand_BrandName(createProduct.getModel(), brand.getBrandName())
                .orElseThrow(() -> new BusinessException("Model not found"));
        Version version = versionRepository.findByVersionNameAndModel_ModelName(createProduct.getVersion(), model.getModelName())
                .orElseThrow(() -> new BusinessException("Version not found"));
        Product product = new Product();
        product.setBrand(brand);
        product.setModel(model);
        product.setVersion(version);
        product.setFirstRegistration(createProduct.getFirstRegistration());
        product.setNumberDoors(createProduct.getNumberDoors());
        product.setPrice(createProduct.getPrice());
        product.setTransmission(Transmission.valueOf(createProduct.getTransmission().toUpperCase()));
        product.setNumberKm(createProduct.getNumberKm());

        List<String> fileNames = new ArrayList<>();
        Arrays.stream(files).forEach(file -> {
            try {
                storageService.save(file);
                fileNames.add(LocalDate.now() + "-" + file.getOriginalFilename());
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        });

        product.setImages(fileNames);

        productRepository.save(product);
    }

    public ProductDto getProductById(Long id) {
        Optional<ProductDto> foundProduct = productRepository.findById(id).map(ProductDto::new);
        if (foundProduct.isEmpty()) {
            throw new BusinessException("No product found");
        }
        return foundProduct.get();
    }
}