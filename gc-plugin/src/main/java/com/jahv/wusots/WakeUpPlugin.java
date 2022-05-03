package com.jahv.wusots;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.plugin.Plugin;
import emu.grasscutter.plugin.PluginManager;
import emu.grasscutter.server.event.EventHandler;
import emu.grasscutter.server.event.HandlerPriority;
import emu.grasscutter.server.event.game.ReceivePacketEvent;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketAvatarLifeStateChangeNotify;

import java.util.Locale;
import java.util.ResourceBundle;

public class WakeUpPlugin extends Plugin {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("locale/LAN", Locale.US);

    @Override
    public void onLoad() {
        Grasscutter.getLogger().debug(bundle.getString("plugin_load"));
    }

    @Override
    public void onEnable() {
        PluginManager pluginManager = Grasscutter.getPluginManager();

        var handler = new EventHandler<>(ReceivePacketEvent.class);
        handler.priority(HandlerPriority.LOW);
        handler.listener((event) -> {
            if (event.getPacketId() != PacketOpcodes.EnterTransPointRegionNotify) return;
            var player = event.getGameSession().getPlayer();
            player.getTeamManager().getActiveTeam().forEach(entity -> {
                boolean isAlive = entity.isAlive();
                entity.setFightProperty(
                        FightProperty.FIGHT_PROP_CUR_HP,
                        entity.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP)
                );
                entity.getWorld().broadcastPacket(new PacketAvatarFightPropUpdateNotify(entity.getAvatar(), FightProperty.FIGHT_PROP_CUR_HP));
                if (!isAlive) {
                    entity.getWorld().broadcastPacket(new PacketAvatarLifeStateChangeNotify(entity.getAvatar()));
                }
            });
        });
        pluginManager.registerListener(handler);

        Grasscutter.getLogger().info(bundle.getString("plugin_enable"));
    }

    @Override
    public void onDisable() {
        Grasscutter.getLogger().info(bundle.getString("plugin_disable"));
    }
}
