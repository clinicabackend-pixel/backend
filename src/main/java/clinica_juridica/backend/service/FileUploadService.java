package clinica_juridica.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileUploadService {

    private final Cloudinary cloudinary;

    public FileUploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> uploadUniversalFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío.");
        }

        Map<String, Object> options = ObjectUtils.asMap(
                "resource_type", "auto",
                "folder", "casos_documentos");

        if (isRawFile(file)) {
            // For non-image/video files, preserving the extension is critical for download.
            // Since we upload bytes, Cloudinary doesn't know the filename, so
            // 'use_filename' fails.
            // We set public_id manually, appending timestamp for uniqueness.
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null) {
                String uniqueName = System.currentTimeMillis() + "_" + originalFilename;
                options.put("public_id", uniqueName);
            }
        }

        return cloudinary.uploader().upload(file.getBytes(), options);
    }

    private boolean isRawFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null)
            return true;
        return !contentType.startsWith("image/") && !contentType.startsWith("video/");
    }
}
