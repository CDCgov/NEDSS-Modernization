package gov.cdc.nbs.gateway.classic;

import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NBS6NettyServerConfiguration {

  /**
   * Adds a {@link io.netty.channel.ChannelHandler} when a remote connection is established to sanitize unsafe urls
   * created and expected within NBS6.
   *
   * @return The {@code NettyServerCustomizer} to configure the underlying Netty Server.
   */
  @Bean
  NettyServerCustomizer nbs6NettyServerCustomizer() {
    return httpServer -> httpServer.doOnConnection(
        connection -> connection.addHandlerLast(
            "nbs6-illegal-uri-CharacterChannelHandler",
            new IllegalURICharacterChannelHandler()
        )
    );
  }

}
