package ch2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created on 2017/9/10.
 *
 * <ul>
 * <p>连接服务器</p>
 * <p>发送消息</p>
 * <p>接受服务器响应的消息</p>
 * <p>关闭连接</p>
 * </ul>
 *
 * @author hlj
 * @since 1.0.0
 */
@ChannelHandler.Sharable //标记该类的实例 可以被多个 channel共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {


    /**
     * 连接建立时被调用， 确保数据将会尽可能快地写入服务器
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    /**
     * 每当接收数据时，都会调用这个方法。
     *
     * 服务器发送的消息可能会被分块接收， 此时 channelRead0() 可能会被调用多次
     *
     * 作为一个面向流的协议，TCP保证了字节数组将会按照服务器发送的顺序被接收
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("client received: " + msg.toString(CharsetUtil.UTF_8));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();  //发生异常时 关闭 channel
    }
}
