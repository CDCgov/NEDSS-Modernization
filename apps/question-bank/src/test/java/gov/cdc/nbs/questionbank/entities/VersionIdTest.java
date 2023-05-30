package gov.cdc.nbs.questionbank.entities;

import static org.junit.Assert.assertEquals;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class VersionIdTest {

    @Test
    void should_set_version_to_1() {
        VersionId versionId = new VersionId();
        assertEquals(1, versionId.getVersion().intValue());
    }

    @Test
    void should_set_version_and_uuid() {
        UUID uuid = UUID.randomUUID();
        Integer version = 9;

        VersionId versionId = new VersionId(uuid, version);
        assertEquals(version, versionId.getVersion());
        assertEquals(uuid, versionId.getId());
    }
}
