package manager;

import database.DatabaseManager;

import model.Device;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    private List<Device> devices;

    public InventoryManager() {

        DatabaseManager
                .initializeDatabase();

        reload();

    }

    // ==========================================
    // LOAD FROM DATABASE
    // ==========================================

    public void reload() {

        devices =
                new ArrayList<>(

                        DatabaseManager
                                .getAllDevices()

                );

    }

    // ==========================================
    // ADD
    // ==========================================

    public void addDevice(
            Device device) {

        DatabaseManager
                .insertDevice(
                        device
                );

        devices.add(
                device
        );

    }

    // ==========================================
    // UPDATE
    // ==========================================

    public void updateDevice(
            Device device) {

        DatabaseManager
                .updateDevice(
                        device
                );

    }

    // ==========================================
    // DELETE
    // ==========================================

    public void deleteDevice(
            Device device) {

        DatabaseManager
                .deleteDevice(
                        device.getId()
                );

        devices.remove(
                device
        );

    }

    // ==========================================
    // GET DEVICES
    // ==========================================

    public List<Device> getDevices() {

        return devices;

    }

}