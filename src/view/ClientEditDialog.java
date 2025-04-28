package view;

import DAO.UtilisateurDAO;
import model.Utilisateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClientEditDialog extends JDialog {
    private final UtilisateurDAO userDao;
    private final Utilisateur client; // null = création

    private JTextField nomF, prenomF, emailF, passF;
    private JComboBox<String> roleC;

    public ClientEditDialog(Window owner,
                            UtilisateurDAO userDao,
                            Utilisateur client) {
        super(owner,
                client == null ? "Ajouter un client" : "Modifier un client",
                ModalityType.APPLICATION_MODAL);
        this.userDao = userDao;
        this.client = client;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(5,2,5,5));
        form.setBorder(new EmptyBorder(10,10,10,10));

        form.add(new JLabel("Nom :"));
        nomF = new JTextField(20);
        form.add(nomF);

        form.add(new JLabel("Prénom :"));
        prenomF = new JTextField(20);
        form.add(prenomF);

        form.add(new JLabel("Email :"));
        emailF = new JTextField(20);
        form.add(emailF);

        form.add(new JLabel("Mot de passe :"));
        passF = new JTextField(20);
        form.add(passF);

        form.add(new JLabel("Rôle :"));
        roleC = new JComboBox<>(new String[]{"client","admin"});
        form.add(roleC);

        add(form, BorderLayout.CENTER);

        // Pré-remplir si édition
        if (client != null) {
            nomF.setText(client.getNom());
            prenomF.setText(client.getPrenom());
            emailF.setText(client.getEmail());
            passF.setText(client.getMotDePasse());
            roleC.setSelectedItem(client.getRole());
        }

        JButton saveB = new JButton("Enregistrer");
        saveB.addActionListener(e -> onSave());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(saveB);
        add(south, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getOwner());
    }

    private void onSave() {
        String nom    = nomF.getText().trim();
        String prenom = prenomF.getText().trim();
        String email  = emailF.getText().trim();
        String pass   = passF.getText().trim();
        String role   = (String) roleC.getSelectedItem();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires");
            return;
        }

        if (client == null) {
            Utilisateur u = new Utilisateur();
            u.setNom(nom);
            u.setPrenom(prenom);
            u.setEmail(email);
            u.setMotDePasse(pass);
            u.setRole(role);
            userDao.insert(u);
        } else {
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setEmail(email);
            client.setMotDePasse(pass);
            client.setRole(role);
            userDao.update(client);
        }
        dispose();
    }
}