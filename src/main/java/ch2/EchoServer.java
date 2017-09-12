package ch2;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created on 2017/9/7.
 *
 * <p>引导服务器</p>
 * <p>
 * <li>绑定到服务器，监听并接受传入连接请求的端口</li>
 * <li>配置channel，将有关的 '入站消息'通知给 {@link EchoServerHandler}</li>
 * </p>
 *
 * @author hlj
 * @since 1.0.0
 */
public class EchoServer {

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    private void start() throws InterruptedException {
        final EchoServerHandler handler = new EchoServerHandler();
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(handler);
                        }
                    });
            //catch
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {

            // gracefully 优雅地关闭。
            eventLoopGroup.shutdownGracefully().sync();
        }

    }

}
