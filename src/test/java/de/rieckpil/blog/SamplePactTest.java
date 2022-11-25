package de.rieckpil.blog;

import java.util.List;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.LambdaDsl;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import kotlin.jvm.internal.Lambda;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@ExtendWith({PactConsumerTestExt.class, MyCustomExtension.class})
@PactTestFor(providerName = "some-provider", providerType = ProviderType.ASYNCH)
class SamplePactTest {

  @BeforeAll
  static void setup(String injectedString) {
    System.out.println(injectedString);
  }

  @Pact(consumer = "some-consumer")
  public MessagePact someMessage(MessagePactBuilder builder) {
    return builder.withContent(LambdaDsl.newJsonBody(object -> object.stringType("test", "Test")).build()).toPact();
  }

  @Test
  @PactTestFor(pactMethod = "someMessage")
  void consume(List<Message> messages) {
    System.out.println(messages);
  }
}
