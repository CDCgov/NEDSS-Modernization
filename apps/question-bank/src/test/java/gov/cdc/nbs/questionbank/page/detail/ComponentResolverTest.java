package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.Component;
import gov.cdc.nbs.questionbank.page.component.Content;
import gov.cdc.nbs.questionbank.page.component.Input;
import gov.cdc.nbs.questionbank.page.component.Layout;
import gov.cdc.nbs.questionbank.page.component.Page;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ComponentResolverTest {

  @Test
  void should_resolve_component_using_layout_resolver() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1002, "name-value", true, 1);

    Layout layout = mock(Page.class);

    LayoutComponentResolver layoutResolver = mock(LayoutComponentResolver.class);

    when(layoutResolver.resolve(any(), any())).thenReturn(layout);

    ContentComponentResolver contentResolver = mock(ContentComponentResolver.class);

    ComponentResolver resolver = new ComponentResolver(layoutResolver, contentResolver);

    Component actual = resolver.resolve(flattened);

    assertThat(actual).isSameAs(layout);

    verify(layoutResolver).resolve(Mockito.isA(Layout.Type.class), eq(flattened));

    verifyNoInteractions(contentResolver);
  }

  @Test
  void should_resolve_component_using_component_resolver() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1008, "name-value", true, 1);

    Content content = mock(Input.class);

    LayoutComponentResolver layoutResolver = mock(LayoutComponentResolver.class);

    ContentComponentResolver contentResolver = mock(ContentComponentResolver.class);

    when(contentResolver.resolve(any(), any())).thenReturn(content);

    ComponentResolver resolver = new ComponentResolver(layoutResolver, contentResolver);

    Component actual = resolver.resolve(flattened);

    assertThat(actual).isSameAs(content);

    verify(contentResolver).resolve(Mockito.isA(Content.Type.class), eq(flattened));

    verifyNoInteractions(layoutResolver);
  }

  @Test
  void should_not_resolve_for_invalid_component_type() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1008, "name-value", true, 1);

    LayoutComponentResolver layoutResolver = mock(LayoutComponentResolver.class);

    ContentComponentResolver contentResolver = mock(ContentComponentResolver.class);

    ComponentResolver resolver = new ComponentResolver(layoutResolver, contentResolver);

    assertThatThrownBy(() -> resolver.resolve(null, flattened))
        .hasMessageContaining("A Component type is required");



  }

}
