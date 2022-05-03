package emu.grasscutter.server.packet;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.EnterTransPointRegionNotify)
public class HandlerWakeUpStatuesOfTheSeven extends PacketHandler {
    @Override
    public void handle(GameSession gameSession, byte[] bytes, byte[] bytes1) throws Exception {

    }
}
