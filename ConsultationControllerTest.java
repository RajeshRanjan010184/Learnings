package org.upgrad.upstac.testrequests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.consultation.Consultation;
import org.upgrad.upstac.testrequests.consultation.ConsultationController;
import org.upgrad.upstac.testrequests.consultation.CreateConsultationRequest;
import org.upgrad.upstac.testrequests.consultation.DoctorSuggestion;
import org.upgrad.upstac.testrequests.lab.CreateLabResult;
import org.upgrad.upstac.testrequests.lab.TestStatus;
import org.upgrad.upstac.users.User;
import java.util.ArrayList;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.testrequests.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequestQueryService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest
@Slf4j
class ConsultationControllerTest {


    @InjectMocks
    ConsultationController consultationController;


    @Mock
    TestRequestQueryService testRequestQueryService;
    
    @Mock
    private UserLoggedInService userLoggedInService;
    
    @Mock
    TestRequestUpdateService  testRequestUpdateService;


    @Test
    @WithUserDetails(value = "doctor")
    public void calling_assignForConsultation_with_valid_test_request_id_should_update_the_request_status(){

        ArrayList<TestRequest> trList = new ArrayList<>();
        TestRequest tr1 = new TestRequest();
        tr1.setRequestId(123456L);
        tr1.setStatus(RequestStatus.DIAGNOSIS_IN_PROCESS);
        trList.add(tr1);
        
        when(testRequestQueryService.findBy(RequestStatus.DIAGNOSIS_IN_PROCESS)).thenReturn(trList);
        TestRequest testRequest = getTestRequestByStatus(RequestStatus.DIAGNOSIS_IN_PROCESS);

        //Implement this method

        //Create another object of the TestRequest method and explicitly assign this object for Consultation using assignForConsultation() method
        // from consultationController class. Pass the request id of testRequest object.
        
        TestRequest tr2= new TestRequest();
        tr2.setStatus(RequestStatus.DIAGNOSIS_IN_PROCESS);
        tr2.setRequestId(123456L);
       
        User user = new User();
        user.setUserName("Rajesh");
        when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        when(testRequestUpdateService.assignForConsultation(testRequest.getRequestId(),user)).thenReturn(tr2);
        TestRequest testReq = consultationController.assignForConsultation(testRequest.getRequestId());

        //Use assertThat() methods to perform the following two comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'DIAGNOSIS_IN_PROCESS'
        // make use of assertNotNull() method to make sure that the consultation value of second object is not null
        // use getConsultation() method to get the lab result
        assertEquals(testRequest.getRequestId(), testReq.getRequestId());
        assertEquals(testReq.getStatus(),RequestStatus.DIAGNOSIS_IN_PROCESS);
        
        

    }

    public TestRequest getTestRequestByStatus(RequestStatus status) {
        return testRequestQueryService.findBy(status).stream().findFirst().get();
    }

    @Test
    @WithUserDetails(value = "doctor")
    public void calling_assignForConsultation_with_valid_test_request_id_should_throw_exception(){

        Long InvalidRequestId= -34L;

        //Implement this method
        
        User user = new User();
        user.setUserName("Rajesh");
        when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        when(testRequestUpdateService.assignForConsultation(InvalidRequestId,user)).thenReturn(null);

        // Create an object of ResponseStatusException . Use assertThrows() method and pass assignForConsultation() method
        // of consultationController with InvalidRequestId as Id

        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{
            consultationController.assignForConsultation(InvalidRequestId);
        });
        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"
        
        assertThat(result.getReason(),containsString("Invalid ID"));

    }

    @Test
    @WithUserDetails(value = "doctor")
    public void calling_updateConsultation_with_valid_test_request_id_should_update_the_request_status_and_update_consultation_details(){

        ArrayList<TestRequest> trList = new ArrayList<>();
        TestRequest tr1 = new TestRequest();
        tr1.setRequestId(123456L);
        tr1.setStatus(RequestStatus.COMPLETED);
        trList.add(tr1);
        
        when(testRequestQueryService.findBy(RequestStatus.COMPLETED)).thenReturn(trList);
        TestRequest testRequest = getTestRequestByStatus(RequestStatus.COMPLETED);

        //Implement this method
        //Create an object of CreateConsultationRequest and call getCreateConsultationRequest() to create the object. Pass the above created object as the parameter

        CreateConsultationRequest createConsultationRequest = getCreateConsultationRequest(testRequest);
        //Create another object of the TestRequest method and explicitly update the status of this object
        // to be 'COMPLETED'. Make use of updateConsultation() method from labRequestController class (Pass the previously created two objects as parameters)

        TestRequest tr2= new TestRequest();
        Consultation consultation = new Consultation();
        tr2.setStatus(RequestStatus.COMPLETED);
        tr2.setRequestId(123456L);
       // tr2.setConsultation(consultation);
        
        User user = new User();
        user.setUserName("Rajesh");
        when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        when(testRequestUpdateService.updateConsultation(testRequest.getRequestId(),createConsultationRequest,user)).thenReturn(tr2);
        TestRequest testReq = consultationController.updateConsultation(testRequest.getRequestId(), createConsultationRequest);
        
        //Use assertThat() methods to perform the following three comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'COMPLETED'
        // 3. the suggestion of both the objects created should be same. Make use of getSuggestion() method to get the results.

        assertEquals(testRequest.getRequestId(),testReq.getRequestId());
        assertEquals(testReq.getStatus(),RequestStatus.COMPLETED);
        assertNotNull(createConsultationRequest.getSuggestion());
      //  assertEquals(testRequest.getLabResult(),testReq.getLabResult());

    }


    @Test
    @WithUserDetails(value = "doctor")
    public void calling_updateConsultation_with_invalid_test_request_id_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.DIAGNOSIS_IN_PROCESS);

        //Implement this method

        //Create an object of CreateConsultationRequest and call getCreateConsultationRequest() to create the object. Pass the above created object as the parameter

        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateConsultation() method
        // of consultationController with a negative long value as Id and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

    }

    @Test
    @WithUserDetails(value = "doctor")
    public void calling_updateConsultation_with_invalid_empty_status_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.DIAGNOSIS_IN_PROCESS);

        //Implement this method

        //Create an object of CreateConsultationRequest and call getCreateConsultationRequest() to create the object. Pass the above created object as the parameter
        // Set the suggestion of the above created object to null.

        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateConsultation() method
        // of consultationController with request Id of the testRequest object and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method


    }

    public CreateConsultationRequest getCreateConsultationRequest(TestRequest testRequest) {

        //Create an object of CreateLabResult and set all the values
        // if the lab result test status is Positive, set the doctor suggestion as "HOME_QUARANTINE" and comments accordingly
        // else if the lab result status is Negative, set the doctor suggestion as "NO_ISSUES" and comments as "Ok"
        // Return the object
        CreateLabResult createLabResult = new CreateLabResult();
        CreateConsultationRequest createConsultationRequest = new CreateConsultationRequest();
        createLabResult.setBloodPressure("102");
        createLabResult.setComments("Fine");
        createLabResult.setHeartBeat("100");
        createLabResult.setOxygenLevel("85");
        createLabResult.setResult(TestStatus.NEGATIVE);
        
        createConsultationRequest.setSuggestion(DoctorSuggestion.HOME_QUARANTINE);
        createConsultationRequest.setComments("HOME_QUARANTINE");
        

        return createConsultationRequest; // Replace this line with your code

    }

}