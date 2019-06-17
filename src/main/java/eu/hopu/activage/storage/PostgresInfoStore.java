package eu.hopu.activage.storage;

import eu.hopu.activage.services.dto.ImageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PostgresInfoStore implements ImageInfoStore {

    private static final Logger LOG = LogManager.getLogger(PostgresInfoStore.class);


    private final String databaseUrl;
    private final String username;
    private final String password;

    public PostgresInfoStore(String host, String port, String database, String username, String password) {
        this.databaseUrl = "jdbc:postgresql://" + host + ":" + port + "/" + database;
        this.username = username;
        this.password = password;
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            Class.forName("org.postgresql.Driver");

            try (Connection connection = DriverManager.getConnection(databaseUrl, username, password)) {
                LOG.info("Database connection established");
                createTable(connection);
            } catch (SQLException e) {
                LOG.warn("Cannot connect to database. Waiting for database to connect...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                connectToDatabase();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTable(Connection connection) {
        try {
            connection.createStatement()
                    .execute("CREATE TABLE IF NOT EXISTS images (name TEXT, info TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ImageInfo addImageInfo(String imageId, String imageInfo) {
        ImageInfo info = new ImageInfo(imageId, imageInfo);
        try (Connection connection = DriverManager.getConnection(databaseUrl, username, password)) {
            String statement = String.format("INSERT INTO images(name, info) VALUES('%s', '%s')", imageId, imageInfo);

            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                LOG.info("{} record inserted", info);
                return info;
            } else {
                LOG.info("{} record not inserted", info);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.warn("Cannot insert → {}", info);
            return null;
        }
    }

    @Override
    public ImageInfo updateImageInfo(String imageId, String imageInfo) {
        ImageInfo info = new ImageInfo(imageId, imageInfo);
        try (Connection connection = DriverManager.getConnection(databaseUrl, username, password)) {
            String statement = String.format("UPDATE images SET info='%s' WHERE name = '%s'", imageInfo, imageId);

            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                LOG.info("{} record updated", info);
                return info;
            } else {
                LOG.info("{} record not updated", info);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.warn("Cannot update → {}", info);
            return null;
        }
    }


    @Override
    public ImageInfo getImageInfo(String imageId) {
        try (Connection connection = DriverManager.getConnection(databaseUrl, username, password)) {
            String query = String.format("SELECT * FROM images WHERE name = '%s'", imageId);

            Statement preparedStatement = connection.createStatement();

            ResultSet resultSet = preparedStatement.executeQuery(query);
            ImageInfo info = new ImageInfo();
            while (resultSet.next()) {
                info.setImageId(resultSet.getString("name"));
                info.setImageInfo(resultSet.getString("info"));
            }

            if (info.getImageId() == null)
                return null;

            return info;

        } catch (SQLException e) {
            e.printStackTrace();
            LOG.warn("Cannot retrieve data from image → " + imageId);
            return null;
        }
    }


    @Override
    public boolean deleteImageInfo(String imageId) {
        try (Connection connection = DriverManager.getConnection(databaseUrl, username, password)) {
            String statement = String.format("DELETE FROM images WHERE name = '%s'", imageId);

            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                LOG.info("{} imageInfo deleted", imageId);
                return true;
            } else {
                LOG.info("{} imageInfo not deleted", imageId);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.warn("Cannot delete → {}", imageId);
            return false;
        }
    }
}
