package cu.dev.halal.service;


import cu.dev.halal.dto.ReportDTO;
import org.json.simple.JSONObject;

public interface ReportService {
    public JSONObject createReport(ReportDTO reportDTO);
    public JSONObject readAllReport(String email);
    // 보류
    public JSONObject updateReport(ReportDTO reportDTO);
    public JSONObject deleteReport(Long id);
}
