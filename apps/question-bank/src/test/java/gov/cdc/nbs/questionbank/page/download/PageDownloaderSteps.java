package gov.cdc.nbs.questionbank.page.download;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import gov.cdc.nbs.questionbank.page.PageController;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.UserMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PageDownloaderSteps {

    @Autowired
    private UserMother userMother;

    @Autowired
    private PageController pageController;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private ResponseEntity<Resource> download;
    private ResponseEntity<byte[]> downloadPdf;


    @Given("I am an admin user make an download page library request")
    public void i_am_an_admin_user_make_an_download_page_library_request() {
        try {
            download = new ResponseEntity<Resource>(HttpStatus.OK);
            userMother.adminUser();
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Given("I am an admin user make an download page metadata request")
    public void i_am_an_admin_user_make_an_download_page_metadata_request() {
        try {
            download = new ResponseEntity<Resource>(HttpStatus.OK);
            userMother.adminUser();
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @When("I make a request to download page library")
    public void i_make_a_request_to_download_page_library() {
        try {
            download = pageController.downloadPageLibrary();
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        } catch (IOException e) {
            exceptionHolder.setException(e);
        }

    }

    @Then("Page library is downloaded")
    public void page_library_is_downloaded() {
        assertNotNull(download);
        assertEquals(HttpStatus.OK, download.getStatusCode());
        assertTrue(download.getHeaders().getContentDisposition().toString().contains("attachment; filename="));
        assertTrue(download.getHeaders().getContentType().toString().contains("application/csv"));

    }

    @When("I make a request to download page library as pdf")
    public void i_make_a_request_to_download_page_library_pdf() {
        try {
            downloadPdf = pageController.downloadPageLibraryPDF();
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        } catch (IOException e) {
            exceptionHolder.setException(e);
        } catch (DocumentException e) {
            exceptionHolder.setException(e);
        }

    }

    @Then("Page library is downloaded file as pdf")
    public void page_library_is_downloadedpdf() {
        assertNotNull(downloadPdf);
        assertEquals(HttpStatus.OK, download.getStatusCode());
        assertTrue(download.getHeaders().getContentDisposition().toString().contains("attachment; filename="));
        assertTrue(download.getHeaders().getContentType().toString().contains("application/pdf"));

    }

    @When("I make a request to download page metadata")
    public void i_make_a_request_to_download_page_metadata() {
        try {
            download = pageController.downloadPageMetadata(100l);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        } catch (IOException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("Page metadata is downloaded")
    public void page_metadata_is_downloaded() {
        assertNotNull(download);
        assertEquals(HttpStatus.OK, download.getStatusCode());
        assertTrue(download.getHeaders().getContentDisposition().toString().contains("attachment; filename="));
        assertTrue(download.getHeaders().getContentType().toString().contains("application/csv"));
    }


}
