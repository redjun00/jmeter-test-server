package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class Controller {

    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    //jmeter test용
    /*
    curl \
      -F "file=@/home/nrkim/tool/jmeter/temp/json_request.txt" \
      http://localhost:8080/fileupload
     */
    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    public String addFile(@RequestPart MultipartFile file){
        String originalFileName = file.getOriginalFilename();
        logger.info("this is add file. file name={} file type={}", originalFileName, file.getContentType());
        logger.info("new file path={}", this.getClass().getResource("/").toString());

        String dirPath = System.getProperty("user.dir") + "/build/temporary/";
        new File(dirPath).mkdir();
        File destinationDir = new File(dirPath + originalFileName);
        try {
            file.transferTo(destinationDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destinationDir.getAbsolutePath();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void testGet(){
        logger.info("this is test");
    }

    //jmeter random parameter
//    random?gender=a
    @RequestMapping(value = "/random/{gender}", method = RequestMethod.GET)
    public boolean testRandomPathVariable(@PathVariable("gender") String gender){
        logger.info("testRandomPathVariable() {}", gender);
        return true;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public boolean testRandomRequestParam(@RequestParam String gender){
        logger.info("testRandomRequestParam() random requestParam {}", gender);
        return true;
    }

    // /123
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getAsset(@PathVariable("id") long id){
        System.out.println("id controller called.");
        return "{\"asset\": {\"id\": " + id + "}}";
    }
}
