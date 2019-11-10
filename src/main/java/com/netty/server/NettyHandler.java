package com.netty.server;



import com.netty.OPStrategy.OPStrategy;
import com.netty.OPStrategy.OP_0;
import com.netty.role.Role;
import com.netty.role.RoleMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import jdk.nashorn.internal.runtime.Debug;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NettyHandler extends ChannelInboundHandlerAdapter {


	public String ID;
	public ChannelHandlerContext context;
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Server： " + ctx.channel().remoteAddress() + "已连接！");
		context = ctx;
		ID = UUID.randomUUID().toString();
		RoleMap.roleMapHashMap.put(ID,new Role(this));
//		STimer.exacute(this);

	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Server： " + ctx.channel().remoteAddress() + "断开连接！");
		RoleMap.roleMapHashMap.remove(ID);

	}

	public boolean frameHeadConfirm(byte[] origin){
		byte[] lb = new byte[3];
		lb[0] = (byte)11;
		lb[1] = (byte)55;
		lb[2] = (byte)101;
		boolean answer = true;
		for (int i =0;i<3;i++
			 ) {
			if (origin[origin.length-3+i] != lb[i])
				return false;
		}
		return  true;
	}
	public void  process(byte[] data){


		if (frameHeadConfirm(data) == false){
			System.out.println("帧验证异常");
			return;
		}

		DataModel model = new DataModel(data);
		if (model.modelStatus == -1)
			return;
		//System.out.println(model.mainCode+"<>"+model.subCode+"<>"+model.strContent+"<>"+model.getFloatStr());
		//ctx.channel().writeAndFlush("我是务器的消息!!".getBytes());


		//DataModel model2 = new DataModel((byte)5,(byte)5,"89898989",new float[]{1.312f,123.2f});
		OPStrategy strategy = null;
		switch (model.mainCode){

			case 0:
				strategy = new OP_0();
				break;

			default:
				break;
		}
		if(strategy != null){

			strategy.doSomething(this,model);
		}


	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			ByteBuf buf = (ByteBuf) msg;
			byte[] bytes = new byte[buf.readableBytes()];
			buf.readBytes(bytes);

			List<Integer> frameList = new ArrayList<>();

			for (int index = 0;index<bytes.length;index++){
				//System.out.println(index+"<>"+(bytes[index]));
				if (bytes[index] == 123){

					if (DataModel.equal(index,bytes,DataModel.frameHead())){
						frameList.add(index);
					}

				}

			}

			List<byte[]> bagList = new ArrayList<>();

			if (frameList.size()==1){
				byte[] newByte = new byte[bytes.length - 10];
				for (int i =10;i<bytes.length;i++){
					newByte[i -10] = bytes[i];

				}
				process(newByte);

			}
			if (frameList.size()>1)
			{


				//System.out.println("有两个以上的包！");
				 //两个以上的包
				 for (int i =0;i<frameList.size() -1;i++){

				 	byte[] tb = new byte[frameList.get(i+1) - frameList.get(i) -10];

				 	for (int j =0;j<tb.length;j++){

						tb[j] =bytes[frameList.get(i)+10+j];
				 	}

				 	process(tb);

				 }
			}





		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("Server： Exception  " + ctx.channel().remoteAddress() + cause.getMessage());
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		IdleState state = ((IdleStateEvent) evt).state();
		if (state == IdleState.READER_IDLE) {
			//System.out.println(state);
		}
		if (state == IdleState.WRITER_IDLE) {
			//System.out.println(state);
		}
		if (state == IdleState.ALL_IDLE) {
			//System.out.println(state);
		}
		// ctx.close();
	}

}
