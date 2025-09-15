package boundary;

import java.awt.CardLayout;
import java.awt.EventQueue;
import control.ControllerUtente;
import eccezioni.DataBaseException;
import eccezioni.NotFoundException;
import eccezioni.UtenteGiaRegistratoException;
import utilities.Validation;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;

public class BoundaryUtente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel autenticazione;
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private JTextField nomeField;
	private JTextField cognomeField;
	private JTextField emailFieldAutenticazione;
	private JTextField emailFieldRegistrazione;
	private JPasswordField passwordFieldRegistrazione;
	private JPasswordField passwordFieldAutenticazione;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoundaryUtente frame = new BoundaryUtente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BoundaryUtente() {
		ControllerUtente controller=ControllerUtente.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 591, 589);
		cardLayout=new CardLayout();
		cardPanel=new JPanel(cardLayout);
		autenticazione = new JPanel();
		autenticazione.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel registrazione=new JPanel();
		autenticazione.setLayout(null);
		JPanel home = new JPanel();
		cardPanel.add(home, "Home");
		home.setLayout(null);
		cardPanel.add(registrazione,"Form registrazione");
		registrazione.setLayout(null);
		setContentPane(cardPanel);
		cardLayout.show(cardPanel,"Home");
		nomeField = new JTextField();
		nomeField.setBounds(113, 156, 260, 19);
		nomeField.setColumns(10);
		registrazione.add(nomeField);
		
		JLabel LableNome = new JLabel("Nome");
		LableNome.setBounds(113, 141, 260, 13);
		registrazione.add(LableNome);
		
		cognomeField = new JTextField();
		cognomeField.setBounds(113, 200, 260, 19);
		cognomeField.setColumns(10);
		registrazione.add(cognomeField);
		
		JLabel LableCognome = new JLabel("Cognome");
		LableCognome.setBounds(113, 186, 260, 13);
		registrazione.add(LableCognome);
		
		emailFieldRegistrazione = new JTextField();
		emailFieldRegistrazione.setBounds(113, 248, 260, 19);
		emailFieldRegistrazione.setColumns(10);
		registrazione.add(emailFieldRegistrazione);
		
		JLabel LabelRuolo = new JLabel("Ruolo");
		LabelRuolo.setBounds(113, 334, 260, 13);
		registrazione.add(LabelRuolo);
		
		JLabel LabelEmailRegistrazione = new JLabel("Indirizzo e-mail");
		LabelEmailRegistrazione.setBounds(113, 230, 260, 13);
		registrazione.add(LabelEmailRegistrazione);
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Docente", "Studente"}));
		comboBox.setBounds(113, 348, 260, 45);
		registrazione.add(comboBox);
		
		passwordFieldRegistrazione = new JPasswordField();
		passwordFieldRegistrazione.setBounds(113, 291, 260, 20);
		registrazione.add(passwordFieldRegistrazione);
		
		
		JButton btnRegistrati = new JButton("Registrati");
		btnRegistrati.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nome=nomeField.getText();
				String cognome=cognomeField.getText();
				String email=emailFieldRegistrazione.getText();
				String password=BCrypt.hashpw(new String(passwordFieldRegistrazione.getPassword()), BCrypt.gensalt());
				System.out.println(password);
				String ruolo=(String)comboBox.getSelectedItem();
				System.out.println(nome+" "+cognome+" "+email+" "+ruolo);
				if(!nome.isEmpty()||!cognome.isEmpty()||!email.isEmpty()||!ruolo.isEmpty()) {
					try {
						Validation.validateNome(nome);
						Validation.validateCognome(cognome);
						Validation.validateEmail(email);
						Validation.validatePassword(password);
						controller.registrazione(nome, cognome, email,password,ruolo);
						JOptionPane.showMessageDialog(null,"Registrazione avvenuta con successo");
					} catch (DataBaseException e1) {
						JOptionPane.showMessageDialog(null,e1);
					} catch (UtenteGiaRegistratoException e1) {
						JOptionPane.showMessageDialog(null,e1);
					} catch(IllegalArgumentException e1) {
						JOptionPane.showMessageDialog(null,e1);
					}
				}
			}
		});
		btnRegistrati.setBounds(387, 518, 178, 21);
		registrazione.add(btnRegistrati);
		
		JTextArea txtrInserireLeCredenziali = new JTextArea();
		txtrInserireLeCredenziali.setBounds(80, 11, 357, 22);
		txtrInserireLeCredenziali.setText("Inserire le credenziali per la registrazione");
		registrazione.add(txtrInserireLeCredenziali);
		
		JButton btnHome = new JButton("Torna alla home");
		btnHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(cardPanel,"Home");
			}
		});
		btnHome.setBounds(113, 479, 260, 21);
		registrazione.add(btnHome);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(113, 280, 260, 13);
		registrazione.add(lblPassword);
		
		
		
		
		cardPanel.add(autenticazione,"Form autenticazione");
		
		JTextArea txtrInserireLidutentePer = new JTextArea();
		txtrInserireLidutentePer.setText("Inserire l'IdUtente per la registrazione");
		txtrInserireLidutentePer.setBounds(93, 11, 341, 22);
		autenticazione.add(txtrInserireLidutentePer);
		
		emailFieldAutenticazione = new JTextField();
		emailFieldAutenticazione.setBounds(162, 158, 177, 19);
		autenticazione.add(emailFieldAutenticazione);
		emailFieldAutenticazione.setColumns(10);
		
		JLabel emailLabelAutenticazione = new JLabel("email");
		emailLabelAutenticazione.setBounds(162, 148, 177, 13);
		autenticazione.add(emailLabelAutenticazione);
		
		JComboBox<String> comboBoxAutenticazione = new JComboBox<>();
		comboBoxAutenticazione.setModel(new DefaultComboBoxModel<>(new String[] {"Docente", "Studente"}));
		comboBoxAutenticazione.setBounds(162, 246, 177, 21);
		autenticazione.add(comboBoxAutenticazione);
		
		passwordFieldAutenticazione = new JPasswordField();
		passwordFieldAutenticazione.setBounds(162, 199, 177, 20);
		autenticazione.add(passwordFieldAutenticazione);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String email=emailFieldAutenticazione.getText();
				String password=new String(passwordFieldAutenticazione.getPassword());
				String ruolo=(String) comboBoxAutenticazione.getSelectedItem();
				System.out.println(ruolo);
				try {
					Validation.validateEmail(email);
					Validation.validatePassword(password);
					controller.autenticazione(email, password,ruolo);
					if(ruolo.equalsIgnoreCase("Studente")) {
						BoundaryStudente stud=new BoundaryStudente();
						stud.setVisible(true);
					}
					else if(ruolo.equalsIgnoreCase("Docente")) {
						BoundaryDocente doc=new BoundaryDocente();
						doc.setVisible(true);
					}
				} catch (DataBaseException e1) {
					JOptionPane.showMessageDialog(null, e1);
				} catch (NotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1);
				} catch(IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		
		
		btnLogIn.setBounds(162, 287, 177, 21);
		autenticazione.add(btnLogIn);
		
		JLabel roleLabel = new JLabel("ruolo");
		roleLabel.setBounds(162, 230, 177, 13);
		autenticazione.add(roleLabel);
		
		JButton btnHome2 = new JButton("Torna alla home");
		btnHome2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(cardPanel,"Home");
			}
		});
		btnHome2.setBounds(176, 469, 151, 51);
		autenticazione.add(btnHome2);
		
		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setBounds(162, 188, 177, 13);
		autenticazione.add(lblPassword_1);
		
		
		
		
		
		JButton btnRegistrazione = new JButton("Registrazione");
		btnRegistrazione.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(cardPanel,"Form registrazione");
			}
		});
		btnRegistrazione.setBounds(158, 164, 228, 55);
		home.add(btnRegistrazione);
		
		JButton btnAutenticazione = new JButton("Autenticazione");
		btnAutenticazione.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(cardPanel,"Form autenticazione");
			}
		});
		btnAutenticazione.setBounds(158, 264, 228, 55);
		home.add(btnAutenticazione);
	}
}
