package database;

import model.Device;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseManager {

    // ============================================================
    // DATABASE DIRECTORY
    // ============================================================
    //
    // Windows:
    //
    // C:\Users\<username>\AppData\Local\ElectronicsInventory
    //
    // ============================================================

    private static final Path DATABASE_DIRECTORY;

    // ============================================================
    // DATABASE FILE
    // ============================================================

    private static final Path DATABASE_FILE;

    // ============================================================
    // DATABASE URL
    // ============================================================

    private static final String DATABASE_URL;


    // ============================================================
    // STATIC INITIALIZATION
    // ============================================================

    static {

        String localAppData =
                System.getenv(
                        "LOCALAPPDATA"
                );

        // --------------------------------------------------------
        // Fallback if LOCALAPPDATA is unavailable
        // --------------------------------------------------------

        if (localAppData == null
                || localAppData.isBlank()) {

            localAppData =
                    System.getProperty(
                            "user.home"
                    );

        }

        DATABASE_DIRECTORY =
                Paths.get(
                        localAppData,
                        "ElectronicsInventory"
                );

        DATABASE_FILE =
                DATABASE_DIRECTORY.resolve(
                        "electronics_inventory.db"
                );

        DATABASE_URL =
                "jdbc:sqlite:"
                        + DATABASE_FILE
                        .toAbsolutePath();

    }


    // ============================================================
    // INITIALIZE DATABASE
    // ============================================================
    //
    // This method:
    //
    // 1. Loads SQLite JDBC
    // 2. Creates application directory
    // 3. Creates devices table if necessary
    // 4. Migrates old database to add "documented"
    //
    // ============================================================

    public static void initializeDatabase() {

        try {

            // ----------------------------------------------------
            // LOAD SQLITE JDBC DRIVER
            // ----------------------------------------------------

            Class.forName(
                    "org.sqlite.JDBC"
            );

            // ----------------------------------------------------
            // CREATE DATABASE DIRECTORY
            // ----------------------------------------------------

            Files.createDirectories(
                    DATABASE_DIRECTORY
            );

            // ----------------------------------------------------
            // CREATE DATABASE / TABLE
            // ----------------------------------------------------

            try (
                    Connection connection =
                            getConnection();

                    Statement statement =
                            connection
                                    .createStatement()
            ) {

                String sql = """

                        CREATE TABLE IF NOT EXISTS devices (

                            id INTEGER PRIMARY KEY AUTOINCREMENT,

                            name TEXT NOT NULL,

                            category TEXT NOT NULL,

                            manufacturer TEXT,

                            processor TEXT,

                            protocols TEXT,

                            device_type TEXT,

                            quantity INTEGER NOT NULL DEFAULT 1,

                            location TEXT,

                            documented INTEGER NOT NULL DEFAULT 0

                        )

                        """;

                statement.execute(
                        sql
                );

                // ------------------------------------------------
                // DATABASE MIGRATION
                // ------------------------------------------------
                //
                // If this application was used before the
                // "documented" feature was introduced, the
                // existing devices table will not contain the
                // documented column.
                //
                // CREATE TABLE IF NOT EXISTS does NOT modify an
                // existing table.
                //
                // Therefore we check whether the column exists and
                // add it if required.
                // ------------------------------------------------

                if (!columnExists(
                        connection,
                        "devices",
                        "documented"
                )) {

                    statement.execute(
                            """
                            ALTER TABLE devices
                            ADD COLUMN documented INTEGER NOT NULL DEFAULT 0
                            """
                    );

                    System.out.println(
                            "Database upgraded: "
                                    + "added documented column."
                    );

                }

            }

            System.out.println(
                    "Database initialized successfully."
            );

            System.out.println(
                    "Database location: "
                            + DATABASE_FILE
                            .toAbsolutePath()
            );

        } catch (
                ClassNotFoundException e) {

            throw new RuntimeException(
                    "SQLite JDBC driver not found. "
                            + "Make sure sqlite-jdbc.jar "
                            + "is included in the classpath.",
                    e
            );

        } catch (
                Exception e) {

            throw new RuntimeException(
                    "Failed to initialize database.",
                    e
            );

        }

    }


    // ============================================================
    // CHECK IF COLUMN EXISTS
    // ============================================================

    private static boolean columnExists(
            Connection connection,
            String tableName,
            String columnName)
            throws SQLException {

        String sql =
                "PRAGMA table_info("
                        + tableName
                        + ")";

        try (
                Statement statement =
                        connection
                                .createStatement();

                ResultSet resultSet =
                        statement
                                .executeQuery(
                                        sql
                                )
        ) {

            while (
                    resultSet.next()
            ) {

                String existingColumn =
                        resultSet.getString(
                                "name"
                        );

                if (columnName
                        .equalsIgnoreCase(
                                existingColumn
                        )) {

                    return true;

                }

            }

        }

        return false;

    }


    // ============================================================
    // GET DATABASE CONNECTION
    // ============================================================

    public static Connection getConnection()
            throws SQLException {

        return DriverManager
                .getConnection(
                        DATABASE_URL
                );

    }


    // ============================================================
    // INSERT DEVICE
    // ============================================================

    public static void insertDevice(
            Device device) {

        String sql = """

                INSERT INTO devices (

                    name,
                    category,
                    manufacturer,
                    processor,
                    protocols,
                    device_type,
                    quantity,
                    location,
                    documented

                )

                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)

                """;

        try (
                Connection connection =
                        getConnection();

                PreparedStatement statement =
                        connection
                                .prepareStatement(
                                        sql,
                                        Statement
                                                .RETURN_GENERATED_KEYS
                                )
        ) {

            // ----------------------------------------------------
            // NAME
            // ----------------------------------------------------

            statement.setString(
                    1,
                    device.getName()
            );

            // ----------------------------------------------------
            // CATEGORY
            // ----------------------------------------------------

            statement.setString(
                    2,
                    device.getCategory()
            );

            // ----------------------------------------------------
            // MANUFACTURER
            // ----------------------------------------------------

            statement.setString(
                    3,
                    device.getManufacturer()
            );

            // ----------------------------------------------------
            // PROCESSOR
            // ----------------------------------------------------

            statement.setString(
                    4,
                    device.getProcessor()
            );

            // ----------------------------------------------------
            // PROTOCOLS
            // ----------------------------------------------------

            statement.setString(
                    5,
                    protocolsToDatabaseString(
                            device.getProtocols()
                    )
            );

            // ----------------------------------------------------
            // DEVICE TYPE
            // ----------------------------------------------------

            statement.setString(
                    6,
                    device.getDeviceType()
            );

            // ----------------------------------------------------
            // QUANTITY
            // ----------------------------------------------------

            statement.setInt(
                    7,
                    device.getQuantity()
            );

            // ----------------------------------------------------
            // LOCATION
            // ----------------------------------------------------

            statement.setString(
                    8,
                    device.getLocation()
            );

            // ----------------------------------------------------
            // DOCUMENTED
            //
            // SQLite:
            //
            // true  -> 1
            // false -> 0
            //
            // ----------------------------------------------------

            statement.setInt(
                    9,
                    device.isDocumented()
                            ? 1
                            : 0
            );

            // ----------------------------------------------------
            // EXECUTE
            // ----------------------------------------------------

            statement.executeUpdate();

            // ----------------------------------------------------
            // GET GENERATED DATABASE ID
            // ----------------------------------------------------

            try (
                    ResultSet generatedKeys =
                            statement
                                    .getGeneratedKeys()
            ) {

                if (generatedKeys.next()) {

                    device.setId(
                            generatedKeys
                                    .getInt(
                                            1
                                    )
                    );

                }

            }

        } catch (
                SQLException e) {

            throw new RuntimeException(
                    "Failed to insert device: "
                            + device.getName(),
                    e
            );

        }

    }


    // ============================================================
    // GET ALL DEVICES
    // ============================================================

    public static List<Device> getAllDevices() {

        List<Device> devices =
                new ArrayList<>();

        String sql = """

                SELECT

                    id,
                    name,
                    category,
                    manufacturer,
                    processor,
                    protocols,
                    device_type,
                    quantity,
                    location,
                    documented

                FROM devices

                ORDER BY name COLLATE NOCASE

                """;

        try (
                Connection connection =
                        getConnection();

                PreparedStatement statement =
                        connection
                                .prepareStatement(
                                        sql
                                );

                ResultSet resultSet =
                        statement
                                .executeQuery()
        ) {

            while (
                    resultSet.next()
            ) {

                // ------------------------------------------------
                // PROTOCOLS
                // ------------------------------------------------

                List<String> protocols =
                        databaseStringToProtocols(
                                resultSet
                                        .getString(
                                                "protocols"
                                        )
                        );

                // ------------------------------------------------
                // CREATE DEVICE
                // ------------------------------------------------

                Device device =
                        new Device(

                                resultSet
                                        .getInt(
                                                "id"
                                        ),

                                resultSet
                                        .getString(
                                                "name"
                                        ),

                                resultSet
                                        .getString(
                                                "category"
                                        ),

                                resultSet
                                        .getString(
                                                "manufacturer"
                                        ),

                                resultSet
                                        .getString(
                                                "processor"
                                        ),

                                protocols,

                                resultSet
                                        .getString(
                                                "device_type"
                                        ),

                                resultSet
                                        .getInt(
                                                "quantity"
                                        ),

                                resultSet
                                        .getString(
                                                "location"
                                        ),

                                resultSet
                                        .getInt(
                                                "documented"
                                        ) == 1

                        );

                devices.add(
                        device
                );

            }

        } catch (
                SQLException e) {

            throw new RuntimeException(
                    "Failed to load devices from database.",
                    e
            );

        }

        return devices;

    }


    // ============================================================
    // UPDATE DEVICE
    // ============================================================

    public static void updateDevice(
            Device device) {

        String sql = """

                UPDATE devices

                SET

                    name = ?,
                    category = ?,
                    manufacturer = ?,
                    processor = ?,
                    protocols = ?,
                    device_type = ?,
                    quantity = ?,
                    location = ?,
                    documented = ?

                WHERE id = ?

                """;

        try (
                Connection connection =
                        getConnection();

                PreparedStatement statement =
                        connection
                                .prepareStatement(
                                        sql
                                )
        ) {

            // ----------------------------------------------------
            // NAME
            // ----------------------------------------------------

            statement.setString(
                    1,
                    device.getName()
            );

            // ----------------------------------------------------
            // CATEGORY
            // ----------------------------------------------------

            statement.setString(
                    2,
                    device.getCategory()
            );

            // ----------------------------------------------------
            // MANUFACTURER
            // ----------------------------------------------------

            statement.setString(
                    3,
                    device.getManufacturer()
            );

            // ----------------------------------------------------
            // PROCESSOR
            // ----------------------------------------------------

            statement.setString(
                    4,
                    device.getProcessor()
            );

            // ----------------------------------------------------
            // PROTOCOLS
            // ----------------------------------------------------

            statement.setString(
                    5,
                    protocolsToDatabaseString(
                            device.getProtocols()
                    )
            );

            // ----------------------------------------------------
            // DEVICE TYPE
            // ----------------------------------------------------

            statement.setString(
                    6,
                    device.getDeviceType()
            );

            // ----------------------------------------------------
            // QUANTITY
            // ----------------------------------------------------

            statement.setInt(
                    7,
                    device.getQuantity()
            );

            // ----------------------------------------------------
            // LOCATION
            // ----------------------------------------------------

            statement.setString(
                    8,
                    device.getLocation()
            );

            // ----------------------------------------------------
            // DOCUMENTED
            // ----------------------------------------------------

            statement.setInt(
                    9,
                    device.isDocumented()
                            ? 1
                            : 0
            );

            // ----------------------------------------------------
            // ID
            // ----------------------------------------------------

            statement.setInt(
                    10,
                    device.getId()
            );

            // ----------------------------------------------------
            // EXECUTE
            // ----------------------------------------------------

            int affectedRows =
                    statement
                            .executeUpdate();

            if (affectedRows == 0) {

                throw new RuntimeException(
                        "Device was not found in database. "
                                + "ID: "
                                + device.getId()
                );

            }

        } catch (
                SQLException e) {

            throw new RuntimeException(
                    "Failed to update device: "
                            + device.getName(),
                    e
            );

        }

    }


    // ============================================================
    // DELETE DEVICE
    // ============================================================

    public static void deleteDevice(
            Device device) {

        if (device == null) {

            return;

        }

        deleteDevice(
                device.getId()
        );

    }


    // ============================================================
    // DELETE DEVICE BY ID
    // ============================================================

    public static void deleteDevice(
            int id) {

        String sql = """

                DELETE FROM devices

                WHERE id = ?

                """;

        try (
                Connection connection =
                        getConnection();

                PreparedStatement statement =
                        connection
                                .prepareStatement(
                                        sql
                                )
        ) {

            statement.setInt(
                    1,
                    id
            );

            statement.executeUpdate();

        } catch (
                SQLException e) {

            throw new RuntimeException(
                    "Failed to delete device. ID: "
                            + id,
                    e
            );

        }

    }


    // ============================================================
    // CONVERT PROTOCOL LIST TO DATABASE STRING
    // ============================================================
    //
    // Java:
    //
    // [UART, I2C, SPI]
    //
    // SQLite:
    //
    // UART,I2C,SPI
    //
    // ============================================================

    private static String protocolsToDatabaseString(
            List<String> protocols) {

        if (protocols == null
                || protocols.isEmpty()) {

            return "";

        }

        return String.join(
                ",",
                protocols
        );

    }


    // ============================================================
    // CONVERT DATABASE STRING TO PROTOCOL LIST
    // ============================================================
    //
    // SQLite:
    //
    // UART,I2C,SPI
    //
    // Java:
    //
    // [UART, I2C, SPI]
    //
    // ============================================================

    private static List<String> databaseStringToProtocols(
            String protocolsString) {

        List<String> protocols =
                new ArrayList<>();

        if (protocolsString == null
                || protocolsString.isBlank()) {

            return protocols;

        }

        Arrays.stream(
                        protocolsString
                                .split(",")
                )

                .map(
                        String::trim
                )

                .filter(
                        protocol ->
                                !protocol
                                        .isEmpty()
                )

                .forEach(
                        protocols::add
                );

        return protocols;

    }


    // ============================================================
    // GET DATABASE FILE
    // ============================================================
    //
    // Useful later for:
    //
    // Backup Database
    // Restore Database
    // Open Database Location
    //
    // ============================================================

    public static Path getDatabaseFile() {

        return DATABASE_FILE;

    }


    // ============================================================
    // GET DATABASE DIRECTORY
    // ============================================================

    public static Path getDatabaseDirectory() {

        return DATABASE_DIRECTORY;

    }

}