package com.eoffice.app.repository;

import com.eoffice.app.domain.Filetosend;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cassandra repository for the Filetosend entity.
 */
@Repository
public class FiletosendRepository {

    private final Session session;

    private Mapper<Filetosend> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public FiletosendRepository(Session session) {
        this.session = session;
        this.mapper = new MappingManager(session).mapper(Filetosend.class);
        this.findAllStmt = session.prepare("SELECT * FROM filetosend");
        this.truncateStmt = session.prepare("TRUNCATE filetosend");
    }

    public List<Filetosend> findAll() {
        List<Filetosend> filetosendsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Filetosend filetosend = new Filetosend();
                filetosend.setId(row.getUUID("id"));
                filetosend.setFilename(row.getString("filename"));
                filetosend.setSubject(row.getString("subject"));
                filetosend.setFiletoupload(row.getBytes("filetoupload"));
                filetosend.setFiletouploadContentType(row.getString("filetoupload_content_type"));

                filetosend.setRecipientemailid(row.getString("recipientemailid"));
                filetosend.setRecipientname(row.getString("recipientname"));
                filetosend.setAddsigner(row.getString("addsigner"));
                filetosend.setAddcc(row.getString("addcc"));
                filetosend.setAddbulk(row.getString("addbulk"));
                filetosend.setCurrentlocation(row.getString("currentlocation"));
                filetosend.setDestinationlocation(row.getString("destinationlocation"));
                filetosend.setFileinitiatedon(row.get("fileinitiatedon", LocalDate.class));
                filetosend.setFileclosedon(row.get("fileclosedon", LocalDate.class));
                filetosend.setDispatchno(row.getString("dispatchno"));
                filetosend.setStatus(row.getBool("status"));
                return filetosend;
            }
        ).forEach(filetosendsList::add);
        return filetosendsList;
    }

    public Filetosend findOne(UUID id) {
        return mapper.get(id);
    }

    public Filetosend save(Filetosend filetosend) {
        if (filetosend.getId() == null) {
            filetosend.setId(UUID.randomUUID());
        }
        mapper.save(filetosend);
        return filetosend;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
