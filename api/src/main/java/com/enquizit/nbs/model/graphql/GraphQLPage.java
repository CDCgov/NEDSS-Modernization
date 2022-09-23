package com.enquizit.nbs.model.graphql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraphQLPage {
    private int pageSize = 1;
    private int pageNumber;

    public int getOffset() {
        return this.getPageNumber() * this.getPageSize();
    }
}
