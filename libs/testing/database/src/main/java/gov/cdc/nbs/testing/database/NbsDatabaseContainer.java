package gov.cdc.nbs.testing.database;

import java.util.Objects;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.utility.DockerImageName;

@SuppressWarnings(
    //  Disable type parameter names rule, one letter is not as helpful
    "java:S119")
class NbsDatabaseContainer<SELF extends MSSQLServerContainer<SELF>>
    extends MSSQLServerContainer<SELF> {
  private static final DockerImageName MS_SQL_SERVER_IMAGE = DockerImageName.parse(IMAGE);

  private String username;

  public NbsDatabaseContainer(final String image) {
    super(DockerImageName.parse(image).asCompatibleSubstituteFor(MS_SQL_SERVER_IMAGE));
    addEnv("ACCEPT_EULA", "Y");
    urlParameters.put("database", "nbs_odse");
  }

  @Override
  public SELF withUsername(final String username) {
    this.username = username;
    return self();
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) return true;
    return other instanceof NbsDatabaseContainer<?> && super.equals(other);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), username);
  }
}
