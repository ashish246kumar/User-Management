package com.nagarro.Mini_Assignment_2.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.nagarro.Mini_Assignment_2.dtos.Country;
import com.nagarro.Mini_Assignment_2.dtos.CountryInfo;
import com.nagarro.Mini_Assignment_2.dtos.GenderInfo;
import com.nagarro.Mini_Assignment_2.dtos.Results;
import com.nagarro.Mini_Assignment_2.exception.CustomException;

@Service
public class ApiService {

	private final WebClient api1WebClient;
	private final WebClient api2WebClient;
	private final WebClient api3WebClient;

	public ApiService(@Qualifier("api1WebClient") WebClient api1WebClient,
			@Qualifier("api2WebClient") WebClient api2WebClient, @Qualifier("api3WebClient") WebClient api3WebClient) {
		this.api1WebClient = api1WebClient;
		this.api2WebClient = api2WebClient;
		this.api3WebClient = api3WebClient;
	}

	public Results getUserDetails() {
		try {
			return api1WebClient.get().retrieve().bodyToMono(Results.class).block();
		} catch (WebClientResponseException e) {
			throw new CustomException("Error fetching user details", e.getStatusCode().value());
		} catch (Exception e) {
			throw new CustomException("Unexpected error fetching user details", 500);
		}
	}

	public List<String> getCountries(String name) {
		try {
			CountryInfo countryInfo = api2WebClient.get().uri("/?name=" + name).retrieve()
					.bodyToMono(new ParameterizedTypeReference<CountryInfo>() {
					}).block();

			if (countryInfo != null && countryInfo.getCountry() != null) {
				return countryInfo.getCountry().stream().map(Country::getCountry_id).collect(Collectors.toList());
			} else {
				throw new CustomException("Country information not available", 404);
			}
		} catch (WebClientResponseException e) {
			throw new CustomException("Error fetching country information", e.getStatusCode().value());
		} catch (Exception e) {
			throw new CustomException("Unexpected error fetching country information", 500);
		}
	}

	public String getGender(String name) {
		try {
			GenderInfo genderInfo = api3WebClient.get().uri("/?name=" + name).retrieve().bodyToMono(GenderInfo.class)
					.block();

			if (genderInfo != null) {
				return genderInfo.getGender();
			} else {
				throw new CustomException("Gender information not available", 404);
			}
		} catch (Exception e) {
			throw new CustomException("Error fetching gender information", 500);
		}
	}
}
