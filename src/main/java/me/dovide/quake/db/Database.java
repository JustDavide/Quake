package me.dovide.quake.db;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.obj.PlayerStats;
import me.dovide.quake.utils.Config;
import org.bukkit.Bukkit;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.UUID;

public class Database {

    private final Config config;

    public Database(QuakeMain instance){
        this.config = instance.getConfig();
    }

    private Connection connection;

    public Connection getConnection() throws SQLException {

        if(connection != null){
            return connection;
        }

        String url = config.getString("db.url");
        String port = config.getString("db.port");
        String database = config.getString("db.database");
        String user = config.getString("db.user");
        String password = config.getString("db.password");

        Connection connection = DriverManager.getConnection("jdbc:mysql://" +
                        url + ":" + port + "/" + database + "?useSSL=false",
                user, password);

        this.connection = connection;
        return connection;

    }

    public void initDatabase() throws SQLException{

        if(connection == null)
            return;

        Statement statsStatement = getConnection().createStatement();
        String playerStats = "CREATE TABLE IF NOT EXISTS player_stats(player_uuid VARCHAR(36), kills int, wins int)";
        statsStatement.execute(playerStats);

        statsStatement.close();

        Bukkit.getLogger().info("Tabelle Create / Caricate Correttamente");

    }

    public void registerPlayer(PlayerStats stats) throws SQLException{
        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO player_stats(player_uuid, kills, wins) VALUES (?, ?, ?)");

        statement.setString(1, stats.getPlayerUUID().toString());
        statement.setInt(2, stats.getKills());
        statement.setInt(3, stats.getWins());

        statement.executeUpdate();
        statement.close();
    }

    public PlayerStats getPlayerStats(UUID playerUUID) throws SQLException{
        Statement statement = getConnection().createStatement();
        String selection = "SELECT * FROM player_STATS WHERE player_uuid = " + playerUUID;

        ResultSet results = statement.executeQuery(selection);

        if(results.next()){ // Prendo solo il primo risultato in quanto di player UUID dovrebbe essercene solo uno
            UUID uuid = UUID.fromString(results.getString("player_uuid"));
            int kills = results.getInt("kills");
            int wins = results.getInt("wins");

            statement.close();
            return new PlayerStats(uuid, kills, wins);
        }

        statement.close();
        return new PlayerStats(playerUUID, 0, 0); // Fail-Safe se il player non è ancora stato registrato nel db
    }

}
