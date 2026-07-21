package ui;

import model.Device;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceDialog extends JDialog {

    // ============================================================
    // COLORS
    // ============================================================

    private static final Color BACKGROUND =
            new Color(15, 23, 42);

    private static final Color PANEL =
            new Color(30, 41, 59);

    private static final Color INPUT =
            new Color(51, 65, 85);

    private static final Color TEXT =
            new Color(226, 232, 240);

    private static final Color TEXT_SECONDARY =
            new Color(148, 163, 184);

    private static final Color BLUE =
            new Color(59, 130, 246);

    private static final Color BLUE_HOVER =
            new Color(37, 99, 235);

    private static final Color BORDER =
            new Color(71, 85, 105);


    // ============================================================
    // DEVICE
    // ============================================================

    private Device device;

    private final Device existingDevice;


    // ============================================================
    // FORM FIELDS
    // ============================================================

    private JTextField nameField;

    private JComboBox<String> categoryCombo;

    private JTextField manufacturerField;

    private JLabel processorLabel;

    private JTextField processorField;

    private JPanel protocolPanel;

    private JCheckBox uartCheck;

    private JCheckBox i2cCheck;

    private JCheckBox spiCheck;

    private JLabel deviceTypeLabel;

    private JTextField deviceTypeField;

    private JSpinner quantitySpinner;

    private JTextField locationField;

    private JCheckBox documentedCheck;


    // ============================================================
    // DYNAMIC FIELD PANELS
    // ============================================================

    private JPanel processorRow;

    private JPanel protocolRow;

    private JPanel deviceTypeRow;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public DeviceDialog(
            Frame parent,
            Device existingDevice) {

        super(
                parent,
                existingDevice == null
                        ? "Add Device"
                        : "Edit Device",
                true
        );

        this.existingDevice =
                existingDevice;

        // --------------------------------------------------------
        // DIALOG SETTINGS
        // --------------------------------------------------------

        setDefaultCloseOperation(
                JDialog.DISPOSE_ON_CLOSE
        );

        setResizable(
                true
        );

        getContentPane()
                .setBackground(
                        BACKGROUND
                );

        // --------------------------------------------------------
        // CREATE MAIN UI
        // --------------------------------------------------------

        JPanel mainPanel =
                createMainPanel();

        setContentPane(
                mainPanel
        );

        // --------------------------------------------------------
        // LOAD EXISTING DEVICE
        // --------------------------------------------------------

        if (existingDevice != null) {

            loadExistingDevice();

        }

        // --------------------------------------------------------
        // UPDATE DYNAMIC FIELDS
        // --------------------------------------------------------

        updateFieldsForCategory();

        // --------------------------------------------------------
        // IMPORTANT:
        //
        // Let Swing calculate the required dialog size.
        //
        // This prevents the bottom fields such as
        // Storage Location and Documentation from being cut off.
        // --------------------------------------------------------

        pack();

        // --------------------------------------------------------
        // MINIMUM SIZE
        // --------------------------------------------------------

        setMinimumSize(
                new Dimension(
                        800,
                        650
                )
        );

        // --------------------------------------------------------
        // Prevent the dialog from becoming taller than
        // the available screen.
        // --------------------------------------------------------

        Dimension screenSize =
                Toolkit
                        .getDefaultToolkit()
                        .getScreenSize();

        int preferredWidth =
                Math.max(
                        800,
                        getWidth()
                );

        int preferredHeight =
                Math.min(
                        getHeight(),
                        screenSize.height - 100
                );

        setSize(
                preferredWidth,
                preferredHeight
        );

        // --------------------------------------------------------
        // CENTER ON PARENT
        // --------------------------------------------------------

        setLocationRelativeTo(
                parent
        );

    }


    // ============================================================
    // CREATE MAIN PANEL
    // ============================================================

    private JPanel createMainPanel() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        // --------------------------------------------------------
        // HEADER
        // --------------------------------------------------------

        mainPanel.add(
                createHeader(),
                BorderLayout.NORTH
        );

        // --------------------------------------------------------
        // FORM
        // --------------------------------------------------------

        JPanel formPanel =
                createFormPanel();

        // --------------------------------------------------------
        // SCROLL PANE
        //
        // This provides an additional safeguard for smaller
        // displays or future fields added to the dialog.
        // --------------------------------------------------------

        JScrollPane scrollPane =
                new JScrollPane(
                        formPanel
                );

        scrollPane.setBorder(
                BorderFactory
                        .createEmptyBorder()
        );

        scrollPane
                .getViewport()
                .setBackground(
                        BACKGROUND
                );

        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants
                        .HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants
                        .VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPane
                .getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // --------------------------------------------------------
        // BUTTONS
        // --------------------------------------------------------

        mainPanel.add(
                createButtonPanel(),
                BorderLayout.SOUTH
        );

        return mainPanel;

    }


    // ============================================================
    // HEADER
    // ============================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel();

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setBackground(
                PANEL
        );

        header.setBorder(
                new EmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        JLabel title =
                new JLabel(
                        existingDevice == null
                                ? "ADD NEW DEVICE"
                                : "EDIT DEVICE"
                );

        title.setForeground(
                TEXT
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        21
                )
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitle =
                new JLabel(
                        existingDevice == null
                                ? "Add hardware to your CircuitShelf inventory"
                                : "Update hardware information"
                );

        subtitle.setForeground(
                TEXT_SECONDARY
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        header.add(
                title
        );

        header.add(
                Box.createVerticalStrut(
                        5
                )
        );

        header.add(
                subtitle
        );

        return header;

    }


    // ============================================================
    // FORM PANEL
    // ============================================================

    private JPanel createFormPanel() {

        JPanel form =
                new JPanel();

        form.setBackground(
                BACKGROUND
        );

        form.setBorder(
                new EmptyBorder(
                        25,
                        40,
                        25,
                        40
                )
        );

        form.setLayout(
                new BoxLayout(
                        form,
                        BoxLayout.Y_AXIS
                )
        );


        // ========================================================
        // DEVICE NAME
        // ========================================================

        nameField =
                createTextField();

        form.add(
                createFormRow(
                        "Device Name",
                        nameField
                )
        );

        form.add(
                createVerticalSpace()
        );


        // ========================================================
        // CATEGORY
        // ========================================================

        categoryCombo =
                new JComboBox<>(
                        new String[]{
                                "SBC",
                                "MCU",
                                "FPGA",
                                "Sensors",
                                "Accessories",
                                "Other Devices"
                        }
                );

        styleComboBox(
                categoryCombo
        );

        form.add(
                createFormRow(
                        "Category",
                        categoryCombo
                )
        );

        form.add(
                createVerticalSpace()
        );


        // ========================================================
        // MANUFACTURER
        // ========================================================

        manufacturerField =
                createTextField();

        form.add(
                createFormRow(
                        "Manufacturer",
                        manufacturerField
                )
        );

        form.add(
                createVerticalSpace()
        );


        // ========================================================
        // PROCESSOR / MCU / FPGA
        // ========================================================

        processorLabel =
                createLabel(
                        "Processor / SoC"
                );

        processorField =
                createTextField();

        processorRow =
                createFormRow(
                        processorLabel,
                        processorField
                );

        form.add(
                processorRow
        );

        form.add(
                createVerticalSpace()
        );


        // ========================================================
        // SENSOR PROTOCOLS
        // ========================================================

        protocolPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                15,
                                0
                        )
                );

        protocolPanel.setOpaque(
                false
        );

        uartCheck =
                createCheckBox(
                        "UART"
                );

        i2cCheck =
                createCheckBox(
                        "I2C"
                );

        spiCheck =
                createCheckBox(
                        "SPI"
                );

        protocolPanel.add(
                uartCheck
        );

        protocolPanel.add(
                i2cCheck
        );

        protocolPanel.add(
                spiCheck
        );

        protocolRow =
                createFormRow(
                        "Hardware Protocol",
                        protocolPanel
                );

        form.add(
                protocolRow
        );

        form.add(
                createVerticalSpace()
        );


        // ========================================================
        // ACCESSORY TYPE / OTHER DEVICE TYPE
        // ========================================================

        deviceTypeLabel =
                createLabel(
                        "Device Type"
                );

        deviceTypeField =
                createTextField();

        deviceTypeRow =
                createFormRow(
                        deviceTypeLabel,
                        deviceTypeField
                );

        form.add(
                deviceTypeRow
        );

        form.add(
                createVerticalSpace()
        );


        // ========================================================
        // QUANTITY
        // ========================================================

        quantitySpinner =
                new JSpinner(
                        new SpinnerNumberModel(
                                1,
                                1,
                                99999,
                                1
                        )
                );

        styleSpinner(
                quantitySpinner
        );

        form.add(
                createFormRow(
                        "Quantity",
                        quantitySpinner
                )
        );

        form.add(
                createVerticalSpace()
        );


        // ========================================================
        // STORAGE LOCATION
        // ========================================================
        //
        // This has its own independent row.
        // ========================================================

        locationField =
                createTextField();

        form.add(
                createFormRow(
                        "Storage Location",
                        locationField
                )
        );

        form.add(
                createVerticalSpace()
        );


        // ========================================================
        // DOCUMENTATION STATUS
        // ========================================================
        //
        // This also has its own independent row.
        //
        // Therefore Storage Location and Documentation can no
        // longer overlap.
        // ========================================================

        documentedCheck =
                createCheckBox(
                        "Device Documented"
                );

        form.add(
                createFormRow(
                        "Documentation",
                        documentedCheck
                )
        );


        // ========================================================
        // CATEGORY EVENT
        // ========================================================

        categoryCombo
                .addActionListener(
                        e ->
                                updateFieldsForCategory()
                );


        return form;

    }


    // ============================================================
    // CREATE FORM ROW
    // ============================================================

    private JPanel createFormRow(
            String labelText,
            JComponent component) {

        return createFormRow(
                createLabel(
                        labelText
                ),
                component
        );

    }


    // ============================================================
    // CREATE FORM ROW WITH LABEL
    // ============================================================

    private JPanel createFormRow(
            JLabel label,
            JComponent component) {

        JPanel row =
                new JPanel(
                        new BorderLayout(
                                25,
                                0
                        )
                );

        row.setOpaque(
                false
        );

        row.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        row.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        54
                )
        );

        row.setPreferredSize(
                new Dimension(
                        700,
                        54
                )
        );

        // --------------------------------------------------------
        // LABEL
        // --------------------------------------------------------

        JPanel labelPanel =
                new JPanel(
                        new BorderLayout()
                );

        labelPanel.setOpaque(
                false
        );

        labelPanel.setPreferredSize(
                new Dimension(
                        230,
                        54
                )
        );

        labelPanel.add(
                label,
                BorderLayout.CENTER
        );

        // --------------------------------------------------------
        // FIELD
        // --------------------------------------------------------

        JPanel fieldPanel =
                new JPanel(
                        new BorderLayout()
                );

        fieldPanel.setOpaque(
                false
        );

        fieldPanel.add(
                component,
                BorderLayout.CENTER
        );

        row.add(
                labelPanel,
                BorderLayout.WEST
        );

        row.add(
                fieldPanel,
                BorderLayout.CENTER
        );

        return row;

    }


    // ============================================================
    // VERTICAL SPACE
    // ============================================================

    private Component createVerticalSpace() {

        return Box.createVerticalStrut(
                12
        );

    }


    // ============================================================
    // BUTTON PANEL
    // ============================================================

    private JPanel createButtonPanel() {

        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                15
                        )
                );

        panel.setBackground(
                PANEL
        );

        panel.setBorder(
                new EmptyBorder(
                        5,
                        25,
                        5,
                        25
                )
        );


        // --------------------------------------------------------
        // CANCEL
        // --------------------------------------------------------

        JButton cancelButton =
                new JButton(
                        "CANCEL"
                );

        styleNormalButton(
                cancelButton
        );

        cancelButton
                .addActionListener(
                        e -> dispose()
                );


        // --------------------------------------------------------
        // SAVE
        // --------------------------------------------------------

        TechButton saveButton =
                new TechButton(
                        existingDevice == null
                                ? "SAVE DEVICE"
                                : "UPDATE DEVICE",
                        BLUE,
                        BLUE_HOVER
                );

        saveButton
                .addActionListener(
                        e -> saveDevice()
                );


        panel.add(
                cancelButton
        );

        panel.add(
                saveButton
        );


        return panel;

    }


    // ============================================================
    // UPDATE FIELDS BASED ON CATEGORY
    // ============================================================

    private void updateFieldsForCategory() {

        if (categoryCombo == null) {

            return;

        }

        String category =
                (String)
                        categoryCombo
                                .getSelectedItem();


        // --------------------------------------------------------
        // DEFAULT:
        // Hide optional rows
        // --------------------------------------------------------

        processorRow.setVisible(
                false
        );

        protocolRow.setVisible(
                false
        );

        deviceTypeRow.setVisible(
                false
        );


        // --------------------------------------------------------
        // SBC
        // --------------------------------------------------------

        if ("SBC".equals(
                category
        )) {

            processorLabel.setText(
                    "Processor / SoC"
            );

            processorRow.setVisible(
                    true
            );

        }


        // --------------------------------------------------------
        // MCU
        // --------------------------------------------------------

        else if ("MCU".equals(
                category
        )) {

            processorLabel.setText(
                    "MCU"
            );

            processorRow.setVisible(
                    true
            );

        }


        // --------------------------------------------------------
        // FPGA
        // --------------------------------------------------------

        else if ("FPGA".equals(
                category
        )) {

            processorLabel.setText(
                    "FPGA / SoC"
            );

            processorRow.setVisible(
                    true
            );

        }


        // --------------------------------------------------------
        // SENSOR
        // --------------------------------------------------------

        else if ("Sensors".equals(
                category
        )) {

            protocolRow.setVisible(
                    true
            );

        }


        // --------------------------------------------------------
        // ACCESSORIES
        // --------------------------------------------------------

        else if ("Accessories".equals(
                category
        )) {

            deviceTypeLabel.setText(
                    "Accessory Type"
            );

            deviceTypeRow.setVisible(
                    true
            );

        }


        // --------------------------------------------------------
        // OTHER DEVICES
        // --------------------------------------------------------

        else if ("Other Devices".equals(
                category
        )) {

            deviceTypeLabel.setText(
                    "Device Type"
            );

            deviceTypeRow.setVisible(
                    true
            );

        }


        // --------------------------------------------------------
        // UPDATE LAYOUT
        // --------------------------------------------------------

        revalidate();

        repaint();

    }


    // ============================================================
    // LOAD EXISTING DEVICE
    // ============================================================

    private void loadExistingDevice() {

        nameField.setText(
                safeString(
                        existingDevice
                                .getName()
                )
        );

        categoryCombo.setSelectedItem(
                existingDevice
                        .getCategory()
        );

        manufacturerField.setText(
                safeString(
                        existingDevice
                                .getManufacturer()
                )
        );

        processorField.setText(
                safeString(
                        existingDevice
                                .getProcessor()
                )
        );

        deviceTypeField.setText(
                safeString(
                        existingDevice
                                .getDeviceType()
                )
        );

        quantitySpinner.setValue(
                existingDevice
                        .getQuantity()
        );

        locationField.setText(
                safeString(
                        existingDevice
                                .getLocation()
                )
        );


        // --------------------------------------------------------
        // PROTOCOLS
        // --------------------------------------------------------

        List<String> protocols =
                existingDevice
                        .getProtocols();

        uartCheck.setSelected(
                protocols != null
                        &&
                        protocols.contains(
                                "UART"
                        )
        );

        i2cCheck.setSelected(
                protocols != null
                        &&
                        protocols.contains(
                                "I2C"
                        )
        );

        spiCheck.setSelected(
                protocols != null
                        &&
                        protocols.contains(
                                "SPI"
                        )
        );


        // --------------------------------------------------------
        // DOCUMENTATION
        // --------------------------------------------------------

        documentedCheck.setSelected(
                existingDevice
                        .isDocumented()
        );

    }


    // ============================================================
    // SAVE DEVICE
    // ============================================================

    private void saveDevice() {

        // --------------------------------------------------------
        // DEVICE NAME
        // --------------------------------------------------------

        String name =
                nameField
                        .getText()
                        .trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a device name.",
                    "Missing Device Name",
                    JOptionPane.WARNING_MESSAGE
            );

            nameField.requestFocus();

            return;

        }


        // --------------------------------------------------------
        // CATEGORY
        // --------------------------------------------------------

        String category =
                (String)
                        categoryCombo
                                .getSelectedItem();


        // --------------------------------------------------------
        // MANUFACTURER
        // --------------------------------------------------------

        String manufacturer =
                manufacturerField
                        .getText()
                        .trim();


        // --------------------------------------------------------
        // PROCESSOR
        // --------------------------------------------------------

        String processor =
                "";

        if ("SBC".equals(category)
                ||
                "MCU".equals(category)
                ||
                "FPGA".equals(category)) {

            processor =
                    processorField
                            .getText()
                            .trim();

        }


        // --------------------------------------------------------
        // PROTOCOLS
        // --------------------------------------------------------

        List<String> protocols =
                new ArrayList<>();

        if ("Sensors".equals(
                category
        )) {

            if (uartCheck.isSelected()) {

                protocols.add(
                        "UART"
                );

            }

            if (i2cCheck.isSelected()) {

                protocols.add(
                        "I2C"
                );

            }

            if (spiCheck.isSelected()) {

                protocols.add(
                        "SPI"
                );

            }

        }


        // --------------------------------------------------------
        // DEVICE TYPE
        // --------------------------------------------------------

        String deviceType =
                "";

        if ("Accessories".equals(category)
                ||
                "Other Devices".equals(category)) {

            deviceType =
                    deviceTypeField
                            .getText()
                            .trim();

        }


        // --------------------------------------------------------
        // QUANTITY
        // --------------------------------------------------------

        int quantity =
                (Integer)
                        quantitySpinner
                                .getValue();


        // --------------------------------------------------------
        // STORAGE LOCATION
        // --------------------------------------------------------

        String location =
                locationField
                        .getText()
                        .trim();


        // --------------------------------------------------------
        // DOCUMENTATION STATUS
        // --------------------------------------------------------

        boolean documented =
                documentedCheck
                        .isSelected();


        // ========================================================
        // CREATE NEW DEVICE
        // ========================================================

        if (existingDevice == null) {

            device =
                    new Device(

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


        // ========================================================
        // UPDATE EXISTING DEVICE
        // ========================================================

        else {

            existingDevice.setName(
                    name
            );

            existingDevice.setCategory(
                    category
            );

            existingDevice.setManufacturer(
                    manufacturer
            );

            existingDevice.setProcessor(
                    processor
            );

            existingDevice.setProtocols(
                    protocols
            );

            existingDevice.setDeviceType(
                    deviceType
            );

            existingDevice.setQuantity(
                    quantity
            );

            existingDevice.setLocation(
                    location
            );

            existingDevice.setDocumented(
                    documented
            );

            device =
                    existingDevice;

        }


        // --------------------------------------------------------
        // CLOSE DIALOG
        // --------------------------------------------------------

        dispose();

    }


    // ============================================================
    // GET DEVICE
    // ============================================================

    public Device getDevice() {

        return device;

    }


    // ============================================================
    // CREATE LABEL
    // ============================================================

    private JLabel createLabel(
            String text) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setForeground(
                new Color(
                        147,
                        197,
                        253
                )
        );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        return label;

    }


    // ============================================================
    // CREATE TEXT FIELD
    // ============================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setPreferredSize(
                new Dimension(
                        400,
                        42
                )
        );

        field.setBackground(
                INPUT
        );

        field.setForeground(
                TEXT
        );

        field.setCaretColor(
                TEXT
        );

        field.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        field.setBorder(
                BorderFactory
                        .createCompoundBorder(

                                BorderFactory
                                        .createLineBorder(
                                                BORDER
                                        ),

                                new EmptyBorder(
                                        8,
                                        12,
                                        8,
                                        12
                                )

                        )
        );

        return field;

    }


    // ============================================================
    // CREATE CHECK BOX
    // ============================================================

    private JCheckBox createCheckBox(
            String text) {

        JCheckBox checkBox =
                new JCheckBox(
                        text
                );

        checkBox.setOpaque(
                false
        );

        checkBox.setForeground(
                TEXT
        );

        checkBox.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        checkBox.setFocusPainted(
                false
        );

        return checkBox;

    }


    // ============================================================
    // STYLE COMBO BOX
    // ============================================================

    private void styleComboBox(
            JComboBox<String> comboBox) {

        comboBox.setBackground(
                INPUT
        );

        comboBox.setForeground(
                TEXT
        );

        comboBox.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        comboBox.setPreferredSize(
                new Dimension(
                        400,
                        42
                )
        );

        comboBox.setFocusable(
                false
        );

    }


    // ============================================================
    // STYLE SPINNER
    // ============================================================

    private void styleSpinner(
            JSpinner spinner) {

        spinner.setPreferredSize(
                new Dimension(
                        400,
                        42
                )
        );

        spinner.setBackground(
                INPUT
        );

        JComponent editor =
                spinner.getEditor();

        if (editor
                instanceof JSpinner.DefaultEditor defaultEditor) {

            JTextField field =
                    defaultEditor
                            .getTextField();

            field.setBackground(
                    INPUT
            );

            field.setForeground(
                    TEXT
            );

            field.setCaretColor(
                    TEXT
            );

            field.setFont(
                    new Font(
                            "SansSerif",
                            Font.PLAIN,
                            14
                    )
            );

        }

    }


    // ============================================================
    // STYLE NORMAL BUTTON
    // ============================================================

    private void styleNormalButton(
            JButton button) {

        button.setBackground(
                new Color(
                        51,
                        65,
                        85
                )
        );

        button.setForeground(
                TEXT
        );

        button.setFocusPainted(
                false
        );

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

    }


    // ============================================================
    // SAFE STRING
    // ============================================================

    private String safeString(
            String value) {

        return value == null
                ? ""
                : value;

    }

}