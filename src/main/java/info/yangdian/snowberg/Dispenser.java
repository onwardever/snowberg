package info.yangdian.snowberg;

import io.netty.channel.*;
import io.netty.handler.codec.http.HttpRequest;

@ChannelHandler.Sharable
public class Dispenser extends SimpleChannelInboundHandler<HttpRequest>
{
    private Registry registry=new Registry();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest request) throws Exception
    {
        System.out.println("here");

        Controller controller = registry.find(request);

        ctx.writeAndFlush(controller.execute(request)).addListener(new ChannelFutureListener()
        {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception
            {
                if(future.isSuccess())
                {
                    System.out.println("成功响应");
                    future.channel().close();
                }
                else
                    future.cause().printStackTrace();
            }
        });
    }
}
