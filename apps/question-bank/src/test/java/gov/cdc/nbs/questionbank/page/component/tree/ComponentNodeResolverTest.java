package gov.cdc.nbs.questionbank.page.component.tree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import gov.cdc.nbs.questionbank.page.component.ContentNode;
import gov.cdc.nbs.questionbank.page.component.InputNode;
import gov.cdc.nbs.questionbank.page.component.LayoutNode;
import gov.cdc.nbs.questionbank.page.component.PageNode;
import org.junit.jupiter.api.Test;

class ComponentResolverTest {

  @Test
  void should_resolve_component_using_layout_resolver() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1002, "name-value", true, 1, 1);

    LayoutNode layout = mock(PageNode.class);

    LayoutComponentResolver layoutResolver = mock(LayoutComponentResolver.class);

    when(layoutResolver.resolve(any(), any())).thenReturn(layout);

    ContentComponentResolver contentResolver = mock(ContentComponentResolver.class);

    ComponentResolver resolver = new ComponentResolver(layoutResolver, contentResolver);

    ComponentNode actual = resolver.resolve(flattened);

    assertThat(actual).isSameAs(layout);

    verify(layoutResolver).resolve(isA(LayoutNode.Type.class), eq(flattened));

    verifyNoInteractions(contentResolver);
  }

  @Test
  void should_resolve_component_using_component_resolver() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1008, "name-value", true, 1, 1);

    ContentNode content = mock(InputNode.class);

    LayoutComponentResolver layoutResolver = mock(LayoutComponentResolver.class);

    ContentComponentResolver contentResolver = mock(ContentComponentResolver.class);

    when(contentResolver.resolve(any(), any())).thenReturn(content);

    ComponentResolver resolver = new ComponentResolver(layoutResolver, contentResolver);

    ComponentNode actual = resolver.resolve(flattened);

    assertThat(actual).isSameAs(content);

    verify(contentResolver).resolve(isA(ContentNode.Type.class), eq(flattened));

    verifyNoInteractions(layoutResolver);
  }

  @Test
  void should_not_resolve_for_invalid_component_type() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1008, "name-value", true, 1, 1);

    LayoutComponentResolver layoutResolver = mock(LayoutComponentResolver.class);

    ContentComponentResolver contentResolver = mock(ContentComponentResolver.class);

    ComponentResolver resolver = new ComponentResolver(layoutResolver, contentResolver);

    assertThatThrownBy(() -> resolver.resolve(null, flattened))
        .hasMessageContaining("A Component type is required");
  }
}
