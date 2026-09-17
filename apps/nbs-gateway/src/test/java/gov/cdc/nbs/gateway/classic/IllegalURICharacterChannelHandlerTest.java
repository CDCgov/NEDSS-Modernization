package gov.cdc.nbs.gateway.classic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeadersFactory;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IllegalURICharacterChannelHandlerTest {

  @Mock private ChannelHandlerContext context;

  private final IllegalURICharacterChannelHandler handler = new IllegalURICharacterChannelHandler();

  private final ArgumentCaptor<DefaultHttpRequest> captor =
      ArgumentCaptor.forClass(DefaultHttpRequest.class);

  @Test
  void verify_uri_is_encoded() throws Exception {
    // given a request that contains disallowed characters
    String uri = " {}[]|\\^`<>\"'#";
    DefaultHttpRequest request = createRequest(uri);

    when(context.fireChannelRead(captor.capture())).thenReturn(context);

    // when the request is handled
    handler.channelRead(context, request);

    // then the disallowed characters are encoded
    DefaultHttpRequest actual = captor.getValue();
    assertThat(actual.uri()).isEqualTo("%20%7B%7D%5B%5D%7C%5C%5E%60%3C%3E%22%27%23");
  }

  @Test
  void verify_uri_can_be_decoded() throws Exception {
    // given a request that contains disallowed characters
    String uri = " {}[]|\\^`<>\"'#";
    DefaultHttpRequest request = createRequest(uri);

    when(context.fireChannelRead(captor.capture())).thenReturn(context);

    // when the request is handled
    handler.channelRead(context, request);

    // then encoded characters can be decoded
    String actual = URLDecoder.decode(captor.getValue().uri(), StandardCharsets.UTF_8);

    assertThat(actual).isEqualTo(uri);
  }

  private DefaultHttpRequest createRequest(String uri) {
    return new DefaultHttpRequest(
        HttpVersion.HTTP_1_0,
        HttpMethod.GET,
        uri,
        DefaultHttpHeadersFactory.headersFactory().newEmptyHeaders(),
        false);
  }
}
