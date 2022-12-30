package fr.supermax_8;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RTPCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("recursivertp.use")) return false;
        if (sender instanceof Player) {
            Player p = (Player) sender;
            World w = p.getLocation().getWorld();
            randomTeleport(p, w, 0);
        }
        return false;
    }

    public void randomTeleport(Player p, World w, int iteration) {
        if (!RecursiveRTP.worlds.contains(p.getWorld().getName())) {
            p.sendMessage(RecursiveRTP.errormessage2);
            return;
        }
        Random r = new Random();
        int x = r.nextInt(RecursiveRTP.radius);
        int z = r.nextInt(RecursiveRTP.radius);

        if (r.nextBoolean()) x = -x;
        if (r.nextBoolean()) z = -z;
        int y = 0;
        switch (w.getEnvironment()) {
            case NETHER:
                for (int i = 1; i <= 256; i++) {
                    Block b = w.getBlockAt(x, i, z);
                    Block b2 = w.getBlockAt(x, i + 1, z);
                    Block b4 = w.getBlockAt(x, i + 2, z);
                    Block b3 = w.getBlockAt(x, i - 1, z);
                    if (b.getType().isAir() && b2.getType().isAir() && b4.getType().isAir() && (!b3.getType().isAir() && !b3.getType().equals(Material.BEDROCK))) {
                        y = i;
                        break;
                    }
                }
                break;
            default:
                for (int i = 1; i <= 256; i++) {
                    Block b = w.getBlockAt(x, i, z);
                    Block b2 = w.getBlockAt(x, i + 1, z);
                    Block b3 = w.getBlockAt(x, i - 1, z);
                    if (b.getType().isAir() && b2.getType().isAir() && !b3.getType().isAir()) y = i;
                }
                break;
        }
        if (y == 0) {
            if (iteration > RecursiveRTP.iteration) {
                p.sendMessage(RecursiveRTP.errormessage);
                return;
            }
            iteration++;
            randomTeleport(p, w, iteration);
            return;
        } else switch (w.getBlockAt(x, y - 1, z).getType()) {
            case WATER:
            case LAVA:
                if (iteration > RecursiveRTP.iteration) {
                    p.sendMessage(RecursiveRTP.errormessage);
                    return;
                }
                iteration++;
                randomTeleport(p, w, iteration);
                return;
        }
        p.setNoDamageTicks(50);
        p.teleport(new Location(w, x, y, z));
    }

}