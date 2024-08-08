package me.lel.name.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    // private final Map<UUID, FastBoard> boards = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Player player = event.getPlayer();
        // FastBoard board = new FastBoard(player);

        // board.updateTitle("FastBoard");

        // boards.put(player.getUniqueId(), board);
        // updateBoard(board);
    }

    // private void updateBoard(FastBoard board) {
    //     board.updateLines(
    //             "",
    //             "Players: " + Bukkit.getServer().getOnlinePlayers().size(),
    //             "",
    //             "Kills: " + board.getPlayer().getStatistic(Statistic.PLAYER_KILLS),
    //             ""
    //     );
    // }
}
