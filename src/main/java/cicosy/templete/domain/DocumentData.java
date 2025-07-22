package cicosy.templete.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "documents")
public class DocumentData {
    @Id
    private String id;

    private String userEmail;
    private String originalFileName;
    private String filePath;
    private Date timestamp;

    public DocumentData() {
        this.timestamp = new Date();
    }

    public DocumentData(String userEmail, String originalFileName, String filePath) {
        this.userEmail = userEmail;
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.timestamp = new Date();
    }
}
