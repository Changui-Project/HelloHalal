package cu.dev.halal.controller;


import cu.dev.halal.dto.ReportDTO;
import cu.dev.halal.service.ReportService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/report")
public class ReportController {
    private final static Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final ReportService reportService;

    public ReportController(
            @Autowired ReportService reportService
    ){
        this.reportService = reportService;
    }

    // result가 success 아니라면 400 Bad Request
    @PostMapping
    public ResponseEntity<JSONObject> createReport(
            @RequestBody ReportDTO reportDTO
            ){
        JSONObject jsonObject = this.reportService.createReport(reportDTO);
        if(!jsonObject.get("result").equals("success")){
            return ResponseEntity.status(400).body(jsonObject);
        }

        return ResponseEntity.status(201).body(jsonObject);
    }

    // 값이 존재하지 않는다면 빈 배열 반환
    @GetMapping
    public ResponseEntity<JSONObject> readAllReport(
            @RequestParam String email
    ){
        JSONObject jsonObject = this.reportService.readAllReport(email);
        // Report 배열이 비어있다면 빈 객체 반환
        return ResponseEntity.status(200).body(jsonObject);
    }


    // result가 success가 아니라면 400 Bad Request 전송
    @DeleteMapping("/{id}")
    public ResponseEntity<JSONObject> deleteReport(
            @PathVariable("id") Long id
    ){
        JSONObject jsonObject = this.reportService.deleteReport(id);
        if(!jsonObject.get("result").equals("success")){
            return ResponseEntity.status(400).body(jsonObject);
        }

        return ResponseEntity.status(200).body(jsonObject);
    }






}
