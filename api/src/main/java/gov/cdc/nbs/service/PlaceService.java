package gov.cdc.nbs.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import gov.cdc.nbs.entity.odse.QPlace;
import gov.cdc.nbs.entity.odse.Place;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.searchFilter.PlaceFilter;
import gov.cdc.nbs.repository.PlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class PlaceService {
    private final int MAX_PAGE_SIZE = 50;

    @PersistenceContext
    private final EntityManager entityManager;
    private final PlaceRepository placeRepository;

    public Optional<Place> findPlaceById(Long id) {
        return placeRepository.findById(id);
    }

    public Page<Place> findAllPlaces(GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);
        if (page == null) {
            page = new GraphQLPage(MAX_PAGE_SIZE, 0);
        }
        return placeRepository.findAll(pageable);
    }

    public List<Place> findPlacesByFilter(PlaceFilter filter, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        var place = QPlace.place;
        var query = queryFactory.selectFrom(place);

        query = applyIfFilterNotNull(query, place.id::eq, filter.getId());
        query = applyIfFilterNotNull(query, place.description::likeIgnoreCase, filter.getDescription());
        query = applyIfFilterNotNull(query, place.nm::likeIgnoreCase, filter.getNm());
        query = applyIfFilterNotNull(query, place.streetAddr1::likeIgnoreCase, filter.getStreetAddr1());
        query = applyIfFilterNotNull(query, place.streetAddr2::likeIgnoreCase, filter.getStreetAddr2());
        query = applyIfFilterNotNull(query, place.cityDescTxt::likeIgnoreCase, filter.getCityDescTxt());
        query = applyIfFilterNotNull(query, place.cityCd::eq, filter.getCityCd());
        query = applyIfFilterNotNull(query, place.stateCd::eq, filter.getStateCd());
        query = applyIfFilterNotNull(query, place.zipCd::eq, filter.getZipCd());

        return query.limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetch();
    }

    private <T> JPAQuery<Place> applyIfFilterNotNull(JPAQuery<Place> query,
            Function<T, BooleanExpression> expression, T parameter) {
        if (parameter != null) {
            return query.where(expression.apply(parameter));
        } else {
            return query;
        }
    }
}
