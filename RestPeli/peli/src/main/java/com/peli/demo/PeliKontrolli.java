package com.peli.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class PeliKontrolli {
    private int score = 0;
    private List<Kysymys> kysymykset;
    private List<OikeaVastaus> oikeatVastaukset;
    

    public PeliKontrolli() {
        // Luodaan kysymykset ja vastaukset
        kysymykset = new ArrayList<>();
        kysymykset.add(new Kysymys(0,"Mikä on Suomen pääkaupunki? id : 0"));
        kysymykset.add(new Kysymys(1,"Kuinka monta maanosaa maapallolla on? id : 1"));
        kysymykset.add(new Kysymys(2,"Kuinka monta jalkaa on hämähäkillä? id : 2"));
        kysymykset.add(new Kysymys(3,"Mikä on suurin planeetta aurinkokunnassa? id : 3"));

        oikeatVastaukset = new ArrayList<>();
        oikeatVastaukset.add(new OikeaVastaus(0,"Helsinki"));
        oikeatVastaukset.add(new OikeaVastaus(1,"7"));
        oikeatVastaukset.add(new OikeaVastaus(2,"8"));
        oikeatVastaukset.add(new OikeaVastaus(3,"Jupiter"));

    }

    @GetMapping("/info")
    public String gameInfo() {
        return "Tervetuloa tietovisaan. Hae kysymys GET-metodilla ja vastaa siihen POST-metodilla käyttämällä saaman kysymyksen id numeroa.";
    }

    @GetMapping("/kysymys") /* GET metodilla saat satunnaisesti jonkun neljästä kysymyksestä */
    public Kysymys getRandomQuestion() {
        Random random = new Random();
        int randomIndex = random.nextInt(kysymykset.size());
        return kysymykset.get(randomIndex);
    }

    @PostMapping("/vastaus") /* Post metodilla vastaat saamaasi kysymykseen, käytä JSON muotoa esimerkiksi {
        "kysymysIndex": 0 // saamasi kysymyksen id, jotta peli tietää mihin kysymykseen aijot vastata
        "vastaus": "Helsinki" // kysymyksen vastaus
    } */
    public String submitVastaus(@RequestBody Vastaus vastaus) {
        String playerAnswer = vastaus.getVastaus();
        OikeaVastaus correctVastaus= oikeatVastaukset.get(vastaus.getKysymysIndex());

        if (playerAnswer.equalsIgnoreCase(correctVastaus.getOikeaVastaus())) {
            score++;
            return "Oikein! Uusi pistemäärä: " + score;
        } else {
            return "Väärin. Oikea vastaus oli: " + correctVastaus.getOikeaVastaus();
        }
    }
}

class Kysymys {
    private String kysymys;

    public Kysymys(int id, String kysymys) {
        this.kysymys = kysymys;
    }

    public String getKysymys() {
        return kysymys;
    }
}

class Vastaus {
    private int kysymysIndex;
    private String vastaus;

    public int getKysymysIndex() {
        return kysymysIndex;
    }

    public String getVastaus() {
        return vastaus;
    }
}

class OikeaVastaus {
    private String oikeaVastaus;


    public OikeaVastaus(int id,String oikeaVastaus) {
        this.oikeaVastaus = oikeaVastaus;
    }

    public String getOikeaVastaus() {
        return oikeaVastaus;
    }
}
