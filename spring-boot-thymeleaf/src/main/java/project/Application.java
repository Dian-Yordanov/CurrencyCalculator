package project;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;


@SpringBootApplication
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    public static void downloadInitializer() throws MalformedURLException, IOException{
        String sourceUrl = "https://www.bnb.bg/Statistics/StExternalSector/StExchangeRates/StERForeignCurrencies/index.htm?download=xml&search=&lang=BG";
        String targetFilename = "src\\main\\resources\\downloads\\Exchange_Rates.xml";

        File f = new File(targetFilename);
        if(!f.exists() && !f.isDirectory()) {
            long bytesDownloaded = download(sourceUrl, targetFilename);

        String message = String.format("Downloaded %d bytes from %s to %s.", bytesDownloaded, sourceUrl, targetFilename);
        System.out.println(message);
        }

    }

    // package scope to allow test class to access
    static long download(String sourceUrl, String targetFileName) throws MalformedURLException, IOException {
        try (InputStream in = URI.create(sourceUrl).toURL().openStream()) {
            return Files.copy(in, Paths.get(targetFileName));
        }
    }
}
