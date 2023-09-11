package cu.dev.halal.dao;


import cu.dev.halal.entity.ReportEntity;
import org.json.simple.JSONObject;

import java.util.List;

public interface ReportDAO {
    public JSONObject createReport(ReportEntity reportEntity);
    public List<ReportEntity> readAllReport(String email);
    public JSONObject updateReport(ReportEntity reportEntity);
    public JSONObject deleteReport(Long id);

}
