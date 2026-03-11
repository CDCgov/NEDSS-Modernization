package gov.cdc.nbs.search;

import static org.assertj.core.api.Assertions.*;

import gov.cdc.nbs.data.pagination.PaginationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class SearchPageableMapperTest {

  @Test
  void should_map_with_page_size() {
    SearchPageableMapper mapper = new SearchPageableMapper(23);

    PaginationRequest request = new PaginationRequest(5, 73, null);

    Pageable actual = mapper.from(request);

    assertThat(actual).extracting(Pageable::getPageSize).isEqualTo(5);
  }

  @Test
  void should_map_with_default_page_size() {
    SearchPageableMapper mapper = new SearchPageableMapper(23);

    PaginationRequest request = new PaginationRequest(null, 73, null);

    Pageable actual = mapper.from(request);

    assertThat(actual).extracting(Pageable::getPageSize).isEqualTo(23);
  }

  @Test
  void should_map_with_page_number() {
    SearchPageableMapper mapper = new SearchPageableMapper(23);

    PaginationRequest request = new PaginationRequest(7, 109, null);

    Pageable actual = mapper.from(request);

    assertThat(actual).extracting(Pageable::getPageNumber).isEqualTo(109);
  }

  @Test
  void should_map_with_default_page_number() {
    SearchPageableMapper mapper = new SearchPageableMapper(23);

    PaginationRequest request = new PaginationRequest(7, null, null);

    Pageable actual = mapper.from(request);

    assertThat(actual).extracting(Pageable::getPageNumber).isEqualTo(0);
  }

  @Test
  void should_map_with_sorting() {
    SearchPageableMapper mapper = new SearchPageableMapper(23);

    PaginationRequest.Sort sort = new PaginationRequest.Sort("property-value", Sort.Direction.ASC);

    PaginationRequest request = new PaginationRequest(7, 17, sort);

    Pageable actual = mapper.from(request);

    assertThat(actual)
        .extracting(Pageable::getSort)
        .isEqualTo(Sort.by(Sort.Direction.ASC, "property-value"));
  }

  @Test
  void should_map_with_default_sorting() {
    SearchPageableMapper mapper = new SearchPageableMapper(23);

    PaginationRequest request = new PaginationRequest(7, 17, null);

    Pageable actual = mapper.from(request);

    assertThat(actual).extracting(Pageable::getSort).isEqualTo(Sort.unsorted());
  }

  @Test
  void should_map_with_default_sorting_when_property_not_present() {
    SearchPageableMapper mapper = new SearchPageableMapper(23);

    PaginationRequest.Sort sort = new PaginationRequest.Sort(null, Sort.Direction.ASC);

    PaginationRequest request = new PaginationRequest(7, 17, sort);

    Pageable actual = mapper.from(request);

    assertThat(actual).extracting(Pageable::getSort).isEqualTo(Sort.unsorted());
  }

  @Test
  void should_map_with_default_sorting_when_direction_not_present() {
    SearchPageableMapper mapper = new SearchPageableMapper(23);

    PaginationRequest.Sort sort = new PaginationRequest.Sort("property-value", null);

    PaginationRequest request = new PaginationRequest(7, 17, sort);

    Pageable actual = mapper.from(request);

    assertThat(actual).extracting(Pageable::getSort).isEqualTo(Sort.unsorted());
  }

  @Test
  void should_map_with_defaults() {
    SearchPageableMapper mapper = new SearchPageableMapper(23);

    PaginationRequest request = PaginationRequest.empty();

    Pageable actual = mapper.from(request);

    assertThat(actual)
        .extracting("pageSize", "pageNumber", "sort")
        .containsExactly(23, 0, Sort.unsorted());
  }

  @Test
  void should_map_null_to_defaults() {
    SearchPageableMapper mapper = new SearchPageableMapper(47);

    Pageable actual = mapper.from(null);

    assertThat(actual)
        .extracting("pageSize", "pageNumber", "sort")
        .containsExactly(47, 0, Sort.unsorted());
  }

  @Test
  void should_throw_exception_page_size_too_large() {
    SearchPageableMapper mapper = new SearchPageableMapper(23);

    PaginationRequest request = PaginationRequest.withSize(37);

    assertThatThrownBy(() -> mapper.from(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("page size: 37");
  }
}
