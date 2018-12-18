package info.yangdian.snowberg;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;

public final class Server
{
    private final int port;

    public Server(int port)
    {
        this.port = port;
    }

    public void start() throws InterruptedException
    {
        EventLoopGroup group = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();

        b.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpInitializer())
                .localAddress(new InetSocketAddress(this.port));

        ChannelFuture f = b.bind().sync();

        f.addListener(new ChannelFutureListener()
        {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception
            {
                if(future.isSuccess())
                    System.out.println("成功启动服务器");
                else
                {
                    future.cause().printStackTrace();
                }
            }
        });

        f.channel().closeFuture().sync();

        group.shutdownGracefully().sync();
    }
}
