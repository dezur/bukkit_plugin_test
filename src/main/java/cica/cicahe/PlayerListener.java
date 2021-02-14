package cica.cicahe;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Set;

public class PlayerListener implements Listener {

    private final Cicahe plugin;

    public PlayerListener(Cicahe instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {       // JÁTÉKOS CSATLAKOZIK ESEMÉNY
        plugin.getLogger().info("Hinyje!");         // Szerver konzoljára logol
        Player player = event.getPlayer();      // A játékos, aki épp csatlakozott
        player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 2); // hang lejátszása a játékosnak

        PlayerInventory inventory = player.getInventory(); // A játékos inventoryja
        ItemStack itemstack = new ItemStack(Material.GOLD_INGOT, 1);    // új itemstack 1db arannyal
        inventory.addItem(itemstack);   // itemstack hozzáadása a játékos inventoryjába
        player.sendMessage("Hello " + player.getDisplayName() + "! Neszebazmeg egy arany!");     // Játékos üdvözlése
    }

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event) {      // JÁTÉKOS BASZTAT EGY BLOKKOT ESEMÉNY
        Player player = event.getPlayer();          // Lekéri a blokkinterakciót végző játékost
        Block targetBlock = player.getTargetBlock((Set<Material>) null, 200);           // Lekéri a játékos blokkját, amire néz
        if (player.getItemInHand().getType() == Material.FISHING_ROD) {     // Ha a kezében lévő tárgy egy horgászbot...
            player.getWorld().strikeLightning(targetBlock.getLocation());       // Villámcsapás előidézése a target blokk helyére
        } else if (player.getItemInHand().getType() == Material.IRON_HOE && targetBlock.getType() == Material.STONE){   // Ha kézben vaskapa és a target blokk STONE akkor..
            targetBlock.setType(Material.DIAMOND_ORE);      // Megváltoztatja a targetblokk típusát
        } else if (targetBlock.getType() == Material.DANDELION || targetBlock.getType() == Material.POPPY) {    // Ha a targetblokk dandelion vagy poppy...
            World world = player.getWorld();        // lekérjük a játékos világát
            ItemStack itemstack = new ItemStack(Material.GOLD_INGOT, 1);       // új itemstack 1db arannyal
            targetBlock.setType(Material.AIR);      // a virág cseréje levegőre (eltűntetés)
            world.dropItem(targetBlock.getLocation(), itemstack);   // itemstack dobása a virág koordinátáján.
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getLogger().info(event.getPlayer().getName() + " left the server! :'(");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (plugin.isDebugging(event.getPlayer())) {
            Location from = event.getFrom();
            Location to = event.getTo();

            plugin.getLogger().info(String.format("From %.2f,%.2f,%.2f to %.2f,%.2f,%.2f", from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ()));
        }
    }

}
