package boundary;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.ControllerStudente;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import dto.TaskDTO;
import dto.UtenteDTO;
import eccezioni.DataBaseException;
import eccezioni.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class BoundaryStudente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private JTable tableStudenti;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public BoundaryStudente() {
		ControllerStudente controller_s=ControllerStudente.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 581, 589);
		cardLayout=new CardLayout();
		cardPanel=new JPanel(cardLayout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(cardPanel);
		
		JPanel taskListPanel = new JPanel();
		cardPanel.add(taskListPanel, "name_113406894143300");
		
		JPanel homeStudente = new JPanel();
		cardPanel.add(homeStudente, "name_6810067799600");
		homeStudente.setLayout(null);
		
		JPanel solutionPanel = new JPanel();
		cardPanel.add(solutionPanel, "name_113996636876000");
		solutionPanel.setLayout(null);
		
		
		JFileChooser fileChooser=new JFileChooser();
		solutionPanel.add(fileChooser);
		
		
		JButton btnSoluzione = new JButton("Consegna Soluzione");
		btnSoluzione.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<TaskDTO> listaTask=new ArrayList<>();
				try {
					listaTask = controller_s.getListaTaskStudente();
				} catch (DataBaseException e1) {
				} catch (NotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
				DefaultListModel<TaskDTO> model=new DefaultListModel<>();
				for (TaskDTO t: listaTask) {
					model.addElement(t);
				}
				JList<TaskDTO> taskList = new JList<>(model);
				setVisible(true);
				JScrollPane scrollPane = new JScrollPane(taskList);
				taskListPanel.removeAll();
				taskListPanel.add(scrollPane);
				
				
				JButton btnHome=new JButton("Torna alla home");
				taskListPanel.add(btnHome);
				
				taskListPanel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cardLayout.show(cardPanel, "name_6810067799600");
					}
				});
				
				cardLayout.show(cardPanel,"name_113406894143300");
		        taskList.addMouseListener(new MouseAdapter() {
	        		@Override
	        		public void mouseClicked(MouseEvent e) {
	        			if (e.getClickCount()==2) {
	        				TaskDTO selezionata=taskList.getSelectedValue();
	        				if(selezionata!=null) {
	        					int id_task=selezionata.getIdTask();
	        					cardLayout.show(cardPanel, "name_113996636876000");
	        					int result=fileChooser.showOpenDialog(null);
	        					if(result==JFileChooser.APPROVE_OPTION) {
	        						File contenuto=fileChooser.getSelectedFile();
	        						try {
										controller_s.consegnaSoluzione(id_task,contenuto);
										JOptionPane.showMessageDialog(null, "Soluzione consegnata");
									} catch (IOException | DataBaseException e1) {
										JOptionPane.showMessageDialog(null, e1);;
									}
	        					}
	        				}
	        			}
	        		}	
	        });
				
			}
		});
		btnSoluzione.setBounds(163, 117, 202, 31);
		homeStudente.add(btnSoluzione);
		

		
		JButton btnListaTask = new JButton("Visualizza lista task assegnati");
		btnListaTask.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<TaskDTO> listaTask=new ArrayList<>();
				try {
					listaTask = controller_s.getListaTaskStudente();
				} catch (DataBaseException e1) {
					JOptionPane.showMessageDialog(null, e1);
				} catch (NotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
				DefaultListModel<TaskDTO> model=new DefaultListModel<>();
				for (TaskDTO t: listaTask) {
					model.addElement(t);
				}
				JList<TaskDTO> taskList = new JList<>(model);
				setVisible(true);
				JScrollPane scrollPane = new JScrollPane(taskList);
				taskListPanel.removeAll();
				taskListPanel.add(scrollPane);
				
				JButton btnHome=new JButton("Torna alla home");
				taskListPanel.add(btnHome);
				
				btnHome.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cardLayout.show(cardPanel, "name_6810067799600");
						taskListPanel.removeAll();
					}
				});
				
				cardLayout.show(cardPanel,"name_113406894143300");
			}
		});
		btnListaTask.setBounds(163, 159, 202, 31);
		homeStudente.add(btnListaTask);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(140, 35, 252, 20);
		homeStudente.add(textPane);
		textPane.setText(controller_s.getUtenteCorrente().getEmail());
		
		String[] colonne_media= {"Nome","Cognome","Email","Media"};
		String[] colonne_task= {"Nome","Cognome","Email","Task Completati"};
		
		JPanel classificaStudentiPanel = new JPanel();
		cardPanel.add(classificaStudentiPanel, "name_379548951054000");
		classificaStudentiPanel.setLayout(new BorderLayout());
	
		JButton btnClassifica = new JButton("Visualizza classifica");
		btnClassifica.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	cardLayout.show(cardPanel, "name_53563936928200");
		    }
		});

		btnClassifica.setBounds(163, 327, 202, 31);
		homeStudente.add(btnClassifica);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BoundaryUtente home=new BoundaryUtente();
				home.setVisible(true);
				dispose();
			}
		});
		btnLogOut.setBounds(222, 448, 89, 23);
		homeStudente.add(btnLogOut);
		
		JButton btnMedia = new JButton("Visualizza media");
		btnMedia.setBounds(163, 201, 202, 31);
		homeStudente.add(btnMedia);
		
		JButton btnPunteggio = new JButton("Visualizza punteggio totale");
		btnPunteggio.setBounds(163, 243, 202, 31);
		homeStudente.add(btnPunteggio);
		
		JButton btnIscrizione = new JButton("Iscriviti");
		btnIscrizione.setBounds(163, 285, 202, 31);
		homeStudente.add(btnIscrizione);
		
		cardLayout.show(cardPanel, "name_6810067799600");  // Mostra la homeStudente
		
		JPanel panelClassifica = new JPanel();
		cardPanel.add(panelClassifica, "name_53563936928200");
		panelClassifica.setLayout(null);
		
		JTextArea txtrScegliereIlCriterio = new JTextArea();
		txtrScegliereIlCriterio.setText("Scegliere il criterio di ordinamento");
		txtrScegliereIlCriterio.setBounds(10, 5, 585, 22);
		panelClassifica.add(txtrScegliereIlCriterio);
		
		JButton btnClassificaMedia = new JButton("Classifica per punteggio medio");
		btnClassificaMedia.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        Object[][] dati = null;
		        try {
		            ArrayList<UtenteDTO> lista_studenti_ordinata = controller_s.getClassificaStudentiMedia();

		            dati = new Object[lista_studenti_ordinata.size()][5];
		            for (int i = 0; i < lista_studenti_ordinata.size(); i++) {
		                UtenteDTO u = lista_studenti_ordinata.get(lista_studenti_ordinata.size() - 1 - i);
		                dati[i][0] = u.getNome(); 
		                dati[i][1] = u.getCognome(); 
		                dati[i][2] = u.getEmail(); 
		                dati[i][3] = u.getMediaPunti();
		            }

		            @SuppressWarnings("serial")
		            DefaultTableModel model = new DefaultTableModel(dati, colonne_media) {
		                @Override
		                public boolean isCellEditable(int row, int column) {
		                    return false;
		                }
		            };

		            tableStudenti = new JTable(model);
		            tableStudenti.setFillsViewportHeight(true);
		            tableStudenti.setRowHeight(30);

		            classificaStudentiPanel.removeAll();
		            classificaStudentiPanel.setLayout(new BorderLayout());

		            JScrollPane scrollPane = new JScrollPane(tableStudenti);
		            classificaStudentiPanel.add(scrollPane, BorderLayout.CENTER);

		            JButton btnHome = new JButton("Torna alla home");
		            btnHome.setPreferredSize(new Dimension(150, 30));
		            classificaStudentiPanel.add(btnHome, BorderLayout.SOUTH);

		            btnHome.addMouseListener(new MouseAdapter() {
		                @Override
		                public void mouseClicked(MouseEvent e) {
		                    cardLayout.show(cardPanel, "name_6810067799600");
		                    classificaStudentiPanel.removeAll();
		                }
		            });

		        } catch (DataBaseException e1) {
		            JOptionPane.showMessageDialog(null, e1);
		        }
		        cardLayout.show(cardPanel, "name_379548951054000");
		    }
		});

		btnClassificaMedia.setBounds(127, 190, 282, 53);
		panelClassifica.add(btnClassificaMedia);
		
		JButton btnClassificaTask = new JButton("Classifica per numero di task completati");
		btnClassificaTask.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        Object[][] dati = null;
		        try {
		            ArrayList<UtenteDTO> lista_studenti_ordinata = controller_s.getClassificaStudentiTask();

		            dati = new Object[lista_studenti_ordinata.size()][5];
		            for (int i = 0; i < lista_studenti_ordinata.size(); i++) {
		                UtenteDTO u = lista_studenti_ordinata.get(lista_studenti_ordinata.size() - 1 - i);
		                dati[i][0] = u.getNome(); 
		                dati[i][1] = u.getCognome(); 
		                dati[i][2] = u.getEmail(); 
		                dati[i][3] = u.getTaskCompletati();
		            }

		            @SuppressWarnings("serial")
		            DefaultTableModel model = new DefaultTableModel(dati, colonne_task) {
		                @Override
		                public boolean isCellEditable(int row, int column) {
		                    return false;
		                }
		            };

		            tableStudenti = new JTable(model);
		            tableStudenti.setFillsViewportHeight(true);
		            tableStudenti.setRowHeight(30);

		            classificaStudentiPanel.removeAll();
		            classificaStudentiPanel.setLayout(new BorderLayout());

		            JScrollPane scrollPane = new JScrollPane(tableStudenti);
		            classificaStudentiPanel.add(scrollPane, BorderLayout.CENTER);

		            JButton btnHome = new JButton("Torna alla home");
		            btnHome.setPreferredSize(new Dimension(150, 30));
		            classificaStudentiPanel.add(btnHome, BorderLayout.SOUTH);

		            btnHome.addMouseListener(new MouseAdapter() {
		                @Override
		                public void mouseClicked(MouseEvent e) {
		                    cardLayout.show(cardPanel, "name_6810067799600");
		                    classificaStudentiPanel.removeAll();
		                }
		            });

		        } catch (DataBaseException e1) {
		            JOptionPane.showMessageDialog(null, e1);
		        }
		        cardLayout.show(cardPanel, "name_379548951054000");
		    }
		});


		btnClassificaTask.setBounds(127, 274, 282, 53);
		panelClassifica.add(btnClassificaTask);
		

	}
}
