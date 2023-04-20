package gov.cdc.nbs.containers.database;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

class NbsDatabaseContainer extends GenericContainer<NbsDatabaseContainer> {
    private static final String IMAGE =
            "501715613725.dkr.ecr.us-east-1.amazonaws.com/cdc-nbs-modernization/modernization-test-db:1.0.7-SNAPSHOT.lb";

    private static final int DEFAULT_PORT = 1433;

    public NbsDatabaseContainer() {
        super(DockerImageName
                .parse(IMAGE));
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
