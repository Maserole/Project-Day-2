package com.expleo.qe;

import net.thucydides.core.annotations.Step;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class dataMethods {

    dataAndFileProcessing mydataManipulation = new dataAndFileProcessing();
    public dataMethods() {
    }

    @Step("Checking If File Exist And Not Empty")
    public void myFileExist(){

        assertThat(mydataManipulation.file + " does not exist", true, is(mydataManipulation.myFileIsFound(mydataManipulation.file)));
    }

    @Step("Data From File Processing")
    public void getDataFromFile(){

        mydataManipulation.fileManipulation();
    }

    @Step("{0}")
    public void printMsg(String msg){

    }

}
