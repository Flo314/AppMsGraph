package com.example.appmsgraph.modelcustom;

import java.util.ArrayList;
import java.util.List;

public class VisiteObject {
    private String id;
    private String date;
    private String type;
    private String note;
    private String comment;

    // Pour rappel :
    // Une méthode de classe s'appelle avec : NomClasse.nomMéthode()
    // Alors qu'une méthode d'instance (donc directement liée à une instance de l'objet) s'appelle avec
    // nomInstance.nomMéthode().
    // La différence est de taille puisque la méthode de classe n'a pas besoin d'instance d'objet pour être appelée!

    // Déclaration d'une liste contenant des objets de type CapitalObject (cast de données)
    // Déclarer cette liste en statique me permet de la préserver durant toute la durée de vie de l'application.
    public static List<VisiteObject> visitList;

    // Getter pour ma liste de capitales.
    // Déclaré en static pour pouvoir y accéder n'importe où dans mes activités, on appelle ca une méthode de classe,
    // au lieu d'une méthode d'instance
    // Le but de cette fonction est de retourner une liste contenant des objets CapitalObject. Pour cela, on donne un type
    // bien particulier à notre List grâce au cast de données List<CapitalObject>


    public static List<VisiteObject> getVisitList() {
        return visitList;
    }

    // Fonction d'ajout de capitales.
    // Déclarée en static (méthode de classe et non d'instance) pour pouvoir être appelée n'importe ou dans mes activités
    public static void addVisite(VisiteObject visite) {
        // Je teste d'abord si ma liste n'est pas null
        // SINON JE RISQUE UNE ERREUR DE TYPE NullPointerException (affectation sur un objet inexistant ou dit null)
        if (visitList != null) {
            // Ma liste n'est pas null, je peux ajouter!
            visitList.add( visite);
        } else {
            // Sinon j'instancie ma liste
            visitList = new ArrayList<>();
            // Et j'ajoute ma visite
            visitList.add( visite );
        }
    }

    // Constructeur
    public VisiteObject(String id, String date, String type, String note, String comment) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.note = note;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

