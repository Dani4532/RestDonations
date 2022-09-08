package com.example.restdonations.controller;

import com.example.restdonations.model.Donation;
import com.example.restdonations.model.Person;
import com.example.restdonations.repository.DonationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DonationController {

    private final DonationRepository repository;

    public DonationController(DonationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/donations/{id}")
    Donation findDonationById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }

    @GetMapping("/dontaions")
    List<Donation> getAllMin(@RequestParam(required = false, name = "min") Integer min) {
        if (min == null){
            return repository.findAll();
        }
        return null;
       //return repository.findPersonsWithMin(min);
    }

    @GetMapping("/persons/{id}/donations")
    List<Donation> getAllDonationPerPerson(@RequestParam(name = "id") Integer id){
        return repository.findAll()
                .stream()
                .filter(donation -> donation.getPerson().getId().equals(id))
                .toList();
    }

    @PostMapping("/persons")
    ResponseEntity<Donation> saveDonation(@RequestBody Donation donation){
        Donation saved = repository.save(donation);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .build(saved.getId());
        return ResponseEntity
                .created(uri)
                .body(saved);
    }
}
