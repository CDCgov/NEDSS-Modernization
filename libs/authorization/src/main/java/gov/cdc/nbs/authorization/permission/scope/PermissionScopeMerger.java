package gov.cdc.nbs.authorization.permission.scope;

import gov.cdc.nbs.accumulation.CollectionMerge;
import java.util.Collection;

class PermissionScopeMerger {

  PermissionScope merge(final PermissionScope current, final PermissionScope next) {

    Collection<Long> areas = CollectionMerge.merged(current.any(), next.any());
    Collection<Long> shared = CollectionMerge.merged(current.shared(), next.shared());

    return new PermissionScope(areas, shared);
  }
}
