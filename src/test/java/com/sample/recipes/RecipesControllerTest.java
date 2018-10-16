package com.sample.recipes;

import com.sample.recipes.controllers.RecipesController;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(RecipesController.class)
public class RecipesControllerTest {
}
