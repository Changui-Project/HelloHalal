package cu.dev.halal.service.impl;

import cu.dev.halal.dao.ReportDAO;
import cu.dev.halal.dto.ReportDTO;
import cu.dev.halal.entity.ReportEntity;
import cu.dev.halal.service.ReportService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final static Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ReportDAO reportDAO;

    public ReportServiceImpl(
            @Autowired ReportDAO reportDAO
    ){
        this.reportDAO = reportDAO;
    }

    // DTO를 Entity로 변환
    @Override
    public JSONObject createReport(ReportDTO reportDTO) {
        ReportEntity reportEntity = ReportEntity.builder()
                .title(reportDTO.getTitle())
                .content(reportDTO.getContent())
                .email(reportDTO.getEmail())
                .build();
        JSONObject jsonObject = this.reportDAO.createReport(reportEntity);

        return jsonObject;
    }

    // List를 Json에 담아 반환
    @Override
    public JSONObject readAllReport(String email) {
        List<ReportEntity> reportList = this.reportDAO.readAllReport(email);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reports", reportList);
        return jsonObject;
    }


    @Override
    public JSONObject updateReport(ReportDTO reportDTO) {
        return null;
    }

    @Override
    public JSONObject deleteReport(Long id) {
        return this.reportDAO.deleteReport(id);
    }
}
