package com.example.restdonations.controller;

import com.example.restdonations.model.Donation;
import com.example.restdonations.model.Person;
import com.example.restdonations.repository.DonationRepository;
import com.example.restdonations.repository.PersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DonationController {

    private final DonationRepository donationRepository;
    private final PersonRepository personRepository;

    public DonationController(DonationRepository donationRepository, PersonRepository personRepository) {
        this.donationRepository = donationRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/donations/{id}")
    Donation findDonationById(@PathVariable Integer id) {
        return donationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }

    @GetMapping("/donations")
    List<Person> getAllMin(@RequestParam(name = "min") Integer min) {
        var result = donationRepository.groupByPersons();
        var donationAmount = donationRepository.findPersonsWithMin();
        var j = 0;
        for (int i = 0; i < donationAmount.size(); i++){
            if (donationAmount.get(i) < min){
                result.remove((i - j));
                j++;
            }
        }
        return result;
    }

    @GetMapping("/persons/{id}/donations")
    List<Donation> getAllDonationPerPerson(@PathVariable Integer id){
        return donationRepository.findAll()
                .stream()
                .filter(donation -> donation.getPerson().getId().equals(id))
                .toList();
    }

    @PostMapping("/persons")
    ResponseEntity<Donation> saveDonation(@RequestBody Donation donation){
        Donation saved = donationRepository.save(donation);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/donations")
                .build(saved.getId());
        return ResponseEntity
                .created(uri)
                .body(saved);
    }
}
