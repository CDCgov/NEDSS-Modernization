package gov.cdc.nbs.patientlistener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import gov.cdc.nbs.entity.odse.LocalUidGenerator;
import gov.cdc.nbs.patientlistener.service.IdGeneratorService.EntityType;
import gov.cdc.nbs.patientlistener.service.IdGeneratorService.GeneratedId;
import gov.cdc.nbs.repository.LocalUidGeneratorRepository;

@SpringBootTest()
@ActiveProfiles("test")
public class IdGeneratorServiceTest {

    @Mock
    private LocalUidGeneratorRepository localUidGeneratorRepository;

    @InjectMocks
    private IdGeneratorService idGeneratorService;

    @Test
    void testGetNextValidId_NBS() {
        when(localUidGeneratorRepository.findByTypeCd(Mockito.anyString())).thenReturn(validGeneratorEntry());
        var response = idGeneratorService.getNextValidId(EntityType.NBS);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("PREFIX", response.getPrefix());
        assertEquals("SUFFIX", response.getSuffix());

        verify(localUidGeneratorRepository).findByTypeCd(EntityType.NBS.toString());
    }

    @Test
    void testGetNextValidId_PERSON() {
        when(localUidGeneratorRepository.findById(Mockito.anyString())).thenReturn(validGeneratorEntry());
        var response = idGeneratorService.getNextValidId(EntityType.PERSON);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("PREFIX", response.getPrefix());
        assertEquals("SUFFIX", response.getSuffix());

        verify(localUidGeneratorRepository).findById(EntityType.PERSON.toString());
    }

    @Test
    void testNullEntityType() {
        IllegalArgumentException exception = null;
        GeneratedId response = null;
        try {
            response = idGeneratorService.getNextValidId(null);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertNull(response);

        verify(localUidGeneratorRepository, never()).save(Mockito.any());
    }

    private Optional<LocalUidGenerator> validGeneratorEntry() {
        var gen = LocalUidGenerator.builder()
                .id("TEST")
                .typeCd("LOCAL")
                .uidPrefixCd("PREFIX")
                .uidSuffixCd("SUFFIX")
                .seedValueNbr(1L)
                .build();

        return Optional.of(gen);
    }
}
