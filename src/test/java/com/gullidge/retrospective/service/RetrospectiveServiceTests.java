package com.gullidge.retrospective.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.gullidge.retrospective.datastore.RetrospectiveDataStore;
import com.gullidge.retrospective.exceptions.ExternalMessageFriendlyException;
import com.gullidge.retrospective.model.RetrospectiveDto;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {RetrospectiveService.class, RetrospectiveDataStore.class})
public class RetrospectiveServiceTests {

	@Autowired
	private RetrospectiveService retrospectiveService;
	
	
	@Test
	public void canCreateAnItem() {
		List<String> participants = new ArrayList<>();
		participants.add("Paul");
		participants.add("Peter");		
		List<RetrospectiveDto> retrospectives = createRetrospectives(20, LocalDate.of(2024, 5, 19), participants);
		for (RetrospectiveDto retrospective : retrospectives) {
			try {
				retrospectiveService.createRetrospective(retrospective);
			} catch (ExternalMessageFriendlyException e) {
				fail();
			}
		}
		List<RetrospectiveDto> retrievedRetrospectives;
		try {
			retrievedRetrospectives = retrospectiveService.getRetrospectives(1, 100);
			assertEquals(20, retrievedRetrospectives.size());
			assertEquals("Retro1", retrievedRetrospectives.get(0).getName());
		} catch (ExternalMessageFriendlyException e) {
			fail();
		}
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
