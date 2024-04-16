package AplicatieManagerStoc;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ManagerStoc managerStoc;
	private JLabel counterLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setTitle("ManagerStoc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		managerStoc = new ManagerStoc();
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(new Color(0, 191, 255));
		contentPane.add(titlePanel, BorderLayout.NORTH);
		titlePanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel titleLabel = new JLabel("MANAGER STOC");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		titlePanel.add(titleLabel);
	    
	    JPanel sidePanel = new JPanel();
	    sidePanel.setBackground(new Color(0, 191, 255));
	    titlePanel.add(sidePanel);
	    sidePanel.setLayout(new BorderLayout(0, 0));
	    
	    counterLabel = new JLabel("Nr. Articole: 0");
		counterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		counterLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
	    sidePanel.add(counterLabel);
	    
	    JButton furnizoriButton = new JButton("Furnizori");
	    furnizoriButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		afiseazaFurnizori(managerStoc.getTotiFurnizorii());
	    	}
	    });
	    furnizoriButton.setFont(new Font("Tahoma", Font.BOLD, 20));
	    sidePanel.add(furnizoriButton, BorderLayout.EAST);
		
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton addButton = new JButton("Adauga Articol");
		addButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Utilizează JOptionPane pentru a colecta datele de la utilizator
		        String denumire = JOptionPane.showInputDialog("Introduceti denumirea articolului:");
		        String categorie = JOptionPane.showInputDialog("Introduceti categoria articolului:");
		        int cantitate = Integer.parseInt(JOptionPane.showInputDialog("Introduceti cantitatea articolului:"));

		        // Creează un nou articol cu datele introduse de utilizator
		        Articol nou = new Articol(denumire, categorie, cantitate);

		        // Adaugă articolul în stoc și actualizează panoul de vizualizare
		        managerStoc.adaugaArticol(nou);
		        afiseazaArticole(managerStoc.afiseazaToateArticolele());
			}
		});
		buttonPanel.add(addButton);
		
		JButton updateButton = new JButton("Actualizeaza Articol");
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cod pentru actualizarea cantității unui articol
                // Poți utiliza un JDialog pentru a colecta datele de la utilizator
                // După actualizare, actualizează panoul de vizualizare cu noile date
				 String denumire = JOptionPane.showInputDialog("Introduceti denumirea articolului:");
	             int cantitateNoua = Integer.parseInt(JOptionPane.showInputDialog("Introduceti noua cantitate:"));
	             managerStoc.actualizeazaCantitate(denumire, cantitateNoua);
	             afiseazaArticole(managerStoc.afiseazaToateArticolele());
			}
		});
		buttonPanel.add(updateButton);
		
		JButton deleteButton = new JButton("Sterge Articol");
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cod pentru ștergerea unui articol
                // Poți utiliza un JDialog pentru a colecta datele de la utilizator
                // După ștergere, actualizează panoul de vizualizare cu noile date
                String denumire = JOptionPane.showInputDialog("Introduceti denumirea articolului:");
                managerStoc.stergeArticol(denumire);
                afiseazaArticole(managerStoc.afiseazaToateArticolele());
			}
		});
		buttonPanel.add(deleteButton);
		
		JButton searchButton = new JButton("Cauta Articol");
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cod pentru căutarea unui articol
                // Poți utiliza un JDialog pentru a colecta datele de la utilizator
                // După căutare, actualizează panoul de vizualizare cu rezultatele
                String criteriu = JOptionPane.showInputDialog("Introduceti criteriul de cautare:");
                afiseazaArticole(managerStoc.cauta(criteriu));
			}
		});
		buttonPanel.add(searchButton);
		
		JButton showAllButton = new JButton("Afiseaza toate articolele");
		showAllButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cod pentru afișarea tuturor articolelor
                afiseazaArticole(managerStoc.afiseazaToateArticolele());
			}
		});
		buttonPanel.add(showAllButton);
		
		JButton lowQuantityButton = new JButton("Cauta Cantitate Scazuta");
		lowQuantityButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		lowQuantityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cod pentru afișarea articolelor cu cantitate scăzută
                int limita = Integer.parseInt(JOptionPane.showInputDialog("Introduceti limita de cantitate:"));
                afiseazaArticole(managerStoc.afiseazaArticoleCuCantitateScazuta(limita));
			}
		});
		buttonPanel.add(lowQuantityButton);
		
		afiseazaArticole(managerStoc.afiseazaToateArticolele());
	}
	
	private void afiseazaArticole(ArrayList<Articol> articole) {
	    // Curăță panoul de vizualizare
	    //contentPane.remove(1); // Șterge panoul existent
	    if (2 < contentPane.getComponentCount()) {
	        contentPane.remove(2);
	    }

	    JPanel viewPanel = new JPanel(new GridBagLayout());
	    JScrollPane scrollPane = new JScrollPane(viewPanel);
	    contentPane.add(scrollPane, BorderLayout.CENTER);

	    // Adaugă articolele în panou
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;

	    for (Articol articol : articole) {
	        JLabel denumireLabel = new JLabel("Denumire: " + articol.getDenumire());
	        JLabel categorieLabel = new JLabel("Categorie: " + articol.getCategorie());
	        JLabel cantitateLabel = new JLabel("Cantitate: " + articol.getCantitate());

	        // Stabilește fontul pentru fiecare etichetă
	        Font font = new Font("Tahoma", Font.BOLD, 16);
	        denumireLabel.setFont(font);
	        categorieLabel.setFont(font);
	        cantitateLabel.setFont(font);

	        // Adaugă etichetele în panou
	        gbc.gridy++;
	        viewPanel.add(denumireLabel, gbc);
	        gbc.gridy++;
	        viewPanel.add(categorieLabel, gbc);
	        gbc.gridy++;
	        viewPanel.add(cantitateLabel, gbc);

	        // Adaugă un spațiu între elementele dintr-o coloană
	        gbc.gridy++;
	        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
	    }
	    
	    counterLabel.setText("Nr. Articole: " + articole.size());
	    // Reafișează panoul
	    contentPane.revalidate();
	    contentPane.repaint();
	}
	
	private void afiseazaFurnizori(ArrayList<Furnizor> furnizori) {
	    // Curăță panoul de vizualizare
	    if (2 < contentPane.getComponentCount()) {
	        contentPane.remove(2);
	    }

	    JPanel viewPanel = new JPanel(new GridBagLayout());
	    JScrollPane scrollPane = new JScrollPane(viewPanel);
	    contentPane.add(scrollPane, BorderLayout.CENTER);

	    // Adaugă furnizorii în panou
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;

	    for (Furnizor furnizor : furnizori) {
	        JLabel numeLabel = new JLabel("Nume Furnizor: " + furnizor.getNumeFurnizor());
	        JLabel adresaLabel = new JLabel("Adresa: " + furnizor.getAdresa());
	        JLabel telefonLabel = new JLabel("Telefon: " + furnizor.getTelefon());
	        JLabel emailLabel = new JLabel("Email: " + furnizor.getEmail());

	        Font font = new Font("Tahoma", Font.BOLD, 16);
	        numeLabel.setFont(font);
	        adresaLabel.setFont(font);
	        telefonLabel.setFont(font);
	        emailLabel.setFont(font);

	        gbc.gridy++;
	        viewPanel.add(numeLabel, gbc);
	        gbc.gridy++;
	        viewPanel.add(adresaLabel, gbc);
	        gbc.gridy++;
	        viewPanel.add(telefonLabel, gbc);
	        gbc.gridy++;
	        viewPanel.add(emailLabel, gbc);

	        gbc.gridy++;
	        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
	    }

	    counterLabel.setText("Nr. Furnizori: " + furnizori.size());
	    contentPane.revalidate();
	    contentPane.repaint();
	}
}
