package csye6225.cloud.noteapp.service;

import csye6225.cloud.noteapp.controller.AttachmentController;
import csye6225.cloud.noteapp.exception.AppException;
import csye6225.cloud.noteapp.model.Attachment;
import csye6225.cloud.noteapp.model.Notes;
import csye6225.cloud.noteapp.repository.AttachmentRepository;
import csye6225.cloud.noteapp.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
public class AttachmentService {

    private static String local_storage = "/home/keyur/Documents/temp";

    @Autowired
    private AttachmentRepository ar;

    @Autowired
    private NotesService notesService;

    @Autowired
    private Environment environment;

    @Autowired
    private NotesRepository noteRepository;

    public String createAttachment(MultipartFile file, String name, String noteid) throws AppException {
        try {
            Notes nt = notesService.findNotesById(noteid);
            if (nt != null && nt.getUser_id().equalsIgnoreCase(name)) {
                Attachment attachment = new Attachment();
                UUID uuid = UUID.randomUUID();

                String mimeType = file.getContentType();
                String type = mimeType.split("/")[1];

                byte[] bytes = file.getBytes();
                Path path = Paths.get(local_storage + "/"  + uuid.toString() + "-" + file.getOriginalFilename());
                Files.write(path, bytes);
                attachment.setAttachment_id(uuid.toString());
                attachment.setPath(path.toString());
                nt.getAttachments().add(attachment);
                noteRepository.save(nt);
                return "Success";
            } else {
                return null;
            }
        }
        catch (IOException exc){
            throw new AppException("IOException");
        }
    }

    public String updateAttachment(MultipartFile file, Notes note, String attachmentid) throws AppException {
        try {
            for (Attachment a:note.getAttachments()) {
                if(a.getAttachment_id().equalsIgnoreCase(attachmentid)) {
                    String mimeType = file.getContentType();
                    File oldfile = new File(a.getPath());
                    if (oldfile.exists()) {
                        oldfile.delete();
                    }

                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(local_storage + "/" + a.getAttachment_id() + "-" + file.getOriginalFilename());
                    Files.write(path, bytes);
                    a.setPath(path.toString());
                    noteRepository.save(note);
                    return "Success";
                }
            }
            return null;
        }
        catch (IOException exc){
            throw new AppException("IOException");
        }
    }

    public int deleteAttachment(Notes note, String attachmentid) {
        Attachment attach = null;
        for (Attachment a:note.getAttachments()) {
            if(a.getAttachment_id().equalsIgnoreCase(attachmentid)){
                File oldfile = new File(a.getPath());
                if (oldfile.exists()) {
                    oldfile.delete();
                }
                attach = a;

            }
        }
        if(attach != null){
            note.getAttachments().remove(attach);
            noteRepository.save(note);
            return 1;
        }
        return 0;
    }
}
