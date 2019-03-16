package MineDict;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Sinan
 */
public class Gui extends JFrame {

    private FileController File = new FileController();
    private JTextField InputSearch;
    private JPanel Panel;
    private JButton Search;
    private DefaultListModel<String> Model;
    private JScrollPane ScrollPane;
    private JList<String> List;
    private JButton AddNewWordButton;
    private JButton DeleteWordButton;
    private JButton BackButton;

    private ArrayList<String> Display;
    private String HolderEng;
    private String HolderTr;

    public void CreateMenu() {

        File.Dict.Output();

        setTitle("Vocable");
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                File.OpenFileToSave();
                File.AddRecords();
                File.CloseFileToSave();
                dispose();
                System.exit(0);
            }
        });
        setSize(600, 400);

        Panel = new JPanel();
        Panel.setLayout(null);
        Search = new JButton();
        Search.addActionListener(new ButtonListener(Search));
        Search.setText("Search");
        Search.setBounds(500, 10, 80, 30);

        //Seacrh Field
        InputSearch = new JTextField();
        InputSearch.setBounds(320, 10, 180, 30);

        //Scroll Pane List
        Model = new DefaultListModel<>();
        ScrollPane = new JScrollPane();
//      List = new JList<>(Model);
        Display = File.Dict.FillScroll();
        List = new JList(Display.toArray());
        List.setName("Eng");
        List.addMouseListener(ClickListener);
        if (Display.size() > 0) {
            List.setSelectedIndex(0);
        }
        ScrollPane.setViewportView(List);
        ScrollPane.setBounds(40, 50, 500, 250);

        AddNewWordButton = new JButton("Add New Word >>");
        AddNewWordButton.setBounds(80, 300, 150, 30);
        AddNewWordButton.addActionListener(new ButtonListener(AddNewWordButton));

        BackButton = new JButton(" << ");
        BackButton.setBounds(390, 300, 150, 30);
        BackButton.addActionListener(new ButtonListener(BackButton));
        BackButton.setVisible(false);

        DeleteWordButton = new JButton("Delete Word <<");
        DeleteWordButton.setBounds(280, 300, 150, 30);
        DeleteWordButton.addActionListener(new ButtonListener(DeleteWordButton));

        JLabel StrTitle = new JLabel("Vocable");
        StrTitle.setBounds(30, 10, 130, 30);
        StrTitle.setFont(new Font("Arial Black", Font.PLAIN, 20));

        Panel.setOpaque(false);
        Panel.add(BackButton);
        Panel.add(InputSearch);
        Panel.add(Search);
        Panel.add(ScrollPane);
        Panel.add(AddNewWordButton);
        Panel.add(DeleteWordButton);
        Panel.add(StrTitle);
        getContentPane().add(Panel);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dim.width / 2) - (this.getSize().width / 2), (dim.height / 2) - (this.getSize().height / 2));
        getContentPane().setBackground(new java.awt.Color(101, 150, 166));

        setVisible(true);

    }

    MouseListener ClickListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            JList<String> SourceList = (JList) mouseEvent.getSource();
            if (mouseEvent.getClickCount() == 2) {
                int Index = SourceList.locationToIndex(mouseEvent.getPoint());
                if (Index >= 0) {
                    Object Item = List.getModel().getElementAt(Index);
                    System.out.println("Double-clicked on: " + Item.toString());
                    if (List.getName().equals("Eng")) {
                        HolderEng = Item.toString();
                        Display = File.Dict.ReturnTr(Item.toString());
                        List = new JList(Display.toArray());
                        if (Display.size() > 0) {
                            List.setSelectedIndex(0);
                        }
                        List.addMouseListener(this);
                        List.setName("Tr");
                        System.out.println("List : " + List.getName());
                        ScrollPane.setViewportView(List);
                        AddNewWordButton.setVisible(false);
                        DeleteWordButton.setVisible(false);
                        Search.setVisible(false);
                        InputSearch.setVisible(false);
                        BackButton.setVisible(true);

                    } else if (List.getName().equals("Tr")) {
                        HolderTr = Item.toString();
                        Display = File.Dict.ReturnExmp(HolderEng, Item.toString());
                        List = new JList(Display.toArray());
                        if (Display.size() > 0) {
                            List.setSelectedIndex(0);
                        }
                        List.setName("Exmp");
                        ScrollPane.setViewportView(List);
                        System.out.println("List : " + List.getName());

                    }
                }
            }
        }
    };

    private class ButtonListener implements ActionListener {

        JButton Button;

        public ButtonListener(JButton bSource) {
            Button = bSource;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            JFrame NewFrame = new JFrame();
            JPanel NewPanel = new JPanel();
            System.out.println(Button.getText() + " Pressed");

            if (Button.getText().equals("Search")) {
                Display = File.Dict.Search(InputSearch.getText());
                if (Display.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No Match", "Search", JOptionPane.INFORMATION_MESSAGE);
                    Display = File.Dict.FillScroll();
                }
                List = new JList(Display.toArray());
                SelectedIndex();
                InputSearch.setText("");
                List.setName("Eng");
                List.addMouseListener(ClickListener);
                ScrollPane.setViewportView(List);
                System.out.println("List : " + List.getName());

            } else if (Button.getText().equals(" << ")) {
                if (List.getName().equals("Tr")) {
                    Display = File.Dict.FillScroll();
                    List = List = new JList(Display.toArray());
                    if (Display.size() > 0) {
                        List.setSelectedIndex(0);
                    }
                    List.setName("Eng");
                    BackButton.setVisible(false);
                    AddNewWordButton.setVisible(true);
                    DeleteWordButton.setVisible(true);
                    InputSearch.setVisible(true);
                    Search.setVisible(true);
                    List.addMouseListener(ClickListener);
                    ScrollPane.setViewportView(List);
                    System.out.println("List : " + List.getName());

                } else if (List.getName().equals("Exmp")) {
                    Display = File.Dict.ReturnTr(HolderEng);
                    List = List = new JList(Display.toArray());
                    if (Display.size() > 0) {
                        List.setSelectedIndex(0);
                    }
                    List.setName("Tr");
                    ScrollPane.setViewportView(List);
                    List.addMouseListener(ClickListener);
                    System.out.println("List : " + List.getName());

                }

            } else if (Button.getText().equals("Delete Word <<")) {
                String Selected = List.getSelectedValue();
                int Input = JOptionPane.showConfirmDialog(null, "Do You Really Want To Detele -" + Selected + "- ?", "Delete Word", JOptionPane.YES_NO_OPTION);
                if (Input == 0) {
                    System.out.println(Selected + " Deleted");
                    File.Dict.RemoveWord(Selected);
                    Display = File.Dict.FillScroll();
                    List = new JList(Display.toArray());
                    List.setName("Eng");
                    ScrollPane.setViewportView(List);
                    List.addMouseListener(ClickListener);
                    SelectedIndex();
                }

            } else if (Button.getText().equals("Add New Word >>")) {
                NewFrame.setSize(500, 400);
                JLabel EngTitle = new JLabel("Eng:");
                EngTitle.setBounds(10, 20, 130, 20);

                JTextField EngField = new JTextField();
                EngField.setBounds(40, 40, 180, 30);

                JTextField TrField = new JTextField();
                TrField.setBounds(90, 120, 180, 30);

                JLabel TrTitle = new JLabel("Tr:");
                TrTitle.setBounds(65, 100, 130, 20);

                JTextField ExmpField = new JTextField();
                ExmpField.setBounds(130, 200, 330, 30);

                JLabel ExmpTitle = new JLabel("Example:");
                ExmpTitle.setBounds(100, 170, 130, 20);

                JButton AddNewWord = new JButton("Add");
                AddNewWord.setBounds(150, 280, 100, 20);

                AddNewWord.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        File.Dict.AddNewVocable(EngField.getText(), TrField.getText(), ExmpField.getText());
                        Display = File.Dict.FillScroll();
                        List = new JList(Display.toArray());
                        List.setName("Eng");
                        ScrollPane.setViewportView(List);
                        List.addMouseListener(ClickListener);
                        SelectedIndex();
                        JOptionPane.showMessageDialog(null, "New Word Added.");
                        NewFrame.dispose();
                    }
                });

                JButton Cancel = new JButton("Cancel");
                Cancel.setBounds(300, 280, 100, 20);
                Cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        NewFrame.dispose();
                    }
                });
                JLabel StrTitle = new JLabel("Vocable");
                StrTitle.setBounds(350, 100, 130, 30);

                StrTitle.setFont(new Font("Arial Black", Font.PLAIN, 20));
                NewPanel.setLayout(null);
                NewPanel.add(EngField);
                NewPanel.add(TrField);
                NewPanel.add(ExmpField);
                NewPanel.add(AddNewWord);
                NewPanel.add(Cancel);
                NewPanel.add(StrTitle);
                NewPanel.add(EngTitle);
                NewPanel.add(TrTitle);
                NewPanel.add(ExmpTitle);
                NewFrame.add(NewPanel);
                NewFrame.setTitle("Add New Word");
                setResizable(false);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                NewFrame.setLocation((dim.width / 2) - (NewFrame.getSize().width / 2), (dim.height / 2) - (NewFrame.getSize().height / 2));
                NewFrame.setVisible(true);

            }

        }
    }

    public void SelectedIndex() {
        if (Display.size() > 0) {
            List.setSelectedIndex(0);
        }

    }

    public FileController getFile() {
        return File;
    }

}
