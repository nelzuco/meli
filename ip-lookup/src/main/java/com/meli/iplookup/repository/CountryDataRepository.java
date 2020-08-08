package com.meli.iplookup.repository;

import com.meli.iplookup.model.CountryData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryDataRepository extends JpaRepository<CountryData, String> {
}
