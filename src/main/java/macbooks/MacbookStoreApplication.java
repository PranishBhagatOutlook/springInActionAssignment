package macbooks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import macbooks.Ingredient.Type;
import macbooks.data.IngredientRepository;

@SpringBootApplication
public class MacbookStoreApplication {
//public class MacbookStoreApplication{
  public static void main(String[] args) {
    SpringApplication.run(MacbookStoreApplication.class, args);
  }

  @Bean
  public CommandLineRunner dataLoader(IngredientRepository repo) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        repo.save(new macbooks.Ingredient("13IN", "13.6 inch", Type.SCREEN));
        repo.save(new macbooks.Ingredient("14IN", "14.6 inch", Type.SCREEN));
        repo.save(new macbooks.Ingredient("BACK", "Backlit", Type.KEYBOARD));
        repo.save(new macbooks.Ingredient("NRML", "Normal", Type.KEYBOARD));
        repo.save(new macbooks.Ingredient("WIRD", "Wired", Type.MOUSE));
        repo.save(new macbooks.Ingredient("WRLS", "Wireless", Type.MOUSE));
        repo.save(new macbooks.Ingredient("35WC", "35W charger", Type.CHARGER));
        repo.save(new macbooks.Ingredient("65WC", "65W charger", Type.CHARGER));
        repo.save(new macbooks.Ingredient("08GR", "8 GB", Type.RAM));
        repo.save(new macbooks.Ingredient("16GR", "16 GB", Type.RAM));
      }
    };
  }
  
}
