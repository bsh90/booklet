package library.booklet;

import library.booklet.entity.DiaryPageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import library.booklet.repository.DiaryPageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

//@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

//    @Bean
//    CommandLineRunner initDatabase(DiaryPageRepository repository) {
//
//        return args -> {
//            log.info("Preloading " + repository.save(new DiaryPageEntity(LocalDate.of(2024, 9, 1), "entry example 1")));
//            log.info("Preloading " + repository.save(new DiaryPageEntity(LocalDate.of(2024, 9, 1), "entry example 2")));
//        };
//    }
}
