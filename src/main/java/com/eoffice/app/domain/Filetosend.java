package com.eoffice.app.domain;

import com.datastax.driver.mapping.annotations.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A Filetosend.
 */

@Table(name = "filetosend")
public class Filetosend implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private String filename;

    private String subject;

    private ByteBuffer filetoupload;

    @Column(name = "filetoupload_content_type")
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

    public Filetosend filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSubject() {
        return subject;
    }

    public Filetosend subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ByteBuffer getFiletoupload() {
        return filetoupload;
    }

    public Filetosend filetoupload(ByteBuffer filetoupload) {
        this.filetoupload = filetoupload;
        return this;
    }

    public void setFiletoupload(ByteBuffer filetoupload) {
        this.filetoupload = filetoupload;
    }

    public String getFiletouploadContentType() {
        return filetouploadContentType;
    }

    public Filetosend filetouploadContentType(String filetouploadContentType) {
        this.filetouploadContentType = filetouploadContentType;
        return this;
    }

    public void setFiletouploadContentType(String filetouploadContentType) {
        this.filetouploadContentType = filetouploadContentType;
    }

    public String getRecipientemailid() {
        return recipientemailid;
    }

    public Filetosend recipientemailid(String recipientemailid) {
        this.recipientemailid = recipientemailid;
        return this;
    }

    public void setRecipientemailid(String recipientemailid) {
        this.recipientemailid = recipientemailid;
    }

    public String getRecipientname() {
        return recipientname;
    }

    public Filetosend recipientname(String recipientname) {
        this.recipientname = recipientname;
        return this;
    }

    public void setRecipientname(String recipientname) {
        this.recipientname = recipientname;
    }

    public String getAddsigner() {
        return addsigner;
    }

    public Filetosend addsigner(String addsigner) {
        this.addsigner = addsigner;
        return this;
    }

    public void setAddsigner(String addsigner) {
        this.addsigner = addsigner;
    }

    public String getAddcc() {
        return addcc;
    }

    public Filetosend addcc(String addcc) {
        this.addcc = addcc;
        return this;
    }

    public void setAddcc(String addcc) {
        this.addcc = addcc;
    }

    public String getAddbulk() {
        return addbulk;
    }

    public Filetosend addbulk(String addbulk) {
        this.addbulk = addbulk;
        return this;
    }

    public void setAddbulk(String addbulk) {
        this.addbulk = addbulk;
    }

    public String getCurrentlocation() {
        return currentlocation;
    }

    public Filetosend currentlocation(String currentlocation) {
        this.currentlocation = currentlocation;
        return this;
    }

    public void setCurrentlocation(String currentlocation) {
        this.currentlocation = currentlocation;
    }

    public String getDestinationlocation() {
        return destinationlocation;
    }

    public Filetosend destinationlocation(String destinationlocation) {
        this.destinationlocation = destinationlocation;
        return this;
    }

    public void setDestinationlocation(String destinationlocation) {
        this.destinationlocation = destinationlocation;
    }

    public LocalDate getFileinitiatedon() {
        return fileinitiatedon;
    }

    public Filetosend fileinitiatedon(LocalDate fileinitiatedon) {
        this.fileinitiatedon = fileinitiatedon;
        return this;
    }

    public void setFileinitiatedon(LocalDate fileinitiatedon) {
        this.fileinitiatedon = fileinitiatedon;
    }

    public LocalDate getFileclosedon() {
        return fileclosedon;
    }

    public Filetosend fileclosedon(LocalDate fileclosedon) {
        this.fileclosedon = fileclosedon;
        return this;
    }

    public void setFileclosedon(LocalDate fileclosedon) {
        this.fileclosedon = fileclosedon;
    }

    public String getDispatchno() {
        return dispatchno;
    }

    public Filetosend dispatchno(String dispatchno) {
        this.dispatchno = dispatchno;
        return this;
    }

    public void setDispatchno(String dispatchno) {
        this.dispatchno = dispatchno;
    }

    public Boolean isStatus() {
        return status;
    }

    public Filetosend status(Boolean status) {
        this.status = status;
        return this;
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
        Filetosend filetosend = (Filetosend) o;
        if (filetosend.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, filetosend.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Filetosend{" +
            "id=" + id +
            ", filename='" + filename + "'" +
            ", subject='" + subject + "'" +
            ", filetoupload='" + filetoupload + "'" +
            ", filetouploadContentType='" + filetouploadContentType + "'" +
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
