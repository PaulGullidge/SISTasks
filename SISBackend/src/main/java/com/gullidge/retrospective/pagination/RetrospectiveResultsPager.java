package com.gullidge.retrospective.pagination;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gullidge.retrospective.model.RetrospectiveDto;

@Component
public class RetrospectiveResultsPager {

	/**
	 * Get the paged results for a list of retrospectives
	 * @param fullList The full list of items
	 * @param currentPage the page of items required
	 * @param pageSize The number if items per page
	 * @return The page of items in a List
	 * @throws CurrentPageOutOfRangeException when the page requested has no items
	 */
	public static List<RetrospectiveDto> getPagedView(List<RetrospectiveDto> fullList, int currentPage, int pageSize) throws CurrentPageOutOfRangeException {
		int numberOfEntries = fullList.size();
		int numberOfPages = numberOfEntries / pageSize;
		if (numberOfEntries % pageSize != 0) {
			numberOfPages += 1;
		}
		if (currentPage < 1 || currentPage > numberOfPages) {
			throw new CurrentPageOutOfRangeException("The selected page is not present (selected: " + currentPage + ", total pages: " + numberOfPages + ", pageSize: " + pageSize +")");
		}

		// Get the start and end positions to return
		int startPosition = (currentPage -1) * pageSize;
		int endPosition = currentPage * pageSize;
		if (endPosition > fullList.size()) {
			endPosition = fullList.size();
		}
		
		// return the correct section of the full list
		List<RetrospectiveDto> valueToReturn = fullList.subList(startPosition, endPosition);
		return valueToReturn;
	}
}
