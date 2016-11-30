package fr.univlyon1.sem.model.csv;

import java.util.ArrayList;
import java.util.List;

public class CSVData {
    protected final List<String> nomColones;
    protected List<List<String>> lignes = new ArrayList<>();
    protected List<String> firstLine = new ArrayList<>();

    public CSVData(List<String> nomColones){
        this.nomColones=nomColones;
    }

    public boolean ajouterLigne(List<String> ligne){
        if(ligne.size()==nomColones.size()){
            lignes.add(ligne);
            return true;
        }return false;
    }

    public void setLignes(List<List<String>> lignes) {
        this.lignes = lignes;
    }

    public List<List<String>> getLignes() {
        return lignes;
    }

    public List<String> getNomColones() {
        return nomColones;
    }

    public void setFirstLine(List<String> firstLine) {
        this.firstLine = firstLine;
    }
}
