package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;


import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

public class VerkkokauppaTest {
    Pankki pankki;
    Varasto varasto;
    Viitegeneraattori viite;
    Kauppa kauppa;
    @Before
    public void setUp() {
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);

        varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(1);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "juusto", 10));
        when(varasto.saldo(3)).thenReturn(0);
        when(varasto.haeTuote(3)).thenReturn(new Tuote(3, "kahvi", 30));
        kauppa = new Kauppa(varasto, pankki, viite);
    }

    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaan() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), anyInt());
    }
    @Test public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaArvoilla() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("jyrki", "12345");

        verify(pankki).tilisiirto(eq("jyrki"), eq(42), eq("12345"), eq("33333-44455"), eq(5));
    }

    @Test
    public void tilisiirtoaKutsutaanOikenJosOstettuKaksiEriTuotetta() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("jyrki", "12345");
    
        verify(pankki).tilisiirto(eq("jyrki"), eq(42), eq("12345"), eq("33333-44455"), eq(15));
    }
    @Test
    public void tilisiirtoaKutsutaanOikeinJosOstetaanKaksiSamaaTuotetta() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("jyrki", "12345");

        verify(pankki).tilisiirto(eq("jyrki"), eq(42), eq("12345"), eq("33333-44455"), eq(10));

    }

    @Test
    public void tilisiirtoaKutsutaanOikeinJosYksiKoriinLisatyistaTuotteistaOnLoppu() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(3);
        kauppa.tilimaksu("jyrki", "12345");

        verify(pankki).tilisiirto(eq("jyrki"), eq(42), eq("12345"), eq("33333-44455"), eq(5));
    }

    @Test
    public void edellisenOstoksenHintaEiNayUudenOstoksenHinnassa() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("jyrki", "12345");
        
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(10));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("jyrki", "12345");

        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(5));
    }
    @Test
    public void jokaiselleMaksullePyydetaanUusiViitenro() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        verify(viite, times(1)).uusi();
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        verify(viite, times(2)).uusi();

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        verify(viite, times(3)).uusi();
    }
    @Test
    public void poistaKoristaPalauttaaTuotteenVarastoon() {
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.poistaKorista(1);
        kauppa.tilimaksu("simo", "15555");
        Tuote testiTuote = varasto.haeTuote(1);

        verify(varasto, times(1)).palautaVarastoon(testiTuote);
    }
}