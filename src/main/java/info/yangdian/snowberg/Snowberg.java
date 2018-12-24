package info.yangdian.snowberg;

import info.yangdian.husky.log.Logger;
import info.yangdian.husky.log.LoggerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public final class Snowberg
{
    private static final Logger logger = LoggerFactory.getInstance(Snowberg.class);

    private final int port;

    private List<Module> modules = new ArrayList<>();

    public void addModule(Module module)
    {
        this.modules.add(module);
    }

    public Snowberg(int port)
    {
        this.port = port;
    }

    public void start() throws InterruptedException
    {
        for (Module module : modules)
        {
            module.register(PathTrie.PATHS);
        }

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
                if (future.isSuccess())
                    logger.info("snowberg started.");
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
