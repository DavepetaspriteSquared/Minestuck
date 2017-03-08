package com.mraof.minestuck.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class PesterchumPacket extends MinestuckPacket
{
  public static final byte PESTER_LIST = 0;

  private byte type;

  private List<String> list;


  @Override
  public MinestuckPacket generatePacket(Object... data)
  {
    byte type = (Byte) data[0];
    this.data.writeByte(type);
    if(type == PESTER_LIST)
    {
      if(data.length > 1)
      {
        List<String> list = (List<String>) data[1];
        for(String pesterHandle : list)
          writeString(this.data, pesterHandle+"\n");
      }
    }

    return this;
  }

  @Override
  public MinestuckPacket consumePacket(ByteBuf data)
  {
    this.type = data.readByte();

    if(type == PESTER_LIST)
    {

      if(data.readableBytes() > 0)
      {
        list = new ArrayList<String>();

        while(data.readableBytes() > 0)
        {
          list.add(readLine(data));
        }
      }
    }

    return this;
  }

  @Override
  public void execute(EntityPlayer player)
  {

  }

  @Override
  public EnumSet<Side> getSenderSide()
  {
    return EnumSet.allOf(Side.class);
  }
}