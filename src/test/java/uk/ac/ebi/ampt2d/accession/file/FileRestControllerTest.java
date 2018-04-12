/*
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package uk.ac.ebi.ampt2d.accession.file;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.ampt2d.accession.file.persistence.FileAccessioningRepository;
import uk.ac.ebi.ampt2d.accession.file.rest.FileDTO;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileRestControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FileAccessioningRepository fileAccessioningRepository;

    @Test
    public void testRestApi() {
        FileDTO fileA = new FileDTO("checksumAAAAAAAAAAAAAAAAAAAAAAAA");
        FileDTO fileB = new FileDTO("checksumBBBBBBBBBBBBBBBBBBBBBBBB");
        FileDTO fileC = new FileDTO("checksumCCCCCCCCCCCCCCCCCCCCCCCC");

        String url = "/v1/file";
        HttpEntity<Object> requestEntity = new HttpEntity<>(Arrays.asList(fileA, fileB, fileC));

        ResponseEntity<Map> response = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void requestPostTwiceAndWeGetSameAccessions() {
        FileDTO fileA = new FileDTO("checksumAAAAAAAAAAAAAAAAAAAAAAAA");
        FileDTO fileB = new FileDTO("checksumBBBBBBBBBBBBBBBBBBBBBBBB");
        FileDTO fileC = new FileDTO("checksumCCCCCCCCCCCCCCCCCCCCCCCC");

        String url = "/v1/file/";
        HttpEntity<Object> requestEntity = new HttpEntity<>(Arrays.asList(fileA, fileB, fileC));

        ResponseEntity<Map> response = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
        assertEquals(3, fileAccessioningRepository.count());

        //Accessing Post Request again with same files
        response = testRestTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
        assertEquals(3, fileAccessioningRepository.count());
    }
}
