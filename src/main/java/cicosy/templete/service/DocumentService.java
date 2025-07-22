package cicosy.templete.service;

import cicosy.templete.domain.DocumentData;
import cicosy.templete.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class DocumentService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private DocumentRepository documentRepository;

    public DocumentData saveDocument(String email, MultipartFile file) throws IOException {
        // Clean the file name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Create upload directory if it doesn't exist
        Files.createDirectories(Paths.get(UPLOAD_DIR));

        // Define unique file path
        String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;
        Path filePath = Paths.get(UPLOAD_DIR + uniqueFileName);

        // Save file to disk
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Save metadata to MongoDB
        DocumentData docData = new DocumentData(email, originalFileName, filePath.toString());
        return documentRepository.save(docData);
    }
    public List<DocumentData> getAllDocuments() {
        return documentRepository.findAll();
    }

    public DocumentData getDocumentById(String id) {
        return documentRepository.findById(id).orElse(null);
    }


}
