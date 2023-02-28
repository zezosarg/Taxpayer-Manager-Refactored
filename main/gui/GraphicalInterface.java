package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import datamanagement.TaxpayerManager;
import datamanagement.WrongFileEndingException;
import datamanagement.WrongTaxpayerStatusException;
import parser.WrongFileFormatException;
import parser.WrongReceiptDateException;
import parser.WrongReceiptKindException;

public class GraphicalInterface extends JFrame {

	  private JPanel contentPane;
	  private TaxpayerManager taxpayerManager = new TaxpayerManager();
	  private String taxpayersTRN = new String();
	  private JTextField txtTaxRegistrationNumber;
	  private String selectedTaxNumber = null;
	  static final long serialVersionUID = 2L;

	  public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	      public void run() {
	        try {
	          GraphicalInterface frame = new GraphicalInterface();
	          frame.setVisible(true);
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      }
	    });
	  }

	  public GraphicalInterface() {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 450, 500);
	    contentPane = new JPanel();
	    contentPane.setBackground(new Color(204, 204, 204));
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(contentPane);
	    contentPane.setLayout(null);

	    try {
	      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
	        | UnsupportedLookAndFeelException e2) {
	      e2.printStackTrace();
	    }

	    JTextPane textPane = new JTextPane();
	    textPane.setEditable(false);
	    textPane.setBackground(new Color(153, 204, 204));
	    textPane.setBounds(0, 21, 433, 441);

	    JPanel fileLoaderPanel = new JPanel(new BorderLayout());
	    JPanel boxPanel = new JPanel(new BorderLayout());
	    JPanel taxRegistrationNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
	    JLabel TRN = new JLabel("Give the tax registration number:");
	    JTextField taxRegistrationNumberField = new JTextField(20);
	    taxRegistrationNumberPanel.add(TRN);
	    taxRegistrationNumberPanel.add(taxRegistrationNumberField);
	    JPanel loadPanel = new JPanel(new GridLayout(1, 2));
	    loadPanel.add(taxRegistrationNumberPanel);
	    fileLoaderPanel.add(boxPanel, BorderLayout.NORTH);
	    fileLoaderPanel.add(loadPanel, BorderLayout.CENTER);
	    JCheckBox txtBox = new JCheckBox("Txt file");
	    JCheckBox xmlBox = new JCheckBox("Xml file");

	    txtBox.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        xmlBox.setSelected(false);
	      }
	    });

	    xmlBox.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        txtBox.setSelected(false);
	      }
	    });
	    txtBox.doClick();
	    boxPanel.add(txtBox, BorderLayout.WEST);
	    boxPanel.add(xmlBox, BorderLayout.EAST);

	    DefaultListModel<String> taxRegisterNumberModel = new DefaultListModel<String>();

	    JList<String> taxRegisterNumberList = new JList<String>(taxRegisterNumberModel);
	    taxRegisterNumberList.setBackground(new Color(153, 204, 204));
	    taxRegisterNumberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    taxRegisterNumberList.setSelectedIndex(0);
	    taxRegisterNumberList.setVisibleRowCount(3);

	    JScrollPane taxRegisterNumberListScrollPane = new JScrollPane(taxRegisterNumberList);
	    taxRegisterNumberListScrollPane.setSize(300, 300);
	    taxRegisterNumberListScrollPane.setLocation(70, 100);
	    contentPane.add(taxRegisterNumberListScrollPane);

	    JButton btnLoadTaxpayer = new JButton("Load Taxpayer");
	    btnLoadTaxpayer.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {

	        JFileChooser fileBrowser = new JFileChooser();
	        fileBrowser.setCurrentDirectory(new File("."));
	        int answer = fileBrowser.showOpenDialog(fileLoaderPanel);
	        String absoluteFilePath;
	        if (answer == JFileChooser.APPROVE_OPTION) {
	          absoluteFilePath = fileBrowser.getSelectedFile().getAbsolutePath();
	          String taxRegistrationNumberFile = Paths.get(absoluteFilePath).getFileName().toString();
	          String[] fileNameParts = taxRegistrationNumberFile.split("\\.", 2);
	          String taxRegistrationNumberString = fileNameParts[0].split("_")[0];
	          int taxRegistrationNumberInt = Integer.parseInt(taxRegistrationNumberString);
	          if (taxRegistrationNumberString.length() != 9) {
	            JOptionPane.showMessageDialog(null,
	                "The tax registration number must have 9 digits.\n");
	          } else {
	            try {
	              if (taxpayerManager.containsTaxpayer(taxRegistrationNumberInt)) {
	                JOptionPane.showMessageDialog(null, "This taxpayer is already loaded.");
	              } else {
	                taxpayerManager.loadTaxpayer(taxRegistrationNumberFile);
	                taxRegisterNumberModel.addElement(taxRegistrationNumberString);
	              }
	              textPane.setText(taxpayersTRN);
	            } catch (NumberFormatException e1) {
	              JOptionPane.showMessageDialog(null,
	                  "The tax registration number must have only digits.");
	            } catch (WrongFileFormatException e1) {
	              JOptionPane.showMessageDialog(null, "Please check your file format and try again.");
	            } catch (WrongFileEndingException e1) {
	              JOptionPane.showMessageDialog(null, "Please check your file ending and try again.");
	            } catch (WrongTaxpayerStatusException e1) {
	              JOptionPane.showMessageDialog(null, "Please check taxpayer's status and try again.");
	            } catch (WrongReceiptKindException e1) {
	              JOptionPane.showMessageDialog(null, "Please check receipts kind and try again.");
	            } catch (WrongReceiptDateException e1) {
	              JOptionPane.showMessageDialog(null,
	                  "Please make sure your date is " + "DD/MM/YYYY and try again.");
	            } catch (IOException e1) {
	              JOptionPane.showMessageDialog(null, "The file does not exist.");
	            }
	          }
	        }
	      }
	    });
	    btnLoadTaxpayer.setBounds(0, 0, 146, 23);
	    contentPane.add(btnLoadTaxpayer);

	    JButton btnSelectTaxpayer = new JButton("Select Taxpayer");
	    btnSelectTaxpayer.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        if (!taxRegisterNumberModel.isEmpty()) {
	          if (selectedTaxNumber != null) {
	            int selectedTaxNumberInt = Integer.parseInt(selectedTaxNumber);
	            TaxpayerData taxpayerData = new TaxpayerData(selectedTaxNumberInt, taxpayerManager);
	            taxpayerData.setVisible(true);
	          } else {
	            JOptionPane.showMessageDialog(null, "A tax number must be selected first.");
	          }
	        } else {
	          JOptionPane.showMessageDialog(null,
	              "There isn't any taxpayer loaded. Please load one first.");
	        }
	      }
	    });
	    btnSelectTaxpayer.setBounds(147, 0, 139, 23);
	    contentPane.add(btnSelectTaxpayer);

	    JButton btnDeleteTaxpayer = new JButton("Delete Taxpayer");
	    btnDeleteTaxpayer.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent arg0) {
	        if (!taxRegisterNumberModel.isEmpty()) {
	          if (selectedTaxNumber != null) {
	            int selectedTaxNumberInt = Integer.parseInt(selectedTaxNumber);
	            taxpayerManager.removeTaxpayer(selectedTaxNumberInt);
	            taxRegisterNumberModel.removeElement(selectedTaxNumber);
	          } else {
	            JOptionPane.showMessageDialog(null, "A tax number must be selected first.");
	          }
	        } else {
	          JOptionPane.showMessageDialog(null,
	              "There isn't any taxpayer loaded. Please load one first.");
	        }
	      }
	    });
	    btnDeleteTaxpayer.setBounds(287, 0, 146, 23);
	    contentPane.add(btnDeleteTaxpayer);

	    taxRegisterNumberList.getSelectionModel().addListSelectionListener(event -> {
	      this.selectedTaxNumber = taxRegisterNumberList.getSelectedValue();
	    });

	    txtTaxRegistrationNumber = new JTextField();
	    txtTaxRegistrationNumber.setEditable(false);
	    txtTaxRegistrationNumber.setBackground(new Color(153, 204, 204));
	    txtTaxRegistrationNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
	    txtTaxRegistrationNumber.setText("Tax Registration Number:");
	    txtTaxRegistrationNumber.setBounds(70, 80, 300, 20);
	    contentPane.add(txtTaxRegistrationNumber);
	    txtTaxRegistrationNumber.setColumns(10);

	  }
	}