package stepdefinition;
import com.expleo.qe.dataAndFileProcessing;
import com.expleo.qe.dataMethods;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class filePrcessing {


    @Steps
    dataMethods myMethods;

    @Given("^The CSV File Exist$")
    public void the_CSV_File_Exist(){

        myMethods.myFileExist();
    }

    @When("^The file is processed$")
    public void the_file_is_processed() {

        myMethods.getDataFromFile();
    }

    @Then("^The serenity report display the test outcomes$")
    public void the_serenity_report_display_the_test_outcomes() {

        myMethods.printMsg(dataAndFileProcessing.getDataLine());
    }
}
