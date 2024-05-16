import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEditor{

    private JFrame mainFrame;

    private JMenuBar menuBar;

    private JMenu fileMenu , editMenu;

    private JMenuItem newFile , saveFile , openFile , cutOp , copyOp , pasteOp , selectAllOp , closeOp;

    private JTextArea textArea;

    private JScrollPane scrollPane;

    private JPanel textPanel;

    private JSpinner fontSizeSpinner;

    private JLabel fontSizeLabel;

    private JButton fontColorButton;

    private JComboBox fontComboBox;

    //MainIconAttribution
    //<a href="https://www.flaticon.com/free-icons/content-writing" title="content writing icons">Content writing icons created by iconixar - Flaticon</a>

    TextEditor()
    {


        newFile = new JMenuItem("New Window");
        newFile.addActionListener(e -> newWindowMethod()); //using lambda expression for function call(working bcz actionlistener have only one method actionperformed

        saveFile = new JMenuItem("Save File");
        saveFile.addActionListener(e -> saveFileMethod());

        openFile = new JMenuItem("Open File");
        openFile.addActionListener(e -> openFileMethod());

        cutOp = new JMenuItem("Cut");
        cutOp.addActionListener(e -> cutMethod());

        copyOp = new JMenuItem("Copy");
        copyOp.addActionListener(e -> copyMethod());

        pasteOp = new JMenuItem("Paste");
        pasteOp.addActionListener(e -> pasteMethod());

        selectAllOp = new JMenuItem("Select All");
        selectAllOp.addActionListener(e -> selectAllMethod());

        closeOp = new JMenuItem("Close");
        closeOp.addActionListener(e -> closeMethod());

        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");


        fileMenu.add(newFile);
        fileMenu.add(saveFile);
        fileMenu.add(openFile);

        editMenu.add(cutOp);
        editMenu.add(copyOp);
        editMenu.add(pasteOp);
        editMenu.add(selectAllOp);
        editMenu.add(closeOp);

        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);


        //frame->panel->scrollpane(applying on textarea) and panel->textarea
        textPanel = new JPanel();
        textPanel.setBorder(new EmptyBorder(2 , 2 , 2 , 2));
        textPanel.setLayout(new BorderLayout(10 , 10));

        ///Assinging values to fontsizespinner
        SpinnerModel sm = new SpinnerNumberModel(24 , 0 , 100 , 1);
        fontSizeSpinner = new JSpinner(sm);
        fontSizeSpinner.setPreferredSize(new Dimension(35, 30));
        fontSizeSpinner.addChangeListener(e -> changeFontSize());

        fontSizeLabel = new JLabel("Font Size");

        //font color button
        fontColorButton = new JButton("Font Color");
        fontColorButton.setSize(new Dimension(35, 30));
        fontColorButton.setFocusPainted(false);
        fontColorButton.addActionListener(e -> changeFontColor());


        //filling font combo box
        String[] fontValues = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox(fontValues);
        fontComboBox.setSelectedItem("Cambria");
        fontComboBox.addActionListener(e -> changeFont());


        //creating a top panel for formatting text options
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER , 10 , 10));
        JLabel emptyLabel1 = new JLabel("          "); //adding empty labels for better spacing between components
        JLabel emptyLabel2 = new JLabel("          ");
        topPanel.add(fontSizeLabel);
        topPanel.add(fontSizeSpinner);
        topPanel.add(emptyLabel1);
        topPanel.add(fontColorButton);
        topPanel.add(emptyLabel2);
        topPanel.add(fontComboBox);


        textArea = new JTextArea();
        textArea.setFont(new Font("Cambria" , Font.PLAIN , 24));
        textPanel.add(textArea , BorderLayout.CENTER);
        textPanel.add(topPanel , BorderLayout.NORTH);

        //vertical scrollbarpolicy should be before horizontal in constructor
        scrollPane = new JScrollPane(textArea ,  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textPanel.add(scrollPane);


        mainFrame = new JFrame();
        mainFrame.setTitle("Basic Text Editor");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setIconImage((new ImageIcon(getClass().getResource("TextEditorIcon.png"))).getImage());
        mainFrame.setSize(1000 , 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.add(textPanel);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setLayout(null);
    }

    public void changeFont()
    {
        textArea.setFont(new Font((String)fontComboBox.getSelectedItem() , Font.PLAIN , textArea.getFont().getSize()));
    }
    public void changeFontSize()
    {
        textArea.setFont(new Font(textArea.getFont().getFamily() , Font.PLAIN , (int)fontSizeSpinner.getValue()));
    }

    public void changeFontColor()
    {
        JColorChooser colorChooser = new JColorChooser();
        Color color = colorChooser.showDialog(null , "Choose a Color" , Color.white);
        textArea.setForeground(color);
    }
    public void newWindowMethod()
    {
        TextEditor newTextEditor = new TextEditor();
    }

    public void openFileMethod()
    {
        JFileChooser fileChooser = new JFileChooser("C:");
        int choice = fileChooser.showOpenDialog(null);
        if(choice == JFileChooser.APPROVE_OPTION)
        {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try{
                BufferedReader bReader = new BufferedReader(new FileReader(filePath));
                StringBuilder newFileData = new StringBuilder();
                String str = "";
                while((str = bReader.readLine()) != null)
                {
                    newFileData.append(str);
                    newFileData.append("\n");
                }

                textArea.setText(newFileData.toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    public void saveFileMethod()
    {
        JFileChooser fileChooser = new JFileChooser("C:");
        int choice = fileChooser.showSaveDialog(null);
        if(choice == JFileChooser.APPROVE_OPTION)
        {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath() + ".txt");

            try{
                BufferedWriter bWriter= new BufferedWriter(new FileWriter(file));
                //copy contents of textarea to file
                textArea.write(bWriter);

                bWriter.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void cutMethod()
    {
        textArea.cut();
    }

    public void copyMethod()
    {
        textArea.copy();
    }

    public void pasteMethod()
    {
        textArea.paste();
    }

    public void selectAllMethod()
    {
        textArea.selectAll();
    }

    public void closeMethod()
    {
        System.exit(0);
    }


    public static void main(String[] args) {
        TextEditor texteditor = new TextEditor();
    }
}