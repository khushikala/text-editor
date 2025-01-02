import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class TextEditor extends JFrame implements ActionListener {

    // Declare GUI components
    JTextArea textArea; // For text editing
    JScrollPane scrollPane; // For adding scrolling functionality
    JLabel fontLabel; // Label for font selector
    JSpinner fontSizeSpinner; // Spinner for font size
    JComboBox<String> fontBox; // Dropdown for font families

    // Declare menu components
    JMenuBar menuBar; // Menu bar
    JMenu fileMenu; // "File" menu
    JMenuItem openItem, saveItem, exitItem; // Menu items for File menu
    Container c; // Container for layout management

    TextEditor() {
        c = getContentPane(); // Get content pane for adding components
        ImageIcon icon = new ImageIcon("logo.png"); // Icon for the editor window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application on exit
        setTitle("Text Editor"); // Set window title
        setIconImage(icon.getImage()); // Set icon
        setSize(900, 600); // Set window dimensions
        c.setLayout(new FlowLayout()); // Use FlowLayout for layout management
        setLocationRelativeTo(null); // Center the window on the screen

        // Initialize text area with default properties
        textArea = new JTextArea();
        textArea.setText("Made By Khushi"); // Default text
        textArea.setLineWrap(true); // Enable line wrapping
        textArea.setWrapStyleWord(true); // Wrap at word boundaries
        textArea.setFont(new Font("Poppins", Font.PLAIN, 24)); // Set default font
        textArea.setBackground(new Color(166, 166, 166)); // Set background color
        textArea.setForeground(new Color(255, 255, 255)); // Set text color
        textArea.setCaretColor(new Color(240, 56, 0)); // Set caret (cursor) color
        c.add(textArea); // Add text area to the container

        // Add scroll pane to the text area
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(880, 525)); // Set dimensions
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); // Show vertical scrollbar when needed

        // Label for font selection
        fontLabel = new JLabel("Font");

        // Spinner for font size selection
        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25)); // Set size of spinner
        fontSizeSpinner.setValue(24); // Default font size
        fontSizeSpinner.addChangeListener(
                e -> textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()))); // Update font size dynamically

        // Dropdown for selecting font families
        String[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); // Get available fonts
        fontBox = new JComboBox<>(fontList); // Populate combo box with fonts
        fontBox.addActionListener(this); // Add action listener for font change
        fontBox.setSelectedItem("Poppins"); // Set default selected font

        // Initialize menu components
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File"); // Add "File" menu
        openItem = new JMenuItem("Open"); // Menu item for opening files
        saveItem = new JMenuItem("Save"); // Menu item for saving files
        exitItem = new JMenuItem("Exit"); // Menu item for exiting the application

        // Add action listeners for menu items
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        // Add menu items to the "File" menu
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        // Add the "File" menu to the menu bar
        menuBar.add(fileMenu);

        // Add components to the container
        this.setJMenuBar(menuBar); // Set the menu bar
        c.add(fontLabel);
        c.add(fontSizeSpinner);
        c.add(fontBox);
        c.add(scrollPane);

        setVisible(true); // Make the window visible
    }

    // Handle actions like menu item clicks or font selection
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fontBox) {
            textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize())); // Change font family
        }

        if (e.getSource() == openItem) {
            JFileChooser fileChooser = new JFileChooser(); // File chooser for opening files
            fileChooser.setCurrentDirectory(new File(".")); // Default directory
            FileNameExtensionFilter nameExtensionFilter = new FileNameExtensionFilter("Text Files", "txt"); // Filter for .txt files
            fileChooser.setFileFilter(nameExtensionFilter);

            int response = fileChooser.showOpenDialog(null); // Show open file dialog
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath()); // Get selected file
                try (Scanner fileIn = new Scanner(file)) { // Open file for reading
                    textArea.setText(""); // Clear text area
                    while (fileIn.hasNextLine()) {
                        textArea.append(fileIn.nextLine() + "\n"); // Append file content to text area
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace(); // Print stack trace for debugging
                }
            }
        }

        if (e.getSource() == saveItem) {
            JFileChooser fileChooser = new JFileChooser(); // File chooser for saving files
            fileChooser.setCurrentDirectory(new File(".")); // Default directory
            int response = fileChooser.showSaveDialog(null); // Show save file dialog

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath()); // Get selected file path
                try (PrintWriter fileOut = new PrintWriter(file)) { // Open file for writing
                    fileOut.println(textArea.getText()); // Write text area content to file
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace(); // Print stack trace for debugging
                }
            }
        }

        if (e.getSource() == exitItem) {
            System.exit(0); // Exit the application
        }
    }
}
