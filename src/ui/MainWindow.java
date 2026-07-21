package ui;

import manager.InventoryManager;
import model.Device;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {

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

    private static final Color GREEN =
            new Color(34, 197, 94);

    private static final Color RED =
            new Color(239, 68, 68);

    private static final Color PURPLE =
            new Color(168, 85, 247);

    private static final Color CYAN =
            new Color(6, 182, 212);


    // ============================================================
    // INVENTORY
    // ============================================================

    private final InventoryManager inventoryManager;


    // ============================================================
    // TABLE
    // ============================================================

    private JTable deviceTable;

    private DefaultTableModel tableModel;


    // ============================================================
    // FILTERS
    // ============================================================

    private JTextField searchField;

    private JComboBox<String> categoryFilter;

    private JComboBox<String> protocolFilter;

    private JComboBox<String> documentationFilter;


    // ============================================================
    // DASHBOARD
    // ============================================================

    private JLabel totalValue;

    private JLabel sbcValue;

    private JLabel mcuValue;

    private JLabel fpgaValue;

    private JLabel sensorValue;


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public MainWindow() {

        // --------------------------------------------------------
        // INVENTORY MANAGER / DATABASE
        // --------------------------------------------------------

        inventoryManager =
                new InventoryManager();


        // --------------------------------------------------------
        // WINDOW
        // --------------------------------------------------------

        setTitle(
                "CircuitShelf - Electronics Inventory"
        );

        setSize(
                1400,
                850
        );

        setMinimumSize(
                new Dimension(
                        1150,
                        700
                )
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(
                null
        );

        getContentPane()
                .setBackground(
                        BACKGROUND
                );

        setLayout(
                new BorderLayout()
        );


        // --------------------------------------------------------
        // HEADER
        // --------------------------------------------------------

        add(
                createHeader(),
                BorderLayout.NORTH
        );


        // --------------------------------------------------------
        // SIDEBAR
        // --------------------------------------------------------

        add(
                createSidebar(),
                BorderLayout.WEST
        );


        // --------------------------------------------------------
        // MAIN CONTENT
        // --------------------------------------------------------

        add(
                createMainContent(),
                BorderLayout.CENTER
        );


        // --------------------------------------------------------
        // LOAD INVENTORY
        // --------------------------------------------------------

        refreshTable();

        updateDashboard();


        // --------------------------------------------------------
        // SHOW WINDOW
        // --------------------------------------------------------

        setVisible(
                true
        );

    }


    // ============================================================
    // HEADER
    // ============================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                PANEL
        );

        header.setBorder(
                new EmptyBorder(
                        18,
                        25,
                        18,
                        25
                )
        );


        // --------------------------------------------------------
        // TITLE
        // --------------------------------------------------------

        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(
                false
        );

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel title =
                new JLabel(
                        "CIRCUITSHELF"
                );

        title.setForeground(
                TEXT
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );


        JLabel subtitle =
                new JLabel(
                        "Electronics Inventory Manager"
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


        titlePanel.add(
                title
        );

        titlePanel.add(
                Box.createVerticalStrut(
                        3
                )
        );

        titlePanel.add(
                subtitle
        );


        // --------------------------------------------------------
        // TOTAL ITEMS
        // --------------------------------------------------------

        JPanel totalPanel =
                new JPanel();

        totalPanel.setOpaque(
                false
        );

        totalPanel.setLayout(
                new BoxLayout(
                        totalPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel totalLabel =
                new JLabel(
                        "TOTAL ITEMS"
                );

        totalLabel.setForeground(
                TEXT_SECONDARY
        );

        totalLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        totalLabel.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );


        totalValue =
                new JLabel(
                        "0"
                );

        totalValue.setForeground(
                BLUE
        );

        totalValue.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );

        totalValue.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );


        totalPanel.add(
                totalLabel
        );

        totalPanel.add(
                totalValue
        );


        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                totalPanel,
                BorderLayout.EAST
        );


        return header;

    }


    // ============================================================
    // SIDEBAR
    // ============================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel();

        sidebar.setPreferredSize(
                new Dimension(
                        190,
                        0
                )
        );

        sidebar.setBackground(
                PANEL
        );

        sidebar.setBorder(
                new EmptyBorder(
                        25,
                        15,
                        20,
                        15
                )
        );

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );


        // --------------------------------------------------------
        // INVENTORY TITLE
        // --------------------------------------------------------

        JLabel title =
                new JLabel(
                        "INVENTORY"
                );

        title.setForeground(
                TEXT_SECONDARY
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        sidebar.add(
                title
        );

        sidebar.add(
                Box.createVerticalStrut(
                        15
                )
        );


        // --------------------------------------------------------
        // CATEGORY BUTTONS
        // --------------------------------------------------------

        sidebar.add(
                createSidebarButton(
                        "All Devices",
                        "All"
                )
        );

        sidebar.add(
                Box.createVerticalStrut(5)
        );


        sidebar.add(
                createSidebarButton(
                        "SBC",
                        "SBC"
                )
        );

        sidebar.add(
                Box.createVerticalStrut(5)
        );


        sidebar.add(
                createSidebarButton(
                        "MCU",
                        "MCU"
                )
        );

        sidebar.add(
                Box.createVerticalStrut(5)
        );


        sidebar.add(
                createSidebarButton(
                        "FPGA",
                        "FPGA"
                )
        );

        sidebar.add(
                Box.createVerticalStrut(5)
        );


        sidebar.add(
                createSidebarButton(
                        "Sensors",
                        "Sensors"
                )
        );

        sidebar.add(
                Box.createVerticalStrut(5)
        );


        sidebar.add(
                createSidebarButton(
                        "Accessories",
                        "Accessories"
                )
        );

        sidebar.add(
                Box.createVerticalStrut(5)
        );


        sidebar.add(
                createSidebarButton(
                        "Other Devices",
                        "Other Devices"
                )
        );


        // --------------------------------------------------------
        // DOCUMENTATION SECTION
        // --------------------------------------------------------

        sidebar.add(
                Box.createVerticalStrut(
                        25
                )
        );


        JLabel documentationTitle =
                new JLabel(
                        "DOCUMENTATION"
                );

        documentationTitle.setForeground(
                TEXT_SECONDARY
        );

        documentationTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        documentationTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        sidebar.add(
                documentationTitle
        );

        sidebar.add(
                Box.createVerticalStrut(
                        10
                )
        );


        // --------------------------------------------------------
        // DOCUMENTED
        // --------------------------------------------------------

        JButton documentedButton =
                createSimpleSidebarButton(
                        "Documented"
                );

        documentedButton.addActionListener(
                e -> {

                    documentationFilter
                            .setSelectedItem(
                                    "Documented"
                            );

                }
        );


        sidebar.add(
                documentedButton
        );

        sidebar.add(
                Box.createVerticalStrut(5)
        );


        // --------------------------------------------------------
        // NOT DOCUMENTED
        // --------------------------------------------------------

        JButton undocumentedButton =
                createSimpleSidebarButton(
                        "Not Documented"
                );

        undocumentedButton.addActionListener(
                e -> {

                    documentationFilter
                            .setSelectedItem(
                                    "Not Documented"
                            );

                }
        );


        sidebar.add(
                undocumentedButton
        );


        return sidebar;

    }


    // ============================================================
    // SIDEBAR CATEGORY BUTTON
    // ============================================================

    private JButton createSidebarButton(
            String text,
            String category) {

        JButton button =
                createSimpleSidebarButton(
                        text
                );


        button.addActionListener(
                e -> {

                    categoryFilter
                            .setSelectedItem(
                                    category
                            );

                }
        );


        return button;

    }


    // ============================================================
    // SIMPLE SIDEBAR BUTTON
    // ============================================================

    private JButton createSimpleSidebarButton(
            String text) {

        JButton button =
                new JButton(
                        text
                );


        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setBackground(
                PANEL
        );

        button.setForeground(
                TEXT
        );

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                new EmptyBorder(
                        8,
                        10,
                        8,
                        10
                )
        );

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );


        return button;

    }


    // ============================================================
    // MAIN CONTENT
    // ============================================================

    private JPanel createMainContent() {

        JPanel main =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );


        main.setBackground(
                BACKGROUND
        );

        main.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );


        // --------------------------------------------------------
        // TOP SECTION
        // --------------------------------------------------------

        JPanel top =
                new JPanel();

        top.setOpaque(
                false
        );

        top.setLayout(
                new BoxLayout(
                        top,
                        BoxLayout.Y_AXIS
                )
        );


        // --------------------------------------------------------
        // DASHBOARD
        // --------------------------------------------------------

        top.add(
                createDashboard()
        );


        top.add(
                Box.createVerticalStrut(
                        15
                )
        );


        // --------------------------------------------------------
        // FILTERS
        // --------------------------------------------------------

        top.add(
                createFilterPanel()
        );


        main.add(
                top,
                BorderLayout.NORTH
        );


        // --------------------------------------------------------
        // INVENTORY TABLE
        // --------------------------------------------------------

        main.add(
                createTablePanel(),
                BorderLayout.CENTER
        );


        // --------------------------------------------------------
        // ACTION BUTTONS
        // --------------------------------------------------------

        main.add(
                createActionPanel(),
                BorderLayout.SOUTH
        );


        return main;

    }


    // ============================================================
    // DASHBOARD
    //
    // ONLY FOUR PRIMARY HARDWARE CATEGORIES ARE DISPLAYED:
    //
    // SBC
    // MCU
    // FPGA
    // SENSORS
    //
    // Accessories, Other Devices and Documentation remain
    // available through filters/sidebar/table.
    // ============================================================

    private JPanel createDashboard() {

        JPanel dashboard =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                18,
                                0
                        )
                );


        dashboard.setOpaque(
                false
        );


        // --------------------------------------------------------
        // VALUES
        // --------------------------------------------------------

        sbcValue =
                new JLabel(
                        "0"
                );

        mcuValue =
                new JLabel(
                        "0"
                );

        fpgaValue =
                new JLabel(
                        "0"
                );

        sensorValue =
                new JLabel(
                        "0"
                );


        // --------------------------------------------------------
        // SBC
        // --------------------------------------------------------

        dashboard.add(
                createDashboardCard(
                        "SBC",
                        sbcValue,
                        BLUE
                )
        );


        // --------------------------------------------------------
        // MCU
        // --------------------------------------------------------

        dashboard.add(
                createDashboardCard(
                        "MCU",
                        mcuValue,
                        GREEN
                )
        );


        // --------------------------------------------------------
        // FPGA
        // --------------------------------------------------------

        dashboard.add(
                createDashboardCard(
                        "FPGA",
                        fpgaValue,
                        PURPLE
                )
        );


        // --------------------------------------------------------
        // SENSORS
        // --------------------------------------------------------

        dashboard.add(
                createDashboardCard(
                        "SENSORS",
                        sensorValue,
                        CYAN
                )
        );


        return dashboard;

    }


    // ============================================================
    // DASHBOARD CARD
    // ============================================================

    private JPanel createDashboardCard(
            String title,
            JLabel value,
            Color accentColor) {

        JPanel card =
                new JPanel();


        card.setBackground(
                PANEL
        );


        card.setBorder(
                BorderFactory
                        .createCompoundBorder(

                                BorderFactory
                                        .createMatteBorder(
                                                0,
                                                4,
                                                0,
                                                0,
                                                accentColor
                                        ),

                                new EmptyBorder(
                                        14,
                                        16,
                                        14,
                                        16
                                )

                        )
        );


        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setForeground(
                TEXT_SECONDARY
        );

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );


        value.setForeground(
                TEXT
        );

        value.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );


        card.add(
                titleLabel
        );

        card.add(
                Box.createVerticalStrut(
                        5
                )
        );

        card.add(
                value
        );


        return card;

    }


    // ============================================================
    // FILTER PANEL
    // ============================================================

    private JPanel createFilterPanel() {

        JPanel filterPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                12,
                                5
                        )
                );


        filterPanel.setBackground(
                PANEL
        );

        filterPanel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        // --------------------------------------------------------
        // SEARCH
        // --------------------------------------------------------

        JLabel searchLabel =
                createFilterLabel(
                        "Search"
                );


        searchField =
                new JTextField(
                        20
                );


        styleTextField(
                searchField
        );


        // --------------------------------------------------------
        // CATEGORY
        // --------------------------------------------------------

        JLabel categoryLabel =
                createFilterLabel(
                        "Category"
                );


        categoryFilter =
                new JComboBox<>(
                        new String[]{
                                "All",
                                "SBC",
                                "MCU",
                                "FPGA",
                                "Sensors",
                                "Accessories",
                                "Other Devices"
                        }
                );


        styleComboBox(
                categoryFilter
        );


        // --------------------------------------------------------
        // PROTOCOL
        // --------------------------------------------------------

        JLabel protocolLabel =
                createFilterLabel(
                        "Protocol"
                );


        protocolFilter =
                new JComboBox<>(
                        new String[]{
                                "All",
                                "UART",
                                "I2C",
                                "SPI"
                        }
                );


        styleComboBox(
                protocolFilter
        );


        // --------------------------------------------------------
        // DOCUMENTATION
        // --------------------------------------------------------

        JLabel documentationLabel =
                createFilterLabel(
                        "Documentation"
                );


        documentationFilter =
                new JComboBox<>(
                        new String[]{
                                "All",
                                "Documented",
                                "Not Documented"
                        }
                );


        styleComboBox(
                documentationFilter
        );


        // --------------------------------------------------------
        // ADD FILTERS
        // --------------------------------------------------------

        filterPanel.add(
                searchLabel
        );

        filterPanel.add(
                searchField
        );


        filterPanel.add(
                categoryLabel
        );

        filterPanel.add(
                categoryFilter
        );


        filterPanel.add(
                protocolLabel
        );

        filterPanel.add(
                protocolFilter
        );


        filterPanel.add(
                documentationLabel
        );

        filterPanel.add(
                documentationFilter
        );


        // --------------------------------------------------------
        // SEARCH EVENT
        // --------------------------------------------------------

        searchField
                .getDocument()
                .addDocumentListener(

                        new DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e) {

                                refreshTable();

                            }


                            @Override
                            public void removeUpdate(
                                    DocumentEvent e) {

                                refreshTable();

                            }


                            @Override
                            public void changedUpdate(
                                    DocumentEvent e) {

                                refreshTable();

                            }

                        }

                );


        // --------------------------------------------------------
        // CATEGORY EVENT
        // --------------------------------------------------------

        categoryFilter
                .addActionListener(
                        e -> {

                            updateProtocolFilterState();

                            refreshTable();

                        }
                );


        // --------------------------------------------------------
        // PROTOCOL EVENT
        // --------------------------------------------------------

        protocolFilter
                .addActionListener(
                        e -> refreshTable()
                );


        // --------------------------------------------------------
        // DOCUMENTATION EVENT
        // --------------------------------------------------------

        documentationFilter
                .addActionListener(
                        e -> refreshTable()
                );


        updateProtocolFilterState();


        return filterPanel;

    }


    // ============================================================
    // FILTER LABEL
    // ============================================================

    private JLabel createFilterLabel(
            String text) {

        JLabel label =
                new JLabel(
                        text
                );


        label.setForeground(
                TEXT_SECONDARY
        );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );


        return label;

    }


    // ============================================================
    // TABLE
    // ============================================================

    private JScrollPane createTablePanel() {

        String[] columns = {

                "ID",
                "Device",
                "Category",
                "Manufacturer",
                "Processor / SoC",
                "Protocol",
                "Type",
                "Qty",
                "Location",
                "Documented"

        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;

                    }

                };


        deviceTable =
                new JTable(
                        tableModel
                );


        deviceTable.setBackground(
                PANEL
        );

        deviceTable.setForeground(
                TEXT
        );

        deviceTable.setSelectionBackground(
                BLUE
        );

        deviceTable.setSelectionForeground(
                Color.WHITE
        );

        deviceTable.setGridColor(
                INPUT
        );

        deviceTable.setRowHeight(
                36
        );

        deviceTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        deviceTable.setFillsViewportHeight(
                true
        );

        deviceTable.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        deviceTable.setAutoCreateRowSorter(
                true
        );


        // --------------------------------------------------------
        // TABLE HEADER
        // --------------------------------------------------------

        JTableHeader header =
                deviceTable
                        .getTableHeader();


        header.setBackground(
                INPUT
        );

        header.setForeground(
                TEXT
        );

        header.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        header.setPreferredSize(
                new Dimension(
                        header
                                .getPreferredSize()
                                .width,
                        38
                )
        );


        // --------------------------------------------------------
        // CENTER RENDERER
        // --------------------------------------------------------

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();


        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        // ID
        deviceTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );


        // Quantity
        deviceTable
                .getColumnModel()
                .getColumn(7)
                .setCellRenderer(
                        centerRenderer
                );


        // --------------------------------------------------------
        // DOCUMENTATION RENDERER
        // --------------------------------------------------------

        deviceTable
                .getColumnModel()
                .getColumn(9)
                .setCellRenderer(
                        new DocumentationRenderer()
                );


        // --------------------------------------------------------
        // COLUMN WIDTHS
        // --------------------------------------------------------

        deviceTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        45
                );


        deviceTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        180
                );


        deviceTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        100
                );


        deviceTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        150
                );


        deviceTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(
                        140
                );


        deviceTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(
                        100
                );


        deviceTable
                .getColumnModel()
                .getColumn(6)
                .setPreferredWidth(
                        120
                );


        deviceTable
                .getColumnModel()
                .getColumn(7)
                .setPreferredWidth(
                        55
                );


        deviceTable
                .getColumnModel()
                .getColumn(8)
                .setPreferredWidth(
                        130
                );


        deviceTable
                .getColumnModel()
                .getColumn(9)
                .setPreferredWidth(
                        100
                );


        // --------------------------------------------------------
        // DOUBLE CLICK TO EDIT
        // --------------------------------------------------------

        deviceTable.addMouseListener(

                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        if (e.getClickCount() == 2) {

                            editSelectedDevice();

                        }

                    }

                }

        );


        // --------------------------------------------------------
        // SCROLL PANE
        // --------------------------------------------------------

        JScrollPane scrollPane =
                new JScrollPane(
                        deviceTable
                );


        scrollPane.setBorder(
                BorderFactory
                        .createEmptyBorder()
        );


        scrollPane
                .getViewport()
                .setBackground(
                        PANEL
                );


        return scrollPane;

    }


    // ============================================================
    // DOCUMENTATION RENDERER
    // ============================================================

    private class DocumentationRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {


            JLabel label =
                    (JLabel)
                            super
                                    .getTableCellRendererComponent(
                                            table,
                                            value,
                                            isSelected,
                                            hasFocus,
                                            row,
                                            column
                                    );


            label.setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            label.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            16
                    )
            );


            if (!isSelected) {

                if ("✓".equals(
                        String.valueOf(
                                value
                        )
                )) {

                    label.setForeground(
                            GREEN
                    );

                }

                else {

                    label.setForeground(
                            RED
                    );

                }


                label.setBackground(
                        PANEL
                );

            }


            return label;

        }

    }


    // ============================================================
    // ACTION PANEL
    // ============================================================

    private JPanel createActionPanel() {

        JPanel actionPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                5
                        )
                );


        actionPanel.setOpaque(
                false
        );


        // --------------------------------------------------------
        // ADD
        // --------------------------------------------------------

        TechButton addButton =
                new TechButton(
                        "ADD DEVICE",
                        BLUE,
                        BLUE_HOVER
                );


        // --------------------------------------------------------
        // EDIT
        // --------------------------------------------------------

        TechButton editButton =
                new TechButton(
                        "EDIT",
                        new Color(
                                71,
                                85,
                                105
                        ),
                        new Color(
                                100,
                                116,
                                139
                        )
                );


        // --------------------------------------------------------
        // DELETE
        // --------------------------------------------------------

        TechButton deleteButton =
                new TechButton(
                        "DELETE",
                        new Color(
                                185,
                                28,
                                28
                        ),
                        RED
                );


        addButton.addActionListener(
                e -> addDevice()
        );


        editButton.addActionListener(
                e -> editSelectedDevice()
        );


        deleteButton.addActionListener(
                e -> deleteSelectedDevice()
        );


        actionPanel.add(
                addButton
        );

        actionPanel.add(
                editButton
        );

        actionPanel.add(
                deleteButton
        );


        return actionPanel;

    }


    // ============================================================
    // ADD DEVICE
    // ============================================================

    private void addDevice() {

        DeviceDialog dialog =
                new DeviceDialog(
                        this,
                        null
                );


        dialog.setVisible(
                true
        );


        Device newDevice =
                dialog.getDevice();


        if (newDevice != null) {

            inventoryManager
                    .addDevice(
                            newDevice
                    );


            refreshTable();

            updateDashboard();

        }

    }


    // ============================================================
    // EDIT DEVICE
    // ============================================================

    private void editSelectedDevice() {

        Device selectedDevice =
                getSelectedDevice();


        if (selectedDevice == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a device to edit.",
                    "No Device Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;

        }


        DeviceDialog dialog =
                new DeviceDialog(
                        this,
                        selectedDevice
                );


        dialog.setVisible(
                true
        );


        Device updatedDevice =
                dialog.getDevice();


        if (updatedDevice != null) {

            inventoryManager
                    .updateDevice(
                            updatedDevice
                    );


            refreshTable();

            updateDashboard();

        }

    }


    // ============================================================
    // DELETE DEVICE
    // ============================================================

    private void deleteSelectedDevice() {

        Device selectedDevice =
                getSelectedDevice();


        if (selectedDevice == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a device to delete.",
                    "No Device Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;

        }


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete \"" +
                                selectedDevice.getName() +
                                "\" from your inventory?",
                        "Delete Device",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (result ==
                JOptionPane.YES_OPTION) {

            inventoryManager
                    .deleteDevice(
                            selectedDevice
                    );


            refreshTable();

            updateDashboard();

        }

    }


    // ============================================================
    // GET SELECTED DEVICE
    // ============================================================

    private Device getSelectedDevice() {

        int selectedViewRow =
                deviceTable
                        .getSelectedRow();


        if (selectedViewRow == -1) {

            return null;

        }


        // --------------------------------------------------------
        // JTable can be sorted.
        // Convert displayed row to model row.
        // --------------------------------------------------------

        int selectedModelRow =
                deviceTable
                        .convertRowIndexToModel(
                                selectedViewRow
                        );


        int id =
                Integer.parseInt(

                        tableModel
                                .getValueAt(
                                        selectedModelRow,
                                        0
                                )
                                .toString()

                );


        for (Device device :
                inventoryManager
                        .getDevices()) {


            if (device.getId()
                    == id) {

                return device;

            }

        }


        return null;

    }


    // ============================================================
    // REFRESH TABLE
    // ============================================================

    private void refreshTable() {

        if (tableModel == null) {

            return;

        }


        tableModel.setRowCount(
                0
        );


        // --------------------------------------------------------
        // SEARCH
        // --------------------------------------------------------

        String search =
                searchField == null
                        ? ""
                        : searchField
                                .getText()
                                .trim()
                                .toLowerCase();


        // --------------------------------------------------------
        // CATEGORY
        // --------------------------------------------------------

        String selectedCategory =
                categoryFilter == null
                        ? "All"
                        : (String)
                                categoryFilter
                                        .getSelectedItem();


        // --------------------------------------------------------
        // PROTOCOL
        // --------------------------------------------------------

        String selectedProtocol =
                protocolFilter == null
                        ? "All"
                        : (String)
                                protocolFilter
                                        .getSelectedItem();


        // --------------------------------------------------------
        // DOCUMENTATION
        // --------------------------------------------------------

        String selectedDocumentation =
                documentationFilter == null
                        ? "All"
                        : (String)
                                documentationFilter
                                        .getSelectedItem();


        List<Device> devices =
                inventoryManager
                        .getDevices();


        for (Device device :
                devices) {


            // ----------------------------------------------------
            // SEARCH MATCH
            // ----------------------------------------------------

            boolean matchesSearch =
                    matchesSearch(
                            device,
                            search
                    );


            // ----------------------------------------------------
            // CATEGORY MATCH
            // ----------------------------------------------------

            boolean matchesCategory =
                    "All".equals(
                            selectedCategory
                    )
                            ||
                    device
                            .getCategory()
                            .equalsIgnoreCase(
                                    selectedCategory
                            );


            // ----------------------------------------------------
            // PROTOCOL MATCH
            // ----------------------------------------------------

            boolean matchesProtocol =
                    true;


            if (!"All".equals(
                    selectedProtocol
            )) {

                matchesProtocol =
                        device
                                .getProtocols()
                                .contains(
                                        selectedProtocol
                                );

            }


            // ----------------------------------------------------
            // DOCUMENTATION MATCH
            // ----------------------------------------------------

            boolean matchesDocumentation =
                    true;


            if ("Documented".equals(
                    selectedDocumentation
            )) {

                matchesDocumentation =
                        device.isDocumented();

            }

            else if (
                    "Not Documented".equals(
                            selectedDocumentation
                    )
            ) {

                matchesDocumentation =
                        !device.isDocumented();

            }


            // ----------------------------------------------------
            // ADD MATCHING DEVICE
            // ----------------------------------------------------

            if (matchesSearch
                    &&
                    matchesCategory
                    &&
                    matchesProtocol
                    &&
                    matchesDocumentation) {


                tableModel.addRow(

                        new Object[]{

                                device.getId(),

                                device.getName(),

                                device.getCategory(),

                                safeString(
                                        device.getManufacturer()
                                ),

                                safeString(
                                        device.getProcessor()
                                ),

                                device.getProtocolsAsString(),

                                safeString(
                                        device.getDeviceType()
                                ),

                                device.getQuantity(),

                                safeString(
                                        device.getLocation()
                                ),

                                device.isDocumented()
                                        ? "✓"
                                        : "✗"

                        }

                );

            }

        }

    }


    // ============================================================
    // SEARCH MATCH
    // ============================================================

    private boolean matchesSearch(
            Device device,
            String search) {

        if (search == null
                ||
                search.isBlank()) {

            return true;

        }


        String searchableText =

                safeString(
                        device.getName()
                )

                        + " "

                        + safeString(
                        device.getCategory()
                )

                        + " "

                        + safeString(
                        device.getManufacturer()
                )

                        + " "

                        + safeString(
                        device.getProcessor()
                )

                        + " "

                        + safeString(
                        device.getProtocolsAsString()
                )

                        + " "

                        + safeString(
                        device.getDeviceType()
                )

                        + " "

                        + safeString(
                        device.getLocation()
                );


        return searchableText
                .toLowerCase()
                .contains(
                        search
                );

    }


    // ============================================================
    // UPDATE DASHBOARD
    // ============================================================
    //
    // Dashboard only shows:
    //
    // SBC
    // MCU
    // FPGA
    // Sensors
    //
    // TOTAL ITEMS still includes every category.
    // ============================================================

    private void updateDashboard() {

        int total = 0;

        int sbc = 0;

        int mcu = 0;

        int fpga = 0;

        int sensors = 0;


        for (Device device :
                inventoryManager
                        .getDevices()) {


            // ----------------------------------------------------
            // TOTAL INVENTORY
            //
            // Includes:
            //
            // SBC
            // MCU
            // FPGA
            // Sensors
            // Accessories
            // Other Devices
            // ----------------------------------------------------

            total +=
                    device.getQuantity();


            // ----------------------------------------------------
            // DASHBOARD CATEGORIES
            // ----------------------------------------------------

            switch (
                    device.getCategory()
            ) {

                case "SBC" ->

                        sbc +=
                                device.getQuantity();


                case "MCU" ->

                        mcu +=
                                device.getQuantity();


                case "FPGA" ->

                        fpga +=
                                device.getQuantity();


                case "Sensors" ->

                        sensors +=
                                device.getQuantity();

            }

        }


        // --------------------------------------------------------
        // TOTAL
        // --------------------------------------------------------

        totalValue.setText(
                String.valueOf(
                        total
                )
        );


        // --------------------------------------------------------
        // SBC
        // --------------------------------------------------------

        sbcValue.setText(
                String.valueOf(
                        sbc
                )
        );


        // --------------------------------------------------------
        // MCU
        // --------------------------------------------------------

        mcuValue.setText(
                String.valueOf(
                        mcu
                )
        );


        // --------------------------------------------------------
        // FPGA
        // --------------------------------------------------------

        fpgaValue.setText(
                String.valueOf(
                        fpga
                )
        );


        // --------------------------------------------------------
        // SENSORS
        // --------------------------------------------------------

        sensorValue.setText(
                String.valueOf(
                        sensors
                )
        );

    }


    // ============================================================
    // PROTOCOL FILTER STATE
    // ============================================================

    private void updateProtocolFilterState() {

        if (categoryFilter == null
                ||
                protocolFilter == null) {

            return;

        }


        String category =
                (String)
                        categoryFilter
                                .getSelectedItem();


        boolean sensorSelected =
                "Sensors".equals(
                        category
                );


        // --------------------------------------------------------
        // Protocol filter is only useful for Sensors
        // --------------------------------------------------------

        protocolFilter.setEnabled(
                sensorSelected
        );


        // --------------------------------------------------------
        // Reset protocol when leaving Sensors
        // --------------------------------------------------------

        if (!sensorSelected) {

            protocolFilter
                    .setSelectedItem(
                            "All"
                    );

        }

    }


    // ============================================================
    // STYLE TEXT FIELD
    // ============================================================

    private void styleTextField(
            JTextField field) {

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
                        13
                )
        );


        field.setBorder(
                BorderFactory
                        .createCompoundBorder(

                                BorderFactory
                                        .createLineBorder(
                                                new Color(
                                                        71,
                                                        85,
                                                        105
                                                )
                                        ),

                                new EmptyBorder(
                                        7,
                                        10,
                                        7,
                                        10
                                )

                        )
        );

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
                        12
                )
        );

        comboBox.setFocusable(
                false
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