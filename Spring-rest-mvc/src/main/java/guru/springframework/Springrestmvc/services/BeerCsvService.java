package guru.springframework.Springrestmvc.services;

import guru.springframework.Springrestmvc.model.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface BeerCsvService {

    List<BeerCSVRecord> convertCSV(File csvFile) throws FileNotFoundException;
}
