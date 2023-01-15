package pro.sky.transferfiles.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    private String name;
    private int cookingTime;
    private String unitTime;
    private List<Ingredient> ingredients;
    private List<String> steps;


}
