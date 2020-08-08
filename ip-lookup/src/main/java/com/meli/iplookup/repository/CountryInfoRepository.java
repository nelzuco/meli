package com.meli.iplookup.repository;

import com.meli.iplookup.model.CountryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryInfoRepository extends JpaRepository<CountryInfo, String> {
}
