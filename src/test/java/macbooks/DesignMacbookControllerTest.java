package macbooks;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import macbooks.Ingredient.Type;
import macbooks.data.IngredientRepository;
import macbooks.data.OrderRepository;
import macbooks.web.DesignMacbookController;

@RunWith(SpringRunner.class)
@WebMvcTest(DesignMacbookController.class)
public class DesignMacbookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private List<Ingredient> ingredients;

  private Macbook design;

  @MockBean
  private IngredientRepository ingredientRepository;

  @MockBean
  private MacbookRepository designRepository;

  @MockBean
  private OrderRepository orderRepository;

  @Before
  public void setup() {
    ingredients = Arrays.asList(
      new Ingredient("13IN", "13.6 inch", Type.SCREEN),
      new Ingredient("14IN", "14.6 inch", Type.SCREEN),
      new Ingredient("BACK", "Backlit", Type.KEYBOARD),
      new Ingredient("NRML", "Normal", Type.KEYBOARD),
      new Ingredient("WIRD", "Wired", Type.MOUSE),
      new Ingredient("WRLS", "Wireless", Type.MOUSE),
      new Ingredient("35WC", "35W charger", Type.CHARGER),
      new Ingredient("65WC", "65W charger", Type.CHARGER),
      new Ingredient("08GR", "8 GB", Type.RAM),
      new Ingredient("16GR", "16 USB", Type.RAM)
    );

    when(ingredientRepository.findAll())
        .thenReturn(ingredients);

    when(ingredientRepository.findById("13IN")).thenReturn(Optional.of(new Ingredient("13IN", "13.6 inch", Type.SCREEN)));
    when(ingredientRepository.findById("BACK")).thenReturn(Optional.of(new Ingredient("BACK", "Backlit", Type.KEYBOARD)));
    when(ingredientRepository.findById("35WC")).thenReturn(Optional.of(new Ingredient("35WC", "35W charger", Type.CHARGER)));

    design = new Macbook();
    design.setName("Test Macbook");

    design.setIngredients(Arrays.asList(
        new Ingredient("13IN", "13.6 INCH", Type.SCREEN),
        new Ingredient("BACK", "Backlit", Type.KEYBOARD),
        new Ingredient("35WC", "35W charger", Type.CHARGER)
  ));

  }

  @Test
  public void testShowDesignForm() throws Exception {
    mockMvc.perform(get("/design"))
        .andExpect(status().isOk())
        .andExpect(view().name("design"))
        .andExpect(model().attribute("screen", ingredients.subList(0, 2)))
        .andExpect(model().attribute("keyboard", ingredients.subList(2, 4)))
        .andExpect(model().attribute("mouse", ingredients.subList(4, 6)))
        .andExpect(model().attribute("charger", ingredients.subList(6, 8)))
        .andExpect(model().attribute("ram", ingredients.subList(8, 10)));
  }

  @Test
  public void processDesign() throws Exception {
    when(designRepository.save(design))
        .thenReturn(design);

    mockMvc.perform(post("/design")
        .content("name=Test+Macbook&ingredients=13IN,BACK,WIRD")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().stringValues("Location", "/orders/current"));
  }

}
