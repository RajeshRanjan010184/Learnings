package org.upgrad.upstac.testrequests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.testrequests.lab.CreateLabResult;
import org.upgrad.upstac.testrequests.lab.LabRequestController;
import org.upgrad.upstac.testrequests.lab.TestStatus;
import org.upgrad.upstac.users.User;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
class LabRequestControllerTest {


    @InjectMocks
    LabRequestController labRequestController;


    @Mock
    TestRequestQueryService testRequestQueryService;
    
    @Mock
    TestRequestUpdateService  testRequestUpdateService;
    
    @Mock
    private UserLoggedInService userLoggedInService;


    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_valid_test_request_id_should_update_the_request_status(){

        ArrayList<TestRequest> trList = new ArrayList<>();
        TestRequest tr1 = new TestRequest();
        tr1.setRequestId(123456L);
        tr1.setStatus(RequestStatus.INITIATED);
        trList.add(tr1);
        
        when(testRequestQueryService.findBy(RequestStatus.INITIATED)).thenReturn(trList);
        TestRequest testRequest = getTestRequestByStatus(RequestStatus.INITIATED);
        
        //Implement this method

        //Create another object of the TestRequest method and explicitly assign this object for Lab Test using assignForLabTest() method
        // from labRequestController class. Pass the request id of testRequest object.

        TestRequest tr2= new TestRequest();
        tr2.setStatus(RequestStatus.INITIATED);
        tr2.setRequestId(123456L);
       
        User user = new User();
        user.setUserName("Rajesh");
        when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        when(testRequestUpdateService.assignForLabTest(testRequest.getRequestId(),user)).thenReturn(tr2);
        TestRequest testReq = labRequestController.assignForLabTest(testRequest.getRequestId());
        
        //Use assertThat() methods to perform the following two comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'INITIATED'
        // make use of assertNotNull() method to make sure that the lab result of second object is not null
        // use getLabResult() method to get the lab result
        assertEquals(testRequest.getRequestId(), testReq.getRequestId());
        assertEquals(testReq.getStatus(),RequestStatus.INITIATED);
         
    }

    public TestRequest getTestRequestByStatus(RequestStatus status) {
        return testRequestQueryService.findBy(status).stream().findFirst().get();
    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_valid_test_request_id_should_throw_exception(){

        Long InvalidRequestId= -34L;

        //Implement this method


        // Create an object of ResponseStatusException . Use assertThrows() method and pass assignForLabTest() method
        // of labRequestController with InvalidRequestId as Id


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_valid_test_request_id_should_update_the_request_status_and_update_test_request_details(){

        ArrayList<TestRequest> trList = new ArrayList<>();
        TestRequest tr1 = new TestRequest();
        tr1.setRequestId(123456L);
        tr1.setStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        trList.add(tr1);
        
        when(testRequestQueryService.findBy(RequestStatus.LAB_TEST_IN_PROGRESS)).thenReturn(trList);
        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);

        //Implement this method
        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        CreateLabResult createLabResult = getCreateLabResult(testRequest);
        //Create another object of the TestRequest method and explicitly update the status of this object
        // to be 'LAB_TEST_IN_PROGRESS'. Make use of updateLabTest() method from labRequestController class (Pass the previously created two objects as parameters)

        TestRequest tr2= new TestRequest();
        tr2.setStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        tr2.setRequestId(123456L);
        
        User user = new User();
        user.setUserName("Rajesh");
        when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        when(testRequestUpdateService.updateLabTest(testRequest.getRequestId(),createLabResult,user)).thenReturn(tr2);
        TestRequest testReq = labRequestController.updateLabTest(testRequest.getRequestId(), createLabResult);
        //Use assertThat() methods to perform the following three comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'LAB_TEST_COMPLETED'
        // 3. the results of both the objects created should be same. Make use of getLabResult() method to get the results.

        assertEquals(testRequest.getRequestId(),testReq.getRequestId());
        assertEquals(testReq.getStatus(),RequestStatus.LAB_TEST_IN_PROGRESS);
        assertEquals(testRequest.getLabResult(),testReq.getLabResult());

    }


    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_test_request_id_should_throw_exception(){

        ArrayList<TestRequest> trList = new ArrayList<>();
        TestRequest tr1 = new TestRequest();
        tr1.setRequestId(1234567L);
        tr1.setStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        trList.add(tr1);
        
        when(testRequestQueryService.findBy(RequestStatus.LAB_TEST_IN_PROGRESS)).thenReturn(trList);
        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);


        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        CreateLabResult createLabResult = getCreateLabResult(testRequest);
        
        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with a negative long value as Id and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method
        TestRequest tr2= new TestRequest();
        tr2.setStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        tr2.setRequestId(-123456L);
        
        User user = new User();
        user.setUserName("Rajesh");
        when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        when(testRequestUpdateService.updateLabTest(testRequest.getRequestId(),createLabResult,user)).thenReturn(tr2);
        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{
            labRequestController.updateLabTest(tr2.getRequestId(), createLabResult);
        });

        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

        assertThat(result.getMessage(),containsString("Invalid ID"));
    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_empty_status_should_throw_exception(){

        ArrayList<TestRequest> trList = new ArrayList<>();
        TestRequest tr1 = new TestRequest();
        tr1.setRequestId(1234567L);
        tr1.setStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        trList.add(tr1);
        
        when(testRequestQueryService.findBy(RequestStatus.LAB_TEST_IN_PROGRESS)).thenReturn(trList);
        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        

        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter
        // Set the result of the above created object to null.
        
        CreateLabResult createLabResult = getCreateLabResult(testRequest);
        
        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with request Id of the testRequest object and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method
        
        TestRequest tr2= new TestRequest();
        tr2.setStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        tr2.setRequestId(-123456L);
        
        User user = new User();
        user.setUserName("Rajesh");
        when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        when(testRequestUpdateService.updateLabTest(testRequest.getRequestId(),createLabResult,user)).thenReturn(tr2);
        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{
            labRequestController.updateLabTest(tr2.getRequestId(), createLabResult);
        });

        assertThat(result.getReason(),containsString("ConstraintViolationException"));
        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "ConstraintViolationException"

    }

    public CreateLabResult getCreateLabResult(TestRequest testRequest) {

        //Create an object of CreateLabResult and set all the values
        // Return the object

        CreateLabResult createLabResult = new CreateLabResult();
        createLabResult.setBloodPressure("102");
        createLabResult.setComments("Fine");
        createLabResult.setHeartBeat("85");
        createLabResult.setOxygenLevel("90");
        createLabResult.setResult(TestStatus.NEGATIVE);
        createLabResult.setTemperature("101");
        return createLabResult; // Replace this line with your code
    }

}