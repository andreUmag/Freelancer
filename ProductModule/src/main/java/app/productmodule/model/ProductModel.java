package app.productmodule.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productModel")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Float price;
    private Integer stock;
}
