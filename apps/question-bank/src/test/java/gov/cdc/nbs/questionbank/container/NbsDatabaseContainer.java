package gov.cdc.nbs.questionbank.container;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

class NbsDatabaseContainer extends GenericContainer<NbsDatabaseContainer> {
    private static final int DEFAULT_PORT = 1433;

    public NbsDatabaseContainer(final String image) {
        super(DockerImageName
                .parse(image));
        addExposedPorts(DEFAULT_PORT);
    }

    public String url() {
        String server = getHost();
        Integer mappedPort = getMappedPort(DEFAULT_PORT);
        return String.format(
                "jdbc:sqlserver://%s:%d;database=nbs_odse;encrypt=true;trustServerCertificate=true;",
                server,
                mappedPort);
    }
}
