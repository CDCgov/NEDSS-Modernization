package gov.cdc.nbs.graphql;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GraphQLPage {
    private int pageSize = 1;
    private int pageNumber;
    private Direction sortDirection;
    private String sortField;

    public GraphQLPage(int pageSize, int pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public int getOffset() {
        return this.getPageNumber() * this.getPageSize();
    }

    public Pageable toPageable(int maxPageSize) {
        int size = Math.min(pageSize, maxPageSize);
        if (sortDirection != null && sortField != null) {
            return PageRequest.of(pageNumber, size, Sort.by(sortDirection, sortField));
        } else {
            return PageRequest.of(pageNumber, size);
        }
    }
}
