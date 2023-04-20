package gov.cdc.nbs.containers.database;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

class NbsDatabaseContainer extends GenericContainer<NbsDatabaseContainer> {

    private static final int DEFAULT_PORT = 1433;

    public NbsDatabaseContainer() {
        //  TODO: set to the actual name of the container
        super(DockerImageName.parse("nbs-mssql-test:latest"));

        addExposedPorts(DEFAULT_PORT);
    }

    public String url() {
        String server = getHost();
        Integer mappedPort = getMappedPort(DEFAULT_PORT);
        return String.format(
            "jdbc:sqlserver://%s:%d;database=nbs_odse;encrypt=true;trustServerCertificate=true;",
            server,
            mappedPort
        );
    }
}
