package com.cem.datajpabasic.school;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SchoolController {


    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }


    @PostMapping("/schools")
    public SchoolDto createSchool(@RequestBody SchoolDto dto) {

       return schoolService.createSchool(dto);
    }



    @GetMapping("/schools")
    public List<SchoolDto> getSchools() {

        return schoolService.getSchools();
    }



}
