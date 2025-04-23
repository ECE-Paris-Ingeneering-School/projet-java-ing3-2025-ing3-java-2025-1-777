package model;

public class Client {
    private int idClient;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String role;

    public Client() {}

    public Client(int idClient, String nom, String prenom, String email, String motDePasse, String role) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public int getIdClient() { 
        return idClient; 
    }
    public void setIdClient(int idClient) { 
        this.idClient = idClient; 
    }

    public String getNom() {
        return nom; 
    }
    public void setNom(String nom) { 
        this.nom = nom; 
    }

    public String getPrenom() { 
        return prenom; 
    }
    public void setPrenom(String prenom) { 
        this.prenom = prenom; 
    }

    public String getEmail() { 
        return email;
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getMotDePasse() { 
        return motDePasse; 
    }
    public void setMotDePasse(String motDePasse) { 
        this.motDePasse = motDePasse;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) { 
        this.role = role; 
    }
}
