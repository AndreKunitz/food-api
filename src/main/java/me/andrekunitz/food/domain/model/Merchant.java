package me.andrekunitz.food.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Merchant {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(name = "delivery_fee", nullable = false)
    private BigDecimal deliveryFee;

//    @JsonIgnore
//    @JsonIgnoreProperties("hibernateLazyInitializer")
    @ManyToOne // (fetch = LAZY)
    @JoinColumn(name = "cuisine_id", nullable = false)
    private Cuisine cuisine;

    @JsonIgnore
    @Embedded
    private Address address;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime registrationDate;

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime updateDate;

//    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "merchant_payment_method",
                joinColumns = @JoinColumn(name = "merchant_id"),
                inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "merchant")
    private List<Product> products = new ArrayList<>();
}
