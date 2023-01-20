package gov.cdc.nbs.controller;

import gov.cdc.nbs.entity.odse.Place;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.PlaceFilter;
import gov.cdc.nbs.service.PlaceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @QueryMapping()
    public List<Place> findPlacesByFilter(@Argument PlaceFilter filter, @Argument GraphQLPage page) {
        return placeService.findPlacesByFilter(filter, page);
    }

    @QueryMapping()
    public Page<Place> findAllPlaces(@Argument GraphQLPage page) {
        return placeService.findAllPlaces(page);
    }

    @QueryMapping()
    public Optional<Place> findPlaceById(@Argument Long id) {
        return placeService.findPlaceById(id);
    }
}