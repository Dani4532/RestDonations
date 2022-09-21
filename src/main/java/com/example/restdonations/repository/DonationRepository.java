package com.example.restdonations.repository;

import com.example.restdonations.model.Donation;
import com.example.restdonations.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface DonationRepository extends JpaRepository<Donation, Integer> {
    @Query(value = """
            select sum (donation.amount)
            from Donation donation
            group by donation.person
            """)
    List<Integer> findPersonsWithMin();

    @Query("""
            select donation.person
            from Donation donation
            group by donation.person
            """)
    List<Person> groupByPersons();
}
