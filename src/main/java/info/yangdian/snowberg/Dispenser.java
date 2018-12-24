package info.yangdian.snowberg;

import info.yangdian.husky.log.Logger;
import info.yangdian.husky.log.LoggerFactory;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import java.util.LinkedHashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class Dispenser extends SimpleChannelInboundHandler<RequestContext>
{
    private static final Logger logger = LoggerFactory.getInstance(Dispenser.class);

    private Paths<Controller> paths = PathTrie.PATHS;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestContext request) throws Exception
    {
        Controller controller = findController(request.getUri(), request);

        if(controller==null)
        {
            ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND)).addListener(ChannelFutureListener.CLOSE);
        }
        else
        {
            logger.debug("Controller: {} analyze PATH: {}", controller.desc(), request.getUri());
            ctx.writeAndFlush(controller.doRequest(request)).addListener(ChannelFutureListener.CLOSE);
        }
    }

    private Controller findController(String uri, RequestContext requestContext)
    {
        Map<String, String> parameters = new LinkedHashMap<>();

        Controller found = paths.get(uri, parameters);

        if (found == null)
        {
            return null;
        }
        else
        {
            for (Map.Entry<String, String> entry : parameters.entrySet())
            {
                requestContext.addParameter(entry.getKey(), entry.getValue());
            }

            return found;
        }
    }
}
