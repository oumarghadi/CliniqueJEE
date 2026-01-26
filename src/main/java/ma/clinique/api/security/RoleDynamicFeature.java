package ma.clinique.api.security;

import jakarta.ws.rs.container.DynamicFeature;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.Provider;

import ma.clinique.api.filters.RoleFilter;

@Provider
public class RoleDynamicFeature implements DynamicFeature {

  @Override
  public void configure(ResourceInfo resourceInfo, FeatureContext context) {
    RequiresRole rr = resourceInfo.getResourceMethod().getAnnotation(RequiresRole.class);
    if (rr == null) rr = resourceInfo.getResourceClass().getAnnotation(RequiresRole.class);

    if (rr != null) {
      context.register(new RoleFilter(rr.value()));
    }
  }
}
