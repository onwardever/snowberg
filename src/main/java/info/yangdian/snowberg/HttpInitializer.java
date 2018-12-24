package info.yangdian.snowberg;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpInitializer extends ChannelInitializer<Channel>
{
    @Override
    protected void initChannel(Channel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("decoder", new HttpRequestDecoder());
                //.addLast("request-converter", new RequestToContext());

        pipeline
                .addLast("aggregator", new HttpObjectAggregator(65535))
                .addLast("encoder", new HttpResponseEncoder())
                .addLast("request-converter", new RequestToContext())
                .addLast("response-converter", new ResponseFromContext())
                .addLast("dispenser", new Dispenser());

    }
}
