package gov.cdc.nbs.patientlistener.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import gov.cdc.nbs.message.RequestStatus;

class StatusProducerTest {

    @Mock
    private KafkaTemplate<String, RequestStatus> template;

    @Captor
    private ArgumentCaptor<RequestStatus> statusCaptor;

    @Captor
    private ArgumentCaptor<String> keyCaptor;

    @InjectMocks
    private StatusProducer statusProducer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(statusProducer, "statusTopic", "topic");
    }

    @Test
    void sendSuccessTest() {
        var status = RequestStatus
                .builder()
                .successful(true)
                .requestId("RequestId")
                .message("Message")
                .entityId(123L)
                .build();
        statusProducer.send(status);

        verify(template, times(1)).send("topic", status);
    }

    @Test
    void sendFailTest() {
        var status = RequestStatus
                .builder()
                .successful(false)
                .requestId("RequestId")
                .message("Message")
                .entityId(123L)
                .build();
        statusProducer.send(status);

        verify(template, times(1)).send("topic", status);
    }

    @Test
    void sendTest() {
        statusProducer.send(true, "key", "message");
        verify(template, times(1)).send(eq("topic"), Mockito.any());

        verify(template).send(keyCaptor.capture(), statusCaptor.capture());

        RequestStatus status = statusCaptor.getValue();
        assertEquals(true, status.isSuccessful());
        assertEquals("key", status.getRequestId());
        assertEquals("message", status.getMessage());
        assertEquals(null, status.getEntityId());
    }
}
