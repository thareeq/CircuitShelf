package model;

import java.util.ArrayList;
import java.util.List;

public class Device {

    // ============================================================
    // FIELDS
    // ============================================================

    private int id;

    private String name;

    private String category;

    private String manufacturer;

    private String processor;

    private List<String> protocols;

    private String deviceType;

    private int quantity;

    private String location;

    // NEW:
    // true  = Device has been documented
    // false = Device has not been documented
    private boolean documented;


    // ============================================================
    // CONSTRUCTOR FOR NEW DEVICE
    // ============================================================
    //
    // Used when adding a new device from DeviceDialog.
    //
    // The database will generate the ID automatically.
    // Therefore ID is initially set to 0.
    // ============================================================

    public Device(
            String name,
            String category,
            String manufacturer,
            String processor,
            List<String> protocols,
            String deviceType,
            int quantity,
            String location,
            boolean documented) {

        this(
                0,
                name,
                category,
                manufacturer,
                processor,
                protocols,
                deviceType,
                quantity,
                location,
                documented
        );
    }


    // ============================================================
    // CONSTRUCTOR FOR DEVICE LOADED FROM DATABASE
    // ============================================================
    //
    // Used by DatabaseManager when reading devices from SQLite.
    //
    // In this case, the database already has an ID.
    // ============================================================

    public Device(
            int id,
            String name,
            String category,
            String manufacturer,
            String processor,
            List<String> protocols,
            String deviceType,
            int quantity,
            String location,
            boolean documented) {

        this.id = id;

        this.name = name;

        this.category = category;

        this.manufacturer = manufacturer;

        this.processor = processor;

        // Prevent protocols from ever being null
        this.protocols =
                protocols != null
                        ? new ArrayList<>(protocols)
                        : new ArrayList<>();

        this.deviceType = deviceType;

        this.quantity = quantity;

        this.location = location;

        this.documented = documented;
    }


    // ============================================================
    // ID
    // ============================================================

    public int getId() {

        return id;
    }


    public void setId(
            int id) {

        this.id = id;
    }


    // ============================================================
    // NAME
    // ============================================================

    public String getName() {

        return name;
    }


    public void setName(
            String name) {

        this.name = name;
    }


    // ============================================================
    // CATEGORY
    // ============================================================

    public String getCategory() {

        return category;
    }


    public void setCategory(
            String category) {

        this.category = category;
    }


    // ============================================================
    // MANUFACTURER
    // ============================================================

    public String getManufacturer() {

        return manufacturer;
    }


    public void setManufacturer(
            String manufacturer) {

        this.manufacturer = manufacturer;
    }


    // ============================================================
    // PROCESSOR / MCU / FPGA
    // ============================================================

    public String getProcessor() {

        return processor;
    }


    public void setProcessor(
            String processor) {

        this.processor = processor;
    }


    // ============================================================
    // PROTOCOLS
    // ============================================================

    public List<String> getProtocols() {

        return protocols;
    }


    public void setProtocols(
            List<String> protocols) {

        this.protocols =
                protocols != null
                        ? new ArrayList<>(protocols)
                        : new ArrayList<>();
    }


    // ============================================================
    // PROTOCOLS AS STRING
    // ============================================================
    //
    // Used by MainWindow to display protocols in the JTable.
    //
    // Example:
    //
    // [I2C, SPI]
    //
    // becomes:
    //
    // I2C, SPI
    //
    // ============================================================

    public String getProtocolsAsString() {

        if (protocols == null
                || protocols.isEmpty()) {

            return "";
        }

        return String.join(
                ", ",
                protocols
        );
    }


    // ============================================================
    // DEVICE TYPE
    // ============================================================
    //
    // Used for:
    //
    // Accessories
    // Other Devices
    //
    // Examples:
    //
    // Cable
    // Programmer
    // Logic Analyzer
    // Adapter
    //
    // ============================================================

    public String getDeviceType() {

        return deviceType;
    }


    public void setDeviceType(
            String deviceType) {

        this.deviceType = deviceType;
    }


    // ============================================================
    // QUANTITY
    // ============================================================

    public int getQuantity() {

        return quantity;
    }


    public void setQuantity(
            int quantity) {

        this.quantity = quantity;
    }


    // ============================================================
    // STORAGE LOCATION
    // ============================================================

    public String getLocation() {

        return location;
    }


    public void setLocation(
            String location) {

        this.location = location;
    }


    // ============================================================
    // DOCUMENTATION STATUS
    // ============================================================
    //
    // true:
    //
    //      Device has been documented
    //
    // false:
    //
    //      Device still needs documentation
    //
    // ============================================================

    public boolean isDocumented() {

        return documented;
    }


    public void setDocumented(
            boolean documented) {

        this.documented = documented;
    }


    // ============================================================
    // TO STRING
    // ============================================================
    //
    // Useful for debugging.
    // ============================================================

    @Override
    public String toString() {

        return "Device{" +

                "id=" + id +

                ", name='" + name + '\'' +

                ", category='" + category + '\'' +

                ", manufacturer='" + manufacturer + '\'' +

                ", processor='" + processor + '\'' +

                ", protocols=" + protocols +

                ", deviceType='" + deviceType + '\'' +

                ", quantity=" + quantity +

                ", location='" + location + '\'' +

                ", documented=" + documented +

                '}';

    }

}