package app.ordermodule.client;

import app.ordermodule.config.FeingConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ProductModule", configuration = FeingConfig.class)
public interface ProductClient {

    @GetMapping("api/product/{id}")
    ResponseEntity<?> getProductById(@PathVariable("id") Long id);

}
