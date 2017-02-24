package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
public class Controller {

    //jmeter test용
    /*
    curl \
      -F "file=@/home/nrkim/tool/jmeter/temp/json_request.txt" \
      http://localhost:8080/fileupload
     */
    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    public String addFile(@RequestPart MultipartFile file){
        String originalFileName = file.getOriginalFilename();
        log.info("this is add file. file name={} file type={}", originalFileName, file.getContentType());
        log.info("new file path={}", this.getClass().getResource("/").toString());

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
        log.info("this is test");
    }

    //jmeter random parameter
    @RequestMapping(value = "/ramdom/{gender}", method = RequestMethod.GET)
    public boolean testRandomPathVariable(@PathVariable("gender") String gender){
        log.info("testRandomPathVariable() {}", gender);
        return true;
    }

    @RequestMapping(value = "/ramdom", method = RequestMethod.GET)
    public boolean testRandomRequestParam(@RequestParam String gender){
        log.info("testRandomRequestParam() random requestParam {}", gender);
        return true;
    }
}
