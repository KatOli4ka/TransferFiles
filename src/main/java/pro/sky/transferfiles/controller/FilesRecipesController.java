package pro.sky.transferfiles.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.transferfiles.services.FilesService;

import java.io.*;

@Tag(name = "FilesRecipesController", description = "API для файла с рецептами")
@RestController
@RequestMapping("/files")
public class FilesRecipesController {
    private final FilesService filesService;

    public FilesRecipesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @Operation(summary = "downloadRecipesFile", description = "Скачивание файла с рецептами")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Скачивание прошло успешно!"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры!")
    })
    @GetMapping("/export/recipes")
    public ResponseEntity<InputStreamResource> downloadRecipesFile() throws FileNotFoundException {
        File recipesFile = filesService.getRecipesFile();
        if (recipesFile.exists()) {
            InputStreamResource resourceR = new InputStreamResource(new FileInputStream(recipesFile));
            return ResponseEntity.ok().
                    contentType(MediaType.APPLICATION_JSON).
                    contentLength(recipesFile.length()).
                    header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesLog.json\"").
                    body(resourceR);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "uploadRecipesFile", description = "Загрузка файла с рецептами")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Загрузка прошла успешно!"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры рецепта!")
    })
    @PostMapping(value = "/import/recipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipesFile(@RequestParam MultipartFile fileRecipes) {
        filesService.cleanDataFileRecipes();
        File fileR = filesService.getRecipesFile();
        try (FileOutputStream fos = new FileOutputStream(fileR)) {
            IOUtils.copy(fileRecipes.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
