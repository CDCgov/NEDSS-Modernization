package test.java.gov.cdc.nbs.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

import java.beans.Transient;
import java.time.Instant;

import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.PatientDeleteRequest;



@SpringBootTest(classes = Application.class, properties = { "spring.profiles.active:test" })
@RunWith(SpringRunner.class)

public class PatientDeleteTest {
    @Mock
	PersonRepository personRepository;
	@Mock
	TeleLocatorRepository teleLocatorRepository;
	@Mock
	PostalLocatorRepository postalLocatorRepository;

	@Mock
	SecurityContextHolder securitContextHolder;

	@InjectMocks
	PatientService patientService;

	Person person;
	Long ID;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PatientDeleteRequest request;

    @Test 
    void findId() throws Exception{
        person = request.findById(ID);
        assertEquals(person.getNames());
    }

    @Test 
    void deletePatient() throws Exception{
        person = request.delete(ID);
        assertNull(findById(ID));
    }
}
