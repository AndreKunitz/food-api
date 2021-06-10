package me.andrekunitz.food.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonRootName("cuisine")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cuisine {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "cuisine")
    private List<Merchant> merchants = new ArrayList<>();

}
