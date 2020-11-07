package ohtu.verkkokauppa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Kauppa {
    @Autowired
    private Halli halli;
    @Autowired
    private rahanKasittelyLaitos rahaLaitos;
    private Ostoskori ostoskori;
    @Autowired
    private Generaattori generaattori;
    private String kaupanTili;

    public Kauppa(Halli halli, rahanKasittelyLaitos laitos, Generaattori generaattori) {
        this.halli = halli;
        this.rahaLaitos = laitos;
        this.generaattori = generaattori;
        kaupanTili = "33333-44455";
    }

    public void aloitaAsiointi() {
        ostoskori = new Ostoskori();
    }

    public void poistaKorista(int id) {
        Tuote t = halli.haeTuote(id); 
        halli.palautaVarastoon(t);
    }

    public void lisaaKoriin(int id) {
        if (halli.saldo(id)>0) {
            Tuote t = halli.haeTuote(id);             
            ostoskori.lisaa(t);
            halli.otaVarastosta(t);
        }
    }

    public boolean tilimaksu(String nimi, String tiliNumero) {
        int viite = generaattori.uusi();
        int summa = ostoskori.hinta();
        
        return rahaLaitos.tilisiirto(nimi, viite, tiliNumero, kaupanTili, summa);
    }

}
