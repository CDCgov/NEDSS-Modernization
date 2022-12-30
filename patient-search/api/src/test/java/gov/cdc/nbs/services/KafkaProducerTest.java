package gov.cdc.nbs.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import gov.cdc.nbs.Application;
import gov.cdc.nbs.message.EnvelopeRequest;
import gov.cdc.nbs.message.TemplateInput;
import gov.cdc.nbs.service.KafkaRequestProducerService;

@SpringBootTest(classes = Application.class, properties = { "spring.profiles.active:test" })
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ContextConfiguration
public class KafkaProducerTest {

	@Mock
	private KafkaTemplate<String, EnvelopeRequest> kafkaTemplate;

	@InjectMocks
	private KafkaRequestProducerService producer;


	@Test
	void testPatientSearchEvent() {
		List<TemplateInput> msgVariables = new ArrayList<TemplateInput>();
		TemplateInput vars = new TemplateInput();
		vars.setKey("key1");
		vars.setValue("Hello World.");
		msgVariables.add(vars);

		EnvelopeRequest message = new EnvelopeRequest("Request-ID", msgVariables);
		ListenableFuture<SendResult<String, EnvelopeRequest>> future = new SettableListenableFuture<SendResult<String, EnvelopeRequest>>();
		Mockito.when(kafkaTemplate.send(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(future);

		producer.requestEnvelope(message);

		ArgumentCaptor<EnvelopeRequest> envelopeEventArgumentCaptor = ArgumentCaptor.forClass(EnvelopeRequest.class);
		verify(kafkaTemplate).send(eq(null), eq("Request-ID"), envelopeEventArgumentCaptor.capture());

		EnvelopeRequest actualRecord = envelopeEventArgumentCaptor.getValue();
		assertThat(actualRecord.getRequestId()).isEqualTo("Request-ID");
		assertThat(actualRecord.getVars().get(0).getValue()).isEqualTo("Hello World.");

		verifyNoMoreInteractions(kafkaTemplate);
	}
}
