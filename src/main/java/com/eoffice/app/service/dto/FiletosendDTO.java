package com.eoffice.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Filetosend entity.
 */
public class FiletosendDTO implements Serializable {

    private UUID id;

    private String filename;

    private String subject;

    private ByteBuffer filetoupload;
    private String filetouploadContentType;

    private String recipientemailid;

    private String recipientname;

    private String addsigner;

    private String addcc;

    private String addbulk;

    private String currentlocation;

    private String destinationlocation;

    private LocalDate fileinitiatedon;

    private LocalDate fileclosedon;

    private String dispatchno;

    @NotNull
    private Boolean status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public ByteBuffer getFiletoupload() {
        return filetoupload;
    }

    public void setFiletoupload(ByteBuffer filetoupload) {
        this.filetoupload = filetoupload;
    }

    public String getFiletouploadContentType() {
        return filetouploadContentType;
    }

    public void setFiletouploadContentType(String filetouploadContentType) {
        this.filetouploadContentType = filetouploadContentType;
    }
    public String getRecipientemailid() {
        return recipientemailid;
    }

    public void setRecipientemailid(String recipientemailid) {
        this.recipientemailid = recipientemailid;
    }
    public String getRecipientname() {
        return recipientname;
    }

    public void setRecipientname(String recipientname) {
        this.recipientname = recipientname;
    }
    public String getAddsigner() {
        return addsigner;
    }

    public void setAddsigner(String addsigner) {
        this.addsigner = addsigner;
    }
    public String getAddcc() {
        return addcc;
    }

    public void setAddcc(String addcc) {
        this.addcc = addcc;
    }
    public String getAddbulk() {
        return addbulk;
    }

    public void setAddbulk(String addbulk) {
        this.addbulk = addbulk;
    }
    public String getCurrentlocation() {
        return currentlocation;
    }

    public void setCurrentlocation(String currentlocation) {
        this.currentlocation = currentlocation;
    }
    public String getDestinationlocation() {
        return destinationlocation;
    }

    public void setDestinationlocation(String destinationlocation) {
        this.destinationlocation = destinationlocation;
    }
    public LocalDate getFileinitiatedon() {
        return fileinitiatedon;
    }

    public void setFileinitiatedon(LocalDate fileinitiatedon) {
        this.fileinitiatedon = fileinitiatedon;
    }
    public LocalDate getFileclosedon() {
        return fileclosedon;
    }

    public void setFileclosedon(LocalDate fileclosedon) {
        this.fileclosedon = fileclosedon;
    }
    public String getDispatchno() {
        return dispatchno;
    }

    public void setDispatchno(String dispatchno) {
        this.dispatchno = dispatchno;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FiletosendDTO filetosendDTO = (FiletosendDTO) o;

        if ( ! Objects.equals(id, filetosendDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FiletosendDTO{" +
            "id=" + id +
            ", filename='" + filename + "'" +
            ", subject='" + subject + "'" +
            ", filetoupload='" + filetoupload + "'" +
            ", recipientemailid='" + recipientemailid + "'" +
            ", recipientname='" + recipientname + "'" +
            ", addsigner='" + addsigner + "'" +
            ", addcc='" + addcc + "'" +
            ", addbulk='" + addbulk + "'" +
            ", currentlocation='" + currentlocation + "'" +
            ", destinationlocation='" + destinationlocation + "'" +
            ", fileinitiatedon='" + fileinitiatedon + "'" +
            ", fileclosedon='" + fileclosedon + "'" +
            ", dispatchno='" + dispatchno + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
