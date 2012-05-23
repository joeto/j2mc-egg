package to.joe.j2mc.egg;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class J2MC_Egg extends JavaPlugin implements Listener {

    private class j2Formatter extends Formatter {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

        @Override
        public String format(LogRecord lr) {
            return this.dateformat.format(new Date(lr.getMillis())) + " " + lr.getMessage() + "\n";
        }
    }
    private Logger log;
    private FileHandler fh;

    private boolean canLog;

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onEgg(PlayerInteractEvent event) {
        if (this.canLog) {
            if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                final ItemStack stack = event.getPlayer().getItemInHand();
                if (stack.getTypeId() == 383) {
                    final Location loc = event.getPlayer().getLocation();
                    this.log.info(event.getPlayer().getName() + " " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() + " " + (int) stack.getData().getData());
                }
            }
        }
    }

    @Override
    public void onEnable() {
        this.log = Logger.getLogger("egglog");
        this.canLog = true;
        try {
            this.fh = new FileHandler("eggs.log", true);
        } catch (final SecurityException e) {
            this.canLog = false;
        } catch (final IOException e) {
            this.canLog = false;
        }
        this.fh.setFormatter(new j2Formatter());
        this.log.addHandler(this.fh);
        this.log.setUseParentHandlers(false);
        this.getServer().getPluginManager().registerEvents(this, this);
    }

}
