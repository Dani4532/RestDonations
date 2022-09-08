package com.example.restdonations.repository;

import com.example.restdonations.model.Donation;
import com.example.restdonations.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Integer> {
@Query("""
select persons
from Person persons
""")
    List<Person> findPersonsWithMin(Integer min);
}
