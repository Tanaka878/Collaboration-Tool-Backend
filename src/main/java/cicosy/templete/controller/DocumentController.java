package cicosy.templete.controller;

import cicosy.templete.domain.DocumentData;
import cicosy.templete.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin("*")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("email") String email,
            @RequestParam("document") MultipartFile file
    ) {
        System.out.println("uploadDocument start ----------");
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
        }

        try {
            DocumentData saved = documentService.saveDocument(email, file);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload file.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllDocuments() {
        try {
            List<DocumentData> documents = documentService.getAllDocuments();
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to fetch documents.");
        }
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadDocument(@PathVariable String id) {
        try {
            DocumentData doc = documentService.getDocumentById(id);
            if (doc == null) {
                return ResponseEntity.notFound().build();
            }

            // Read file from disk using the stored path
            Path path = Paths.get(doc.getFilePath());
            byte[] fileBytes = Files.readAllBytes(path);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + doc.getOriginalFileName() + "\"")
                    .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
                    .body(new org.springframework.core.io.ByteArrayResource(fileBytes));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to download document.");
        }
    }


}
