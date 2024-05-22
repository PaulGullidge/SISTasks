package com.gullidge.retrospective.pagination;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.gullidge.retrospective.model.RetrospectiveDto;

public class RetrospectiveResultsPagerTests {

	@Test
	public void shouldReturnTwoPagesWhen20ItemsAnd10ItemsPerPage() {
		List<RetrospectiveDto> retrospectives = new ArrayList<>();
		for(int i=0; i<20; i++) {
			RetrospectiveDto retrospective = new RetrospectiveDto();
			retrospective.setName("Name" + i);
			retrospectives.add(retrospective);
		}
		
		try {
			List<RetrospectiveDto> retrospectivesPage = 
					RetrospectiveResultsPager.getPagedView(retrospectives, 1, 10);
			assertEquals(10, retrospectivesPage.size());
			assertEquals("Name0", retrospectivesPage.get(0).getName());
			assertEquals("Name9", retrospectivesPage.get(9).getName());
			retrospectivesPage = RetrospectiveResultsPager.getPagedView(retrospectives, 2, 10);
			assertEquals(10, retrospectivesPage.size());
			assertEquals("Name10", retrospectivesPage.get(0).getName());
			assertEquals("Name19", retrospectivesPage.get(9).getName());
			try {
				retrospectivesPage = RetrospectiveResultsPager.getPagedView(retrospectives, 3, 10);
				fail();
			}
			catch (CurrentPageOutOfRangeException pageOutOfRangeException) {
				// expect to reach here
			}
		} catch (CurrentPageOutOfRangeException e) {
			fail();
		}
	}
	
	@Test
	public void shouldReturnOnePagesWhen5ItemsAnd10ItemsPerPage() {
		List<RetrospectiveDto> retrospectives = new ArrayList<>();
		for(int i=0; i<5; i++) {
			RetrospectiveDto retrospective = new RetrospectiveDto();
			retrospective.setName("Name" + i);
			retrospectives.add(retrospective);
		}
		
		try {
			List<RetrospectiveDto> retrospectivesPage = 
					RetrospectiveResultsPager.getPagedView(retrospectives, 1, 10);
			assertEquals(5, retrospectivesPage.size());
			assertEquals("Name0", retrospectivesPage.get(0).getName());
			assertEquals("Name4", retrospectivesPage.get(4).getName());
			try {
				retrospectivesPage = RetrospectiveResultsPager.getPagedView(retrospectives, 2, 10);
				fail();
			}
			catch (CurrentPageOutOfRangeException pageOutOfRangeException) {
				// expect to reach here
			}
		} catch (CurrentPageOutOfRangeException e) {
			fail();
		}
	}

	@Test
	public void shouldReturnTwoPagesWhen11ItemsAnd10ItemsPerPage() {
		List<RetrospectiveDto> retrospectives = new ArrayList<>();
		for(int i=0; i<11; i++) {
			RetrospectiveDto retrospective = new RetrospectiveDto();
			retrospective.setName("Name" + i);
			retrospectives.add(retrospective);
		}
		
		try {
			List<RetrospectiveDto> retrospectivesPage = 
					RetrospectiveResultsPager.getPagedView(retrospectives, 1, 10);
			assertEquals(10, retrospectivesPage.size());
			assertEquals("Name0", retrospectivesPage.get(0).getName());
			assertEquals("Name9", retrospectivesPage.get(9).getName());
			retrospectivesPage = RetrospectiveResultsPager.getPagedView(retrospectives, 2, 10);
			assertEquals(1, retrospectivesPage.size());
			assertEquals("Name10", retrospectivesPage.get(0).getName());
		} catch (CurrentPageOutOfRangeException e) {
			fail();
		}
	}

	@Test
	public void shouldThrowExceptionWhen0ItemsAnd10ItemsPerPage() {
		List<RetrospectiveDto> retrospectives = new ArrayList<>();
		
		try {
			List<RetrospectiveDto> retrospectivesPage = 
					RetrospectiveResultsPager.getPagedView(retrospectives, 1, 10);
			fail();
		} catch (CurrentPageOutOfRangeException e) {
			// expect to get here
		}
	}
	
}
