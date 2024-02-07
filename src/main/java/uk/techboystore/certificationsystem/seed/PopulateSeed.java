package uk.techboystore.certificationsystem.seed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class PopulateSeed {
    private final JdbcTemplate jdbcTemplate;

    public PopulateSeed(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5434/pg_nlw");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");

        PopulateSeed populateSeed = new PopulateSeed(dataSource);
        populateSeed.run(args);
    }

    public void run(String... args) {
        executeSqlFile("src/main/resources/created.sql");
    }

    private void executeSqlFile(String filePath) {
        try {
            String sqlStript = new String(Files.readAllBytes(Paths.get(filePath)));
            jdbcTemplate.execute(sqlStript);

            System.out.println("Seed realizado com sucesso");
        } catch (IOException error) {
            System.err.println("Algo correu mal. Error: " + error.toString());
        }
    }
}
