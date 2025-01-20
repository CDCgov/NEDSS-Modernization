package gov.cdc.nbs.gateway.classic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpRequest;

/**
 * A {@link io.netty.channel.ChannelHandler} that will encode the pipe ({@code |}) character to the safer {@code %7C}
 * value on the uri of the request.  The pipe character is considered unsafe to be included in URIs.
 */
class IllegalURICharacterChannelHandler extends ChannelInboundHandlerAdapter {

  private static final String PIPE = "|";
  private static final String PIPE_ENCODED = "%7C";

  @Override
  public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
    if (msg instanceof DefaultHttpRequest request) {
      //  alter the uri to encode pipe characters.
      request.setUri(request.uri().replace(PIPE, PIPE_ENCODED));
    }

    super.channelRead(ctx, msg);
  }

}
