package com.eoffice.app.web.rest;

import com.eoffice.app.AbstractCassandraTest;
import com.eoffice.app.NeweofficeApp;

import com.eoffice.app.domain.Filetosend;
import com.eoffice.app.repository.FiletosendRepository;
import com.eoffice.app.service.FiletosendService;
import com.eoffice.app.service.dto.FiletosendDTO;
import com.eoffice.app.service.mapper.FiletosendMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FiletosendResource REST controller.
 *
 * @see FiletosendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NeweofficeApp.class)
public class FiletosendResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final ByteBuffer DEFAULT_FILETOUPLOAD = ByteBuffer.wrap(TestUtil.createByteArray(1, "0"));
    private static final ByteBuffer UPDATED_FILETOUPLOAD = ByteBuffer.wrap(TestUtil.createByteArray(2, "1"));
    private static final String DEFAULT_FILETOUPLOAD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILETOUPLOAD_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_RECIPIENTEMAILID = "AAAAAAAAAA";
    private static final String UPDATED_RECIPIENTEMAILID = "BBBBBBBBBB";

    private static final String DEFAULT_RECIPIENTNAME = "AAAAAAAAAA";
    private static final String UPDATED_RECIPIENTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDSIGNER = "AAAAAAAAAA";
    private static final String UPDATED_ADDSIGNER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDCC = "AAAAAAAAAA";
    private static final String UPDATED_ADDCC = "BBBBBBBBBB";

    private static final String DEFAULT_ADDBULK = "AAAAAAAAAA";
    private static final String UPDATED_ADDBULK = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENTLOCATION = "AAAAAAAAAA";
    private static final String UPDATED_CURRENTLOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATIONLOCATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATIONLOCATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FILEINITIATEDON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FILEINITIATEDON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FILECLOSEDON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FILECLOSEDON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DISPATCHNO = "AAAAAAAAAA";
    private static final String UPDATED_DISPATCHNO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private FiletosendRepository filetosendRepository;

    @Autowired
    private FiletosendMapper filetosendMapper;

    @Autowired
    private FiletosendService filetosendService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFiletosendMockMvc;

    private Filetosend filetosend;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FiletosendResource filetosendResource = new FiletosendResource(filetosendService);
        this.restFiletosendMockMvc = MockMvcBuilders.standaloneSetup(filetosendResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Filetosend createEntity() {
        Filetosend filetosend = new Filetosend()
                .filename(DEFAULT_FILENAME)
                .subject(DEFAULT_SUBJECT)
                .filetoupload(DEFAULT_FILETOUPLOAD)
                .filetouploadContentType(DEFAULT_FILETOUPLOAD_CONTENT_TYPE)
                .recipientemailid(DEFAULT_RECIPIENTEMAILID)
                .recipientname(DEFAULT_RECIPIENTNAME)
                .addsigner(DEFAULT_ADDSIGNER)
                .addcc(DEFAULT_ADDCC)
                .addbulk(DEFAULT_ADDBULK)
                .currentlocation(DEFAULT_CURRENTLOCATION)
                .destinationlocation(DEFAULT_DESTINATIONLOCATION)
                .fileinitiatedon(DEFAULT_FILEINITIATEDON)
                .fileclosedon(DEFAULT_FILECLOSEDON)
                .dispatchno(DEFAULT_DISPATCHNO)
                .status(DEFAULT_STATUS);
        return filetosend;
    }

    @Before
    public void initTest() {
        filetosendRepository.deleteAll();
        filetosend = createEntity();
    }

    @Test
    public void createFiletosend() throws Exception {
        int databaseSizeBeforeCreate = filetosendRepository.findAll().size();

        // Create the Filetosend
        FiletosendDTO filetosendDTO = filetosendMapper.filetosendToFiletosendDTO(filetosend);

        restFiletosendMockMvc.perform(post("/api/filetosends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filetosendDTO)))
            .andExpect(status().isCreated());

        // Validate the Filetosend in the database
        List<Filetosend> filetosendList = filetosendRepository.findAll();
        assertThat(filetosendList).hasSize(databaseSizeBeforeCreate + 1);
        Filetosend testFiletosend = filetosendList.get(filetosendList.size() - 1);
        assertThat(testFiletosend.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testFiletosend.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testFiletosend.getFiletoupload()).isEqualTo(DEFAULT_FILETOUPLOAD);
        assertThat(testFiletosend.getFiletouploadContentType()).isEqualTo(DEFAULT_FILETOUPLOAD_CONTENT_TYPE);
        assertThat(testFiletosend.getRecipientemailid()).isEqualTo(DEFAULT_RECIPIENTEMAILID);
        assertThat(testFiletosend.getRecipientname()).isEqualTo(DEFAULT_RECIPIENTNAME);
        assertThat(testFiletosend.getAddsigner()).isEqualTo(DEFAULT_ADDSIGNER);
        assertThat(testFiletosend.getAddcc()).isEqualTo(DEFAULT_ADDCC);
        assertThat(testFiletosend.getAddbulk()).isEqualTo(DEFAULT_ADDBULK);
        assertThat(testFiletosend.getCurrentlocation()).isEqualTo(DEFAULT_CURRENTLOCATION);
        assertThat(testFiletosend.getDestinationlocation()).isEqualTo(DEFAULT_DESTINATIONLOCATION);
        assertThat(testFiletosend.getFileinitiatedon()).isEqualTo(DEFAULT_FILEINITIATEDON);
        assertThat(testFiletosend.getFileclosedon()).isEqualTo(DEFAULT_FILECLOSEDON);
        assertThat(testFiletosend.getDispatchno()).isEqualTo(DEFAULT_DISPATCHNO);
        assertThat(testFiletosend.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    public void createFiletosendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filetosendRepository.findAll().size();

        // Create the Filetosend with an existing ID
        Filetosend existingFiletosend = new Filetosend();
        existingFiletosend.setId(UUID.randomUUID());
        FiletosendDTO existingFiletosendDTO = filetosendMapper.filetosendToFiletosendDTO(existingFiletosend);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFiletosendMockMvc.perform(post("/api/filetosends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFiletosendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Filetosend> filetosendList = filetosendRepository.findAll();
        assertThat(filetosendList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = filetosendRepository.findAll().size();
        // set the field null
        filetosend.setStatus(null);

        // Create the Filetosend, which fails.
        FiletosendDTO filetosendDTO = filetosendMapper.filetosendToFiletosendDTO(filetosend);

        restFiletosendMockMvc.perform(post("/api/filetosends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filetosendDTO)))
            .andExpect(status().isBadRequest());

        List<Filetosend> filetosendList = filetosendRepository.findAll();
        assertThat(filetosendList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFiletosends() throws Exception {
        // Initialize the database
        filetosendRepository.save(filetosend);

        // Get all the filetosendList
        restFiletosendMockMvc.perform(get("/api/filetosends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filetosend.getId().toString())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].filetouploadContentType").value(hasItem(DEFAULT_FILETOUPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].filetoupload").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILETOUPLOAD.array()))))
            .andExpect(jsonPath("$.[*].recipientemailid").value(hasItem(DEFAULT_RECIPIENTEMAILID.toString())))
            .andExpect(jsonPath("$.[*].recipientname").value(hasItem(DEFAULT_RECIPIENTNAME.toString())))
            .andExpect(jsonPath("$.[*].addsigner").value(hasItem(DEFAULT_ADDSIGNER.toString())))
            .andExpect(jsonPath("$.[*].addcc").value(hasItem(DEFAULT_ADDCC.toString())))
            .andExpect(jsonPath("$.[*].addbulk").value(hasItem(DEFAULT_ADDBULK.toString())))
            .andExpect(jsonPath("$.[*].currentlocation").value(hasItem(DEFAULT_CURRENTLOCATION.toString())))
            .andExpect(jsonPath("$.[*].destinationlocation").value(hasItem(DEFAULT_DESTINATIONLOCATION.toString())))
            .andExpect(jsonPath("$.[*].fileinitiatedon").value(hasItem(DEFAULT_FILEINITIATEDON.toString())))
            .andExpect(jsonPath("$.[*].fileclosedon").value(hasItem(DEFAULT_FILECLOSEDON.toString())))
            .andExpect(jsonPath("$.[*].dispatchno").value(hasItem(DEFAULT_DISPATCHNO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    public void getFiletosend() throws Exception {
        // Initialize the database
        filetosendRepository.save(filetosend);

        // Get the filetosend
        restFiletosendMockMvc.perform(get("/api/filetosends/{id}", filetosend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filetosend.getId().toString()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.filetouploadContentType").value(DEFAULT_FILETOUPLOAD_CONTENT_TYPE))
            .andExpect(jsonPath("$.filetoupload").value(Base64Utils.encodeToString(DEFAULT_FILETOUPLOAD.array())))
            .andExpect(jsonPath("$.recipientemailid").value(DEFAULT_RECIPIENTEMAILID.toString()))
            .andExpect(jsonPath("$.recipientname").value(DEFAULT_RECIPIENTNAME.toString()))
            .andExpect(jsonPath("$.addsigner").value(DEFAULT_ADDSIGNER.toString()))
            .andExpect(jsonPath("$.addcc").value(DEFAULT_ADDCC.toString()))
            .andExpect(jsonPath("$.addbulk").value(DEFAULT_ADDBULK.toString()))
            .andExpect(jsonPath("$.currentlocation").value(DEFAULT_CURRENTLOCATION.toString()))
            .andExpect(jsonPath("$.destinationlocation").value(DEFAULT_DESTINATIONLOCATION.toString()))
            .andExpect(jsonPath("$.fileinitiatedon").value(DEFAULT_FILEINITIATEDON.toString()))
            .andExpect(jsonPath("$.fileclosedon").value(DEFAULT_FILECLOSEDON.toString()))
            .andExpect(jsonPath("$.dispatchno").value(DEFAULT_DISPATCHNO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    public void getNonExistingFiletosend() throws Exception {
        // Get the filetosend
        restFiletosendMockMvc.perform(get("/api/filetosends/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFiletosend() throws Exception {
        // Initialize the database
        filetosendRepository.save(filetosend);
        int databaseSizeBeforeUpdate = filetosendRepository.findAll().size();

        // Update the filetosend
        Filetosend updatedFiletosend = filetosendRepository.findOne(filetosend.getId());
        updatedFiletosend
                .filename(UPDATED_FILENAME)
                .subject(UPDATED_SUBJECT)
                .filetoupload(UPDATED_FILETOUPLOAD)
                .filetouploadContentType(UPDATED_FILETOUPLOAD_CONTENT_TYPE)
                .recipientemailid(UPDATED_RECIPIENTEMAILID)
                .recipientname(UPDATED_RECIPIENTNAME)
                .addsigner(UPDATED_ADDSIGNER)
                .addcc(UPDATED_ADDCC)
                .addbulk(UPDATED_ADDBULK)
                .currentlocation(UPDATED_CURRENTLOCATION)
                .destinationlocation(UPDATED_DESTINATIONLOCATION)
                .fileinitiatedon(UPDATED_FILEINITIATEDON)
                .fileclosedon(UPDATED_FILECLOSEDON)
                .dispatchno(UPDATED_DISPATCHNO)
                .status(UPDATED_STATUS);
        FiletosendDTO filetosendDTO = filetosendMapper.filetosendToFiletosendDTO(updatedFiletosend);

        restFiletosendMockMvc.perform(put("/api/filetosends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filetosendDTO)))
            .andExpect(status().isOk());

        // Validate the Filetosend in the database
        List<Filetosend> filetosendList = filetosendRepository.findAll();
        assertThat(filetosendList).hasSize(databaseSizeBeforeUpdate);
        Filetosend testFiletosend = filetosendList.get(filetosendList.size() - 1);
        assertThat(testFiletosend.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testFiletosend.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testFiletosend.getFiletoupload()).isEqualTo(UPDATED_FILETOUPLOAD);
        assertThat(testFiletosend.getFiletouploadContentType()).isEqualTo(UPDATED_FILETOUPLOAD_CONTENT_TYPE);
        assertThat(testFiletosend.getRecipientemailid()).isEqualTo(UPDATED_RECIPIENTEMAILID);
        assertThat(testFiletosend.getRecipientname()).isEqualTo(UPDATED_RECIPIENTNAME);
        assertThat(testFiletosend.getAddsigner()).isEqualTo(UPDATED_ADDSIGNER);
        assertThat(testFiletosend.getAddcc()).isEqualTo(UPDATED_ADDCC);
        assertThat(testFiletosend.getAddbulk()).isEqualTo(UPDATED_ADDBULK);
        assertThat(testFiletosend.getCurrentlocation()).isEqualTo(UPDATED_CURRENTLOCATION);
        assertThat(testFiletosend.getDestinationlocation()).isEqualTo(UPDATED_DESTINATIONLOCATION);
        assertThat(testFiletosend.getFileinitiatedon()).isEqualTo(UPDATED_FILEINITIATEDON);
        assertThat(testFiletosend.getFileclosedon()).isEqualTo(UPDATED_FILECLOSEDON);
        assertThat(testFiletosend.getDispatchno()).isEqualTo(UPDATED_DISPATCHNO);
        assertThat(testFiletosend.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    public void updateNonExistingFiletosend() throws Exception {
        int databaseSizeBeforeUpdate = filetosendRepository.findAll().size();

        // Create the Filetosend
        FiletosendDTO filetosendDTO = filetosendMapper.filetosendToFiletosendDTO(filetosend);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFiletosendMockMvc.perform(put("/api/filetosends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filetosendDTO)))
            .andExpect(status().isCreated());

        // Validate the Filetosend in the database
        List<Filetosend> filetosendList = filetosendRepository.findAll();
        assertThat(filetosendList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFiletosend() throws Exception {
        // Initialize the database
        filetosendRepository.save(filetosend);
        int databaseSizeBeforeDelete = filetosendRepository.findAll().size();

        // Get the filetosend
        restFiletosendMockMvc.perform(delete("/api/filetosends/{id}", filetosend.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Filetosend> filetosendList = filetosendRepository.findAll();
        assertThat(filetosendList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filetosend.class);
    }
}
