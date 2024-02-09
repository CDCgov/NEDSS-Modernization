package gov.cdc.nbs.codes.user;

import gov.cdc.nbs.authentication.NbsAuthority;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.GraphQLPageableMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
class UserListResolver {

  private final AuthUserRepository userRepository;
  private final GraphQLPageableMapper pageableMapper;

  UserListResolver(
      final AuthUserRepository userRepository,
      final GraphQLPageableMapper pageableMapper
  ) {
    this.userRepository = userRepository;
    this.pageableMapper = pageableMapper;
  }

  /**
   * Returns a page of users that share a program area with the current user, logic copied from legacy NBS -
   * DbAuthDAOImpl.java "getSecureUserDTListBasedOnProgramArea"
   */
  @QueryMapping(name = "findAllUsers")
  public Page<AuthUser> resolve(
      @AuthenticationPrincipal final NbsUserDetails user,
      @Argument final GraphQLPage page
  ) {
    var userProgramAreas = user.getAuthorities()
        .stream()
        .map(NbsAuthority::getProgramArea)
        .distinct()
        .toList();

    Pageable pageable = pageableMapper.from(page);

    return userRepository.findByProgramAreas(userProgramAreas, pageable);
  }

}
