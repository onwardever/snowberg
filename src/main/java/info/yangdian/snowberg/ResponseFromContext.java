package info.yangdian.snowberg;

import info.yangdian.husky.log.Logger;
import info.yangdian.husky.log.LoggerFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpHeaders;

public class ResponseFromContext
    extends ChannelOutboundHandlerAdapter
{
    private static final Logger logger = LoggerFactory.getInstance(ResponseFromContext.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        if (msg instanceof ResponseContext)
        {
            ResponseContext context = (ResponseContext) msg;
            appendHeaders(context.headers());
            ctx.write(context.getResponse(), promise);
        }
        else
            ctx.write(msg, promise);
    }

    private void appendHeaders(HttpHeaders headers)
    {
        headers.set("Server", "snowberg/1.0");
    }
}
