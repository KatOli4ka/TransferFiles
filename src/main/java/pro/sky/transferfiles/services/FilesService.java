package pro.sky.datafiles.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesService {
    @Value("${path.to.data.file.ingredients}")
    private String dataFileIngredientsPath;
    @Value("${name.of.data.file.ingredients}")
    private String dataFileIngredientsName;
    @Value("${path.to.data.file.recipes}")
    private String dataFileRecipesPath;
    @Value("${name.of.data.file.recipes}")
    private String dataFileRecipesName;

    public boolean saveToFileIngredients(String json) {
        try {
            cleanDataFileIngredients();
            Files.writeString(Path.of(dataFileIngredientsPath, dataFileIngredientsName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean saveToFileRecipes(String json) {
        try {
            cleanDataFileRecipes();
            Files.writeString(Path.of(dataFileRecipesPath, dataFileRecipesName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String readFromFileIngredients() {
        try {
            return Files.readString(Path.of(dataFileIngredientsPath, dataFileIngredientsName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readFromFileRecipes() {
        try {
            return Files.readString(Path.of(dataFileRecipesPath, dataFileRecipesName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean cleanDataFileIngredients() {
        try {
            Path path = Path.of(dataFileIngredientsPath, dataFileIngredientsName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean cleanDataFileRecipes() {
        try {
            Path path = Path.of(dataFileRecipesPath, dataFileRecipesName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
