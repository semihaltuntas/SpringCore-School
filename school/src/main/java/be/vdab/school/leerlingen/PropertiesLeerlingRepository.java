package be.vdab.school.leerlingen;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
@Component
@Qualifier("PRO")
public class PropertiesLeerlingRepository implements LeerlingRepository {
    @Override
    public List<Leerling> findAll() {
        try (var stream = Files.lines(Path.of("/data/leerlingen.properties"))) {
            return stream
                    .map(regel -> regel.split("[:,]"))
                    .map(onderdelen -> new Leerling(
                            Long.parseLong(onderdelen[0]),
                            onderdelen[1],
                            onderdelen[2]))
                    .toList();
        } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            throw new IllegalArgumentException("Leerlingenbestand bevat verkeerde data", ex);
        }
    }
}
