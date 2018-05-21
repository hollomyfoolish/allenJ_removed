package com.allenJ.run.byteencode;

import java.io.IOException;

import org.msgpack.MessagePack;
import org.msgpack.type.ArrayValue;
import org.msgpack.type.Value;

import com.allenJ.run.byteencode.po.UserInfo;

public class MsgpackTest {

	public static void main(String[] args){
		MessagePack pack = new MessagePack();
//		pack.register(UserInfo.class);
		UserInfo user = new UserInfo(1, "Tom");
		try {
			byte[] bytes = pack.write(user);
			System.out.println("encode length: " + bytes.length);
			
			Value v = pack.read(bytes);
			StringBuilder sb = new StringBuilder("decode: ");
			v.toString(sb);
			System.out.println(v.getType());
			System.out.println(sb);
			ArrayValue vs = v.asArrayValue();
			System.out.println(vs.getElementArray()[0].getType());
			System.out.println(vs.getElementArray()[1].getType());
			System.out.println(vs.getElementArray()[1].asRawValue().getString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
