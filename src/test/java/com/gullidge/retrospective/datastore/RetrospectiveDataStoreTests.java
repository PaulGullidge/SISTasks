package com.gullidge.retrospective.datastore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gullidge.retrospective.model.RetrospectiveDto;

public class RetrospectiveDataStoreTests {

	private RetrospectiveDataStore retrospectiveDataStore;
	
	@BeforeEach
	public void init() {
		retrospectiveDataStore = new RetrospectiveDataStore();
	}

	@Test
	public void allExpectedItemsSaved() {
		List<String> participants = new ArrayList<>();
		participants.add("Paul");
		participants.add("Peter");
		List<RetrospectiveDto> retrospectives = createRetrospectives(20, LocalDate.of(2024, 5, 19), participants);
		for (RetrospectiveDto retrospective : retrospectives) {
			try {
				retrospectiveDataStore.addRetrospective(retrospective);
			} catch (DuplicateRetrospectiveException e) {
				fail();
			}
		}
		List<RetrospectiveDto> retrievedRetrospectives = retrospectiveDataStore.getRetrospectives(null);
		assertEquals(20, retrievedRetrospectives.size());
		assertEquals("Retro1", retrievedRetrospectives.get(0).getName());
	}

	@Test
	public void allItemsOfASpecificDateAreRetrieved() {
		
		List<String> participants = new ArrayList<>();
		participants.add("Paul");
		participants.add("Peter");
		// Add 5 items with date of 19/5/24
		List<RetrospectiveDto> retrospectives = createRetrospectives(1, 5, LocalDate.of(2024, 5, 19), participants);
		for (RetrospectiveDto retrospective : retrospectives) {
			try {
				retrospectiveDataStore.addRetrospective(retrospective);
			} catch (DuplicateRetrospectiveException e) {
				fail();
			}
		}
		// Add 3 items with date of 18/5/24
		retrospectives = createRetrospectives(6, 3, LocalDate.of(2024, 5, 18), participants);
		for (RetrospectiveDto retrospective : retrospectives) {
			try {
				retrospectiveDataStore.addRetrospective(retrospective);
			} catch (DuplicateRetrospectiveException e) {
				fail();
			}
		}


		List<RetrospectiveDto> retrievedRetrospectives = retrospectiveDataStore.getRetrospectives(null);
		assertEquals(8, retrievedRetrospectives.size());
		retrievedRetrospectives = retrospectiveDataStore.getRetrospectives("19/5/2024");
		assertEquals(5, retrievedRetrospectives.size());
		retrievedRetrospectives = retrospectiveDataStore.getRetrospectives("19/05/2024");
		assertEquals(5, retrievedRetrospectives.size());
		retrievedRetrospectives = retrospectiveDataStore.getRetrospectives("18/5/2024");
		assertEquals(3, retrievedRetrospectives.size());
		retrievedRetrospectives = retrospectiveDataStore.getRetrospectives("1/05/2024");
		assertEquals(0, retrievedRetrospectives.size());
	}
	

	private List<RetrospectiveDto> createRetrospectives(int numberToCreate, LocalDate dateOfRetros, List<String> participants) {
		return createRetrospectives(1, numberToCreate, dateOfRetros, participants);
	}

	private List<RetrospectiveDto> createRetrospectives(int startNumber, int numberToCreate, LocalDate dateOfRetros, List<String> participants) {
		List<RetrospectiveDto> retrospectives = new ArrayList<>();
		for (int i=0; i<numberToCreate; i++) {
			RetrospectiveDto retrospectiveDto = new RetrospectiveDto();
			retrospectiveDto.setName("Retro" + (i + startNumber));
			retrospectiveDto.setDate(dateOfRetros);
			retrospectiveDto.setSummary("Summary details " + (i + startNumber));
			retrospectiveDto.setParticipants(participants);
			retrospectives.add(retrospectiveDto);
		}
		
		return retrospectives;
	}
	
}
