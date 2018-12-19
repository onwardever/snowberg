package info.yangdian.snowberg;

import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;

@ChannelHandler.Sharable
public class Dispenser extends SimpleChannelInboundHandler<FullHttpRequest>
{
    private Registry registry = Registry.INSTANCE;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception
    {
        System.out.println("here:"+request.uri());

        Controller controller = registry.find(request);

        if (controller == null)
        {
            System.out.println("controller 为空");


            ctx.channel().close().addListener(new ChannelFutureListener()
            {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception
                {
                    if(future.isSuccess())
                        System.out.println("未发现路径");
                    else
                        future.cause().printStackTrace();
                }
            });
        }
        else
        {
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
}
