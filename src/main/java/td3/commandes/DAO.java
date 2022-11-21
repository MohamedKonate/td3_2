package td3.commandes;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.HashSet;

import static td3.commandes.Categorie.*;
import td3.paires.Paire;

public class DAO {
    private List<Commande> commandes;

    private DAO(Commande c1, Commande ...cs) {
        commandes = new ArrayList<>();
        commandes.add(c1);
        commandes.addAll(List.of(cs));
    }

    public static DAO instance = null;

    public static final DAO instance() {
        if (instance == null) {
            Produit camembert = new Produit("Camembert", 4.0, NORMAL);
            Produit yaourts = new Produit("Yaourts", 2.5, INTERMEDIAIRE);
            Produit masques = new Produit("Masques", 25.0, REDUIT);
            Produit gel = new Produit("Gel", 5.0, REDUIT);
            Produit tournevis = new Produit("Tournevis", 4.5, NORMAL);
            //
            Commande c1 = new Commande()
                    .ajouter(camembert, 1)
                    .ajouter(yaourts, 6);
            Commande c2 = new Commande()
                    .ajouter(masques, 2)
                    .ajouter(gel, 10)
                    .ajouter(camembert, 2)
                    .ajouter(masques, 3);
            //
            instance = new DAO(c1,c2);
        }
        return instance;
    }

    public List<Commande> commandes() { return commandes; }

    public Set<Produit> produits() {
        Set<Produit> rtr = new HashSet<>();
        for(Commande c : commandes) {
            for(Paire<Produit, Integer> p : c.lignes()) {
                rtr.add(p.fst()); // on utilise la propriété des ensembles
            }
        }
        return rtr;
    }

    public List<Commande> selectionCommande(Predicate<Commande> p) {
        List<Commande> rtr = new ArrayList<>();
        for(Commande c: commandes) {
            if(p.test(c)) {
                rtr.add(c);
            }
        }
        return rtr;
    }

    public List<Commande> selectionCommandeSurExistanceLigne(Predicate<Paire<Produit,Integer>> p) {
        List<Commande> rtr = new ArrayList<>();
        for(Commande c: commandes) {
            for (Paire<Produit, Integer> ligne : c.lignes()) {
                if (p.test(ligne)) {
                    rtr.add(c);
                    break;
                }
            }
        }
        return rtr;
    }

    public Set<Produit> selectionProduits(Predicate<Produit> p) {
        Set<Produit> rtr = new HashSet<>();
        for (Produit x : produits()) {
            if (p.test(x)) {
                rtr.add(x);
            }
        }
        return rtr;
    }

}
