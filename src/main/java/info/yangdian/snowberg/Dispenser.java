package info.yangdian.snowberg;

import info.yangdian.husky.log.Logger;
import info.yangdian.husky.log.LoggerFactory;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.util.LinkedHashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class Dispenser extends SimpleChannelInboundHandler<HttpRequestContext>
{
    private static final Logger logger = LoggerFactory.getInstance(Dispenser.class);

    private Paths<Controller> paths = PathTrie.PATHS;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpRequestContext request) throws Exception
    {
        Controller controller = findController(request.getUri(), request);

        HttpResponse response;

        if(controller==null)
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        else
            response = controller.doRequest(request);

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Controller findController(String uri, HttpRequestContext requestContext)
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
