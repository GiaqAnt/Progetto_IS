package boundary;

import org.jdatepicker.DateModel;
import org.jdatepicker.impl.*;

import java.time.LocalDate;

import java.awt.CardLayout;
import java.awt.Desktop;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import control.ControllerDocente;
import dto.ClasseDTO;
import dto.SoluzioneDTO;
import dto.TaskDTO;
import dto.UtenteDTO;
import eccezioni.DataBaseException;
import eccezioni.NotFoundException;
import eccezioni.PunteggioTroppoAltoException;
import eccezioni.StudenteGiaIscrittoException;
import utilities.Validation;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;


public class BoundaryDocente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel homeDocente;
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private JTextField titoloField;
	private JTextField descrizioneField;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	private JTextField textField;
	private ControllerDocente controller_d;
	private JPanel classListPanel;
	private JPanel taskListPanel;
	private JPanel studentListPanel;
	private JPanel solutionListPanel;
	private JButton confermaButton;
	private JButton downloadButton;
	private JSpinner punteggio;
	private JPanel assegnaPunteggio;
	private JPanel creaTask;
	private JButton taskButton;
	private ClasseDTO classeSelezionata;
	

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public BoundaryDocente() {
		controller_d=ControllerDocente.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 585);
		cardLayout=new CardLayout();
		cardPanel=new JPanel(cardLayout);
		homeDocente = new JPanel();
		homeDocente.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel creaClasse=new JPanel();
		JLabel nomeLabel=new JLabel("Nome classe");
		nomeLabel.setBounds(149, 223, 150, 13);
		JTextField nomeField=new JTextField();
		nomeField.setBounds(149, 238, 249, 25);
		JButton submitButton= new JButton("Crea classe");
		submitButton.setBounds(149, 274, 249, 31);
		creaClasse.setLayout(null);
		creaClasse.add(nomeLabel);
		creaClasse.add(nomeField);
		JLabel label = new JLabel();
		label.setBounds(-11, 182, 213, 81);
		creaClasse.add(label);
		creaClasse.add(submitButton);
		
		cardPanel.add(homeDocente,"start");
		cardPanel.add(creaClasse,"Form Classe");
		
		JButton btnGoHome = new JButton("Ritorna alla home");
		btnGoHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(cardPanel,"start");
			}
		});
		btnGoHome.setBounds(338, 462, 180, 23);
		creaClasse.add(btnGoHome);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(157, 11, 195, 20);
		homeDocente.add(textPane);
		getContentPane().add(cardPanel);
		textPane.setText(controller_d.getUtenteCorrente().getEmail());
		
		
		//setContentPane(contentPane);
		homeDocente.setLayout(null);
		
		JButton btnClasse = new JButton("Crea Classe");
		btnClasse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(cardPanel,"Form Classe");
			}
		});
		
		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nome=nomeField.getText();
				try {
					Validation.validateClassName(nome);
					controller_d.creaClasse(nome);
					JOptionPane.showMessageDialog(null,"Classe creata con successo");
				}
				catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(null, e1);
				} catch (DataBaseException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnClasse.setBounds(137, 162, 239, 21);
		homeDocente.add(btnClasse);
		
		JButton btnTask = new JButton("Crea task");
		btnTask.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostraListaClassi(btnTask);
			}
		});
		btnTask.setBounds(137, 194, 239, 21);
		homeDocente.add(btnTask);
		classListPanel = new JPanel();
		cardPanel.add(classListPanel, "Lista classi");
		
		studentListPanel = new JPanel();
		cardPanel.add(studentListPanel, "Lista studenti");
		
		JButton btnListaClassi = new JButton("Visualizza lista classi");
		btnListaClassi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostraListaClassi(btnListaClassi);
			}
		});
		
		
		taskListPanel = new JPanel();
		cardPanel.add(taskListPanel, "Lista task");
		
		
		btnListaClassi.setBounds(137, 226, 239, 21);
		homeDocente.add(btnListaClassi);
		
		JButton btnListaTask = new JButton("Visualizza lista task");
		btnListaTask.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostraListaClassi(btnListaTask);	
			}
		});
		
		
		btnListaTask.setBounds(137, 258, 239, 21);
		homeDocente.add(btnListaTask);
		
		JButton btnIscrizione = new JButton("Iscrivi uno studente");
		btnIscrizione.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostraListaStudentiPiattaforma(btnIscrizione);
			}
		});
		btnIscrizione.setBounds(137, 290, 239, 21);
		homeDocente.add(btnIscrizione);
		
		
		solutionListPanel = new JPanel();
		cardPanel.add(solutionListPanel, "Lista soluzioni");
		
		assegnaPunteggio = new JPanel();
		cardPanel.add(assegnaPunteggio, "Form punteggio");
		
		JLabel labelPunteggio = new JLabel("Inserisci il punteggio:");
		labelPunteggio.setBounds(140, 273, 195, 13);

        downloadButton = new JButton("Visualizza/Scarica Soluzione");
        downloadButton.setBounds(140, 191, 195, 21);
        
        confermaButton = new JButton("Assegna Punteggio");
        confermaButton.setBounds(140, 319, 195, 21);
        assegnaPunteggio.setLayout(null);
        assegnaPunteggio.add(downloadButton);
        assegnaPunteggio.add(labelPunteggio);
        assegnaPunteggio.add(confermaButton);
        
        textField = new JTextField();
        textField.setBounds(140, 289, 195, 19);
        assegnaPunteggio.add(textField);
        textField.setColumns(10);
        
        JButton btnGoHomePunteggio = new JButton("Torna alla home");
        btnGoHomePunteggio.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		cardLayout.show(cardPanel, "start");
        	}
        });
        btnGoHomePunteggio.setBounds(370, 512, 176, 23);
        assegnaPunteggio.add(btnGoHomePunteggio);
		
		JButton btnAssegnaPunteggio = new JButton("Correggi");
		btnAssegnaPunteggio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostraListaClassi(btnAssegnaPunteggio);
			}
		});
		btnAssegnaPunteggio.setBounds(137, 322, 239, 21);
		homeDocente.add(btnAssegnaPunteggio);
		
		JButton buttonLogOutDocente = new JButton("Log Out");
		buttonLogOutDocente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BoundaryUtente home=new BoundaryUtente();
				home.setVisible(true);
				dispose();
			}
		});
		buttonLogOutDocente.setBounds(213, 414, 85, 21);
		homeDocente.add(buttonLogOutDocente);
		JButton btnStudenti = new JButton("Visualizza lista studenti");
		btnStudenti.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        ArrayList<UtenteDTO> listaStudenti = new ArrayList<>();
		        try {
		            listaStudenti = controller_d.getListaStudentiPiattaforma();
		            DefaultListModel<UtenteDTO> model_2 = new DefaultListModel<>();
		            for (UtenteDTO u : listaStudenti) {
		                model_2.addElement(u);
		            }

		            JList<UtenteDTO> studentList = new JList<>(model_2);
		            JScrollPane scrollPane = new JScrollPane(studentList);

		            studentListPanel.removeAll();
		            studentListPanel.add(scrollPane);

		            JButton btnHome = new JButton("Torna alla home");
		            studentListPanel.add(btnHome);

		            btnHome.addMouseListener(new MouseAdapter() {
		                @Override
		                public void mouseClicked(MouseEvent e) {
		                    cardLayout.show(cardPanel, "start");
		                    classListPanel.removeAll();
		                }
		            });

		            cardLayout.show(cardPanel, "Lista studenti");
		            setVisible(true);
		        } catch (DataBaseException e1) {
		            JOptionPane.showMessageDialog(null, e1);
		        } catch (NotFoundException e1) {
		            JOptionPane.showMessageDialog(null, e1);
		        }
		    }
		});

		btnStudenti.setBounds(137, 128, 239, 23);
		homeDocente.add(btnStudenti);
		
		
		creaTask = new JPanel();
		cardPanel.add(creaTask, "Form Task");
		creaTask.setLayout(null);
		
		titoloField = new JTextField();
		titoloField.setBounds(132, 159, 192, 19);
		creaTask.add(titoloField);
		titoloField.setColumns(10);
		
		descrizioneField = new JTextField();
		descrizioneField.setBounds(132, 200, 192, 19);
		creaTask.add(descrizioneField);
		descrizioneField.setColumns(10);
		
		UtilDateModel model=new UtilDateModel();
		Properties prop= new Properties();
		prop.put("text.day", "Giorno");
		prop.put("text.month","Mese");
		prop.put("text.year","Anno");
		datePanel=new JDatePanelImpl(model,prop);
		datePicker=new JDatePickerImpl(datePanel,new DateComponentFormatter());
		datePicker.setBounds(132,244,192,25);
		creaTask.add(datePicker);
		
		JLabel titoloLabel = new JLabel("Titolo");
		titoloLabel.setBounds(132, 145, 192, 13);
		creaTask.add(titoloLabel);
		
		JLabel descrizioneLabel = new JLabel("Descrizione");
		descrizioneLabel.setBounds(132, 186, 192, 13);
		creaTask.add(descrizioneLabel);
		
		JLabel dateLabel = new JLabel("Data di scadenza");
		dateLabel.setBounds(132, 230, 192, 13);
		creaTask.add(dateLabel);
		
		JLabel punteggioLabel = new JLabel("Punteggio massimo");
		punteggioLabel.setBounds(132, 280, 205, 13);
		creaTask.add(punteggioLabel);
		
		punteggio = new JSpinner();
		punteggio.setBounds(132, 295, 65, 25);
		creaTask.add(punteggio);
		
		taskButton = new JButton("Crea task");
		taskButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				creaTask(e,classeSelezionata);
			}
		});
		taskButton.setBounds(132, 331, 192, 21);
		creaTask.add(taskButton);
		
		JButton btnGoHomeTask = new JButton("Torna alla home");
		btnGoHomeTask.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(cardPanel, "start");
			}
		});
		btnGoHomeTask.setBounds(354, 512, 192, 23);
		creaTask.add(btnGoHomeTask);
	}
	
	private void mostraListaClassi(JButton btn) {
		ArrayList<ClasseDTO> listaClassi=new ArrayList<>();
		try {
			listaClassi = controller_d.getListaClassi();
			DefaultListModel<ClasseDTO> model_1=new DefaultListModel<>();
			for (ClasseDTO c: listaClassi) {
				model_1.addElement(c);
			}
			JList<ClasseDTO> classList = new JList<>(model_1);
			setVisible(true);
			JScrollPane scrollPane = new JScrollPane(classList);
			classListPanel.removeAll();
			classListPanel.add(scrollPane);
			
			JButton btnHome=new JButton("Torna alla home");
			classListPanel.add(btnHome);
			
			btnHome.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cardLayout.show(cardPanel, "start");
					classList.removeAll();
				}
			});
			cardLayout.show(cardPanel,"Lista classi");
			if(btn.getText().equalsIgnoreCase("Visualizza lista task") || btn.getText().equalsIgnoreCase("Correggi")) {
				classList.addMouseListener(new MouseAdapter() {
		    		@Override
		    		public void mouseClicked(MouseEvent e) {
		    			mostraListaTask(e,classList,btn);
		    		}
				});
			}
			else if(btn.getText().equalsIgnoreCase("Crea task")) {
				classList.addMouseListener(new MouseAdapter() {
		    		@Override
		    		public void mouseClicked(MouseEvent e) {
		    			if(e.getClickCount()==2) {
		    				classeSelezionata=classList.getSelectedValue();
		    				cardLayout.show(cardPanel, "Form Task");
		    			}
		    		}
				});
			}
			

		} catch (DataBaseException e1) {
			JOptionPane.showMessageDialog(null, e1);
			e1.printStackTrace();
		} catch (NotFoundException e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
	}
	
	private void mostraListaTask(MouseEvent e, JList<ClasseDTO> classList,JButton btn) {
		if (e.getClickCount()==2) {
			ClasseDTO selezionata=classList.getSelectedValue();
			System.out.println(selezionata);
			if(selezionata!=null) {
				String codiceClasse=selezionata.getCodice();
				ArrayList<TaskDTO> lista_task=new ArrayList<>();
				try {
					lista_task = controller_d.getListaTask(codiceClasse);
					DefaultListModel<TaskDTO> model_3=new DefaultListModel<>();
					for (TaskDTO t: lista_task) {
						model_3.addElement(t);
					}
					JList<TaskDTO> taskList = new JList<>(model_3);
					setVisible(true);
					JScrollPane scrollPane = new JScrollPane(taskList);
					taskListPanel.removeAll();
					taskListPanel.add(scrollPane);					
					JButton btnHome=new JButton("Torna alla home");
					taskListPanel.add(btnHome);
					
					btnHome.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							cardLayout.show(cardPanel, "start");
							taskListPanel.removeAll();
						}
					});
					
					cardLayout.show(cardPanel,"Lista task");
					if(btn.getText().equalsIgnoreCase("Correggi")) {
						taskList.addMouseListener(new MouseAdapter() {
				    		@Override
				    		public void mouseClicked(MouseEvent e) {
				    			mostraListaSoluzioni(e,taskList,codiceClasse);
				    		}
						});
					}
				} catch (DataBaseException e1) {
					JOptionPane.showMessageDialog(null, e1);
					e1.printStackTrace();
				} catch (NotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1);
					e1.printStackTrace();
				}
			}
		}
	}
	
	/*private void mostraListaStudentiPiattaforma(MouseEvent e, JList<ClasseDTO> classList, JButton btn) {
	    if (e.getClickCount() == 2) {
	        ClasseDTO selezionata = classList.getSelectedValue();
	        if (selezionata != null) {
	            String codiceClasse = selezionata.getCodice();
	            ArrayList<UtenteDTO> listaStudenti = new ArrayList<>();
	            try {
	                listaStudenti = controller_u.getListaStudentiPiattaforma();
	                DefaultListModel<UtenteDTO> model_2 = new DefaultListModel<>();
	                for (UtenteDTO u : listaStudenti) {
	                    model_2.addElement(u);
	                }

	                JList<UtenteDTO> studentList = new JList<>(model_2);
	                JScrollPane scrollPane = new JScrollPane(studentList);

	                studentListPanel.removeAll();
	                studentListPanel.add(scrollPane);

	                JButton btnHome = new JButton("Torna alla home");
	                studentListPanel.add(btnHome);

	                btnHome.addMouseListener(new MouseAdapter() {
	                    @Override
	                    public void mouseClicked(MouseEvent e) {
	                        cardLayout.show(cardPanel, "start");
	                        classListPanel.removeAll();
	                    }
	                });

	                if (btn.getText().equalsIgnoreCase("Iscrivi uno studente")) {
	                    studentList.addMouseListener(new MouseAdapter() {
	                        @Override
	                        public void mouseClicked(MouseEvent e) {
	                            iscriviStudente(e, studentList, codiceClasse);
	                        }
	                    });
	                } 

	                cardLayout.show(cardPanel, "Lista studenti");
	                setVisible(true);

	            } catch (DataBaseException e1) {
	                JOptionPane.showMessageDialog(null, e1);
	            } catch (NotFoundException e1) {
	                JOptionPane.showMessageDialog(null, e1);
	            }
	        } 
	    }
	}*/
	
	private void mostraListaStudentiPiattaforma(JButton btn) {
        ArrayList<UtenteDTO> listaStudenti = new ArrayList<>();
        try {
            listaStudenti = controller_d.getListaStudentiPiattaforma();
            DefaultListModel<UtenteDTO> model_2 = new DefaultListModel<>();
            for (UtenteDTO u : listaStudenti) {
                model_2.addElement(u);
            }

            JList<UtenteDTO> studentList = new JList<>(model_2);
            JScrollPane scrollPane = new JScrollPane(studentList);

            studentListPanel.removeAll();
            studentListPanel.add(scrollPane);

            JButton btnHome = new JButton("Torna alla home");
            studentListPanel.add(btnHome);

            btnHome.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cardLayout.show(cardPanel, "start");
                    classListPanel.removeAll();
                }
            });
            if(btn.getText().equalsIgnoreCase("Iscrivi uno studente")) {
            	studentList.addMouseListener(new MouseAdapter() {
            		public void mouseClicked(MouseEvent e) {
            			if(e.getClickCount()==2) {
            				mostraListaClassiIscrizione(studentList);
            			}
            		}
            	});
            }
            cardLayout.show(cardPanel, "Lista studenti");
            setVisible(true);
        } catch (DataBaseException e1) {
            JOptionPane.showMessageDialog(null, e1);
        } catch (NotFoundException e1) {
            JOptionPane.showMessageDialog(null, e1);
        }
    } 

	private void mostraListaClassiIscrizione(JList<UtenteDTO> studentList) {
		ArrayList<ClasseDTO> listaClassi=new ArrayList<>();
		try {
			listaClassi = controller_d.getListaClassi();
			DefaultListModel<ClasseDTO> model_1=new DefaultListModel<>();
			for (ClasseDTO c: listaClassi) {
				model_1.addElement(c);
			}
			JList<ClasseDTO> classList = new JList<>(model_1);
			setVisible(true);
			JScrollPane scrollPane = new JScrollPane(classList);
			classListPanel.removeAll();
			classListPanel.add(scrollPane);
			
			JButton btnHome=new JButton("Torna alla home");
			classListPanel.add(btnHome);
			
			btnHome.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cardLayout.show(cardPanel, "start");
					classList.removeAll();
				}
			});
			cardLayout.show(cardPanel,"Lista classi");
			classList.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					iscriviStudente(e,studentList,classList.getSelectedValue().getCodice());
				}
			});
			} catch (DataBaseException e1) {
			JOptionPane.showMessageDialog(null, e1);
			e1.printStackTrace();
			} catch (NotFoundException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
	}
	
		
		
	/*private void mostraListaStudentiClasse(MouseEvent e, JList<ClasseDTO> classList) {
		if (e.getClickCount()==2) {
			ClasseDTO selezionata=classList.getSelectedValue();
			if(selezionata!=null) {
				String codice_classe=selezionata.getCodice();
				ArrayList<UtenteDTO> listaStudenti=new ArrayList<>();
				try {
					listaStudenti = controller_d.getListaStudentiClasse(codice_classe);
					DefaultListModel<UtenteDTO> model_2=new DefaultListModel<>();
					for (UtenteDTO u: listaStudenti) {
						model_2.addElement(u);
					}
					JList<UtenteDTO> studentList = new JList<>(model_2);
					setVisible(true);
					JScrollPane scrollPane = new JScrollPane(studentList);
					studentListPanel.removeAll();
					studentListPanel.add(scrollPane);
					
					JButton btnHome=new JButton("Torna alla home");
					classListPanel.add(btnHome);
					
					btnHome.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							cardLayout.show(cardPanel, "start");
							classListPanel.removeAll();
						}
					});
					
					
				} catch (DataBaseException e1) {
					JOptionPane.showMessageDialog(null, e1);
				} catch (NotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		}
	}
	*/
	private void mostraListaSoluzioni(MouseEvent e,JList<TaskDTO> taskList,String codiceClasse) {
		if (e.getClickCount()==2) {
			TaskDTO selezionata=taskList.getSelectedValue();
			int idTask=selezionata.getIdTask();
			if(selezionata!=null) {
				ArrayList<SoluzioneDTO> listaSoluzioni=new ArrayList<>();
				try {
					listaSoluzioni=controller_d.getListaSoluzioni(codiceClasse,idTask);
					DefaultListModel<SoluzioneDTO> model_5=new DefaultListModel<>();
					for (SoluzioneDTO s: listaSoluzioni) {
						if(s.getPunteggio()==0)
							model_5.addElement(s);
					}
					JList<SoluzioneDTO> solutionList = new JList<>(model_5);
					setVisible(true);
					JScrollPane scrollPane = new JScrollPane(solutionList);
					solutionListPanel.removeAll();
					solutionListPanel.add(scrollPane);
					
					JButton btnHome=new JButton("Torna alla home");
					solutionListPanel.add(btnHome);
					
					btnHome.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							cardLayout.show(cardPanel, "start");
							solutionListPanel.removeAll();
						}
					});
					
					cardLayout.show(cardPanel, "Lista soluzioni");
					solutionList.addMouseListener(new MouseAdapter() {
					    @Override
					    public void mouseClicked(MouseEvent e) {
					        correggi(e,solutionList,codiceClasse,idTask);
					    }
					});
				} catch (DataBaseException e1) {
					JOptionPane.showMessageDialog(null, e1);
					e1.printStackTrace();
				} catch (NotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1);
					e1.printStackTrace();
				}
			}
		}
	}
	
	private void iscriviStudente(MouseEvent e, JList<UtenteDTO> studentList,String codice_classe) {
		if (e.getClickCount()==2) {
			UtenteDTO selezionata=studentList.getSelectedValue();
			if(selezionata!=null) {
				String email_studente=selezionata.getEmail();
				try {
					controller_d.iscrizioneDaDocente(email_studente,codice_classe);
					JOptionPane.showMessageDialog(null,"Studente iscritto con successo");
				} catch (StudenteGiaIscrittoException | DataBaseException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		}
	}
	
	private void correggi(MouseEvent e, JList<SoluzioneDTO> solutionList,String codiceClasse,int idTask) {
	    if (e.getClickCount() == 2) {
	        SoluzioneDTO selezionata = solutionList.getSelectedValue();
	        if (selezionata != null) {
	        	System.out.println(selezionata.getIdSoluzione());
	            int idSoluzione = selezionata.getIdSoluzione();
	            byte[] contenuto = selezionata.getContenuto();
	            for (ActionListener al : downloadButton.getActionListeners()) {
	                downloadButton.removeActionListener(al);
	            }
	            
	            JButton btnHome=new JButton("Torna alla home");
				assegnaPunteggio.add(btnHome);
				
				btnHome.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cardLayout.show(cardPanel, "start");
					}
				});
	            
	            
	            downloadButton.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent ev) {
	                    try {
	                        File tempFile = File.createTempFile("soluzione_", ".txt");
	                        Files.write(tempFile.toPath(), contenuto);
	                        Desktop.getDesktop().open(tempFile);
	                    } catch (IOException ex) {
	                        JOptionPane.showMessageDialog(null, "Errore durante l'apertura del file.");
	                        ex.printStackTrace();
	                    } catch (UnsupportedOperationException ex) {
	                        JOptionPane.showMessageDialog(null, "Operazione non supportata sul tuo sistema.");
	                        ex.printStackTrace();
	                    }
	                }
	            });
	
	            for (ActionListener al : confermaButton.getActionListeners()) {
	                confermaButton.removeActionListener(al);
	            }
	
	            confermaButton.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent ev) {
	                    try {
	                        int punteggio = Integer.parseInt(textField.getText());
	                        try {
	                        	controller_d.assegnaPunteggio(codiceClasse,idTask,idSoluzione, punteggio);				                            
	                        	JOptionPane.showMessageDialog(null, "Punteggio assegnato!");

	                        	DefaultListModel<SoluzioneDTO> model = (DefaultListModel<SoluzioneDTO>) solutionList.getModel();
	                        	model.removeElement(selezionata);

	                        	if (!model.isEmpty()) {
	                        	    cardLayout.show(cardPanel, "Lista soluzioni"); 
	                        	} else {
	                        	    JOptionPane.showMessageDialog(null, "Hai corretto tutte le soluzioni!");
	                        	    cardLayout.show(cardPanel, "start");
	                        	}

							} catch (PunteggioTroppoAltoException e) {
								JOptionPane.showMessageDialog(null, e);
							}
	                    } catch (NumberFormatException ex) {
	                        JOptionPane.showMessageDialog(null, "Inserisci un numero valido.");
	                    }
	                }
	            });
	            cardLayout.show(cardPanel, "Form punteggio");
	        }
	    }
	}
	
	private void creaTask(MouseEvent e, ClasseDTO selezionata) {
		if(selezionata!=null) {
			String codice_classe=selezionata.getCodice();
			String titolo=titoloField.getText();
			String descrizione =descrizioneField.getText();
			DateModel<?> model=datePicker.getModel();
			int giorno=0;
			int mese=0;
			int anno=0;
			if(model.getValue()!=null) {
				giorno=model.getDay();
				mese=model.getMonth()+1;
				anno=model.getYear();
			}
			LocalDate data_scadenza=LocalDate.of(anno, mese, giorno);
			int punteggioMax=(int)punteggio.getValue();
			try {
				Validation.validateTaskTitle(titolo);
				Validation.validateTaskDescription(descrizione);
				Validation.validateDueDate(data_scadenza);
				Validation.validateMaxScore(punteggio.getValue());
				controller_d.creaTask(titolo, descrizione, data_scadenza, punteggioMax, codice_classe);
				JOptionPane.showMessageDialog(null,"Task creato correttamente");
				cardLayout.show(cardPanel, "start");
			} catch (DataBaseException e1) {
				JOptionPane.showMessageDialog(null, e1);
				e1.printStackTrace();
			} catch(IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
	}
}
