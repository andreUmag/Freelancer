package app.ordermodule.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orderModel")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private Long product_id;
    private String state;
    private Integer quantity;
}
