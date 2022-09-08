package com.example.restdonations.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    Integer id;
    @Min(5)
    int amount;
    @PastOrPresent
            @Column(name = "deposit_date")
    LocalDate date;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Donation donation = (Donation) o;
        return id != null && Objects.equals(id, donation.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
