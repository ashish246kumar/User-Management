package com.nagarro.Mini_Assignment_2.serviceImpl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.Mini_Assignment_2.dtos.UserInfo;
import com.nagarro.Mini_Assignment_2.enums.VerificationResult;
import com.nagarro.Mini_Assignment_2.exception.CustomException;

@Service
public class VerificationService {

	private final ApiService apiService;

	private final ExecutorService executorService = Executors.newFixedThreadPool(2); // Two threads for parallel
																						// execution

	@Autowired
	public VerificationService(ApiService apiService) {
		this.apiService = apiService;
	}

	public VerificationResult verifyUser(UserInfo userDetails) {
		try {
			CompletableFuture<List<String>> nationality = CompletableFuture
					.supplyAsync(() -> apiService.getCountries(userDetails.getName().getFirst()), executorService);

			CompletableFuture<String> genderStr = CompletableFuture
					.supplyAsync(() -> apiService.getGender(userDetails.getName().getFirst()), executorService);

			CompletableFuture<VerificationResult> combinedFuture = nationality.thenCombine(genderStr, (nat, gender) -> {
				if (nat.contains(userDetails.getNat()) && userDetails.getGender().equalsIgnoreCase(gender)) {
					return VerificationResult.VERIFIED;
				} else {
					return VerificationResult.TO_BE_VERIFIED;
				}
			});

			return combinedFuture.join();
		} catch (CustomException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new CustomException("Error verifying user", 500);
		}
	}
}
