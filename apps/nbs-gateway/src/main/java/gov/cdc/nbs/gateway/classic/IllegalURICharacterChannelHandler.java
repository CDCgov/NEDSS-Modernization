package gov.cdc.nbs.gateway.classic;

import java.util.Map;
import static java.util.Map.entry;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpRequest;

/**
 * A {@link io.netty.channel.ChannelHandler} that will encode special characters to the
 * safe values on the uri of the request. 
 */
class IllegalURICharacterChannelHandler extends ChannelInboundHandlerAdapter {

  private static final Map<String, String> ENCODING_MAP = Map.ofEntries(
    entry(" ", "%20"),
    entry("{", "%7B"),
    entry("}", "%7D"),
    entry("[", "%5B"),
    entry("]", "%5D"),
    entry("|", "%7C"),
    entry("\\", "%5C"),
    entry("^", "%5E"),
    entry("`", "%60"),
    entry("<", "%3C"),
    entry(">", "%3E"),
    entry("\"", "%22"),
    entry("'", "%27")
  );

  @Override
  public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
    if (msg instanceof DefaultHttpRequest request) {
      // alter the uri to encode special characters sent by NBS 6.
      request.setUri(encodeUri(request.uri()));
    }

    super.channelRead(ctx, msg);
  }

  String encodeUri(String uri) {
    for (Map.Entry<String, String> entry : ENCODING_MAP.entrySet()) {
      uri = uri.replace(entry.getKey(), entry.getValue());
    }
    return uri;
  }
}
