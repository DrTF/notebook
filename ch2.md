#### Echo Server
****************************

- 所有的netty服务器都需要以下两部分
  - 至少一个 `ChannelHandler`, 实现了 服务器端对客户端接受的数据的 业务处理逻辑
  - `引导` 配置服务器的启动代码，至少会将服务器绑定到要监听的端口上

- ChannelHandler
   - 针对不同类型的事件来调用 `ChannelHandler` 
   - 程序通过实现或扩展`ChannelHandler` 来挂到事件的生命周期，并且提供自定义的业务逻辑
   - 架构上讲， `ChannelHandler` 有助于保持业务逻辑与网络处理代码的分离。

- 引导服务器
  - 绑定到服务器，监听并接受传入连接请求的端口
  - 配置`Channel`  
  > 主要步骤
  > - 创建 `ServerBootStrap`的实例 引导和绑定服务器
  > - 创建`NioEventLoopGroup`进行事件处理， i.e. 接受新连接 & read/write数据
  > - 指定服务器绑定本地的 `InetSocketAddress`
  > - 使用 EchoServerHandler 初始化每一个新的 Channel
  > - 调用 ServerBootStrap.bind() 绑定服务器
  > - 服务器初始化完毕
    
#### Echo Client
*********************************    
- ChannelHandler
  - 使用`SimpleChannelInBoundHandler`处理所有任务
    - channelActive() //和服务器连接建立后被调用
    - channelRead0() //从服务器接收到一条消息时被调用
    - exceptionCaught() //处理过程中发生异常时被调用
- 引导
  - 大致同 EchoServer
  - client 主动调用 BootStrap.connect()连接远程节点   
   