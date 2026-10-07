package tn.esprit.autoloc.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initVehicules(VehiculeRepository vehiculeRepository) {
        return args -> {

            if (vehiculeRepository.count() == 0) {

                Vehicule v1 = new Vehicule();
                v1.setImmatriculation("TU-1234");
                v1.setMarque("Peugeot");
                v1.setModele("208");
                v1.setCategorie(CategorieVehicule.CITADINE);
                v1.setTarifJournalier(new BigDecimal("120.00"));
                v1.setStatut(StatutVehicule.DISPONIBLE);

                Vehicule v2 = new Vehicule();
                v2.setImmatriculation("TU-5678");
                v2.setMarque("Renault");
                v2.setModele("Clio");
                v2.setCategorie(CategorieVehicule.CITADINE);
                v2.setTarifJournalier(new BigDecimal("100.00"));
                v2.setStatut(StatutVehicule.DISPONIBLE);

                vehiculeRepository.save(v1);
                vehiculeRepository.save(v2);

                System.out.println("Véhicules de démonstration ajoutés.");
            }
        };
    }
}