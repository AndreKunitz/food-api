package me.andrekunitz.food.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.andrekunitz.food.Groups;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Merchant {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(groups = Groups.MerchantRegistration.class)
    @Column(length = 30, nullable = false)
    private String name;

    @PositiveOrZero(groups = Groups.MerchantRegistration.class)
    @Column(name = "delivery_fee", nullable = false)
    private BigDecimal deliveryFee;

//    @JsonIgnore
//    @JsonIgnoreProperties("hibernateLazyInitializer")
    @Valid
    @NotNull(groups = Groups.MerchantRegistration.class)
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
